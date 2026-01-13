#!/bin/bash
set -e

cd ~/app

# 필수 환경 변수 검증
REQUIRED_VARS=(
  "DOCKER_USERNAME"
  "DB_HOST"
  "DB_NAME"
  "DB_USERNAME"
  "DB_PASSWORD"
  "KAKAO_CLIENT_ID"
  "KAKAO_CLIENT_SECRET"
  "KAKAO_REDIRECT_URI"
  "JWT_SECRET"
  "FRONTEND_URL"
)

for var in "${REQUIRED_VARS[@]}"; do
  if [ -z "${!var}" ]; then
    echo "❌ 오류: $var 환경 변수가 설정되지 않았습니다."
    exit 1
  fi
done

# .env 파일 생성
cat > .env << EOF
DOCKER_USERNAME=${DOCKER_USERNAME}
DB_HOST=${DB_HOST}
DB_NAME=${DB_NAME}
DB_USERNAME=${DB_USERNAME}
DB_PASSWORD=${DB_PASSWORD}
KAKAO_CLIENT_ID=${KAKAO_CLIENT_ID}
KAKAO_CLIENT_SECRET=${KAKAO_CLIENT_SECRET}
KAKAO_REDIRECT_URI=${KAKAO_REDIRECT_URI}
JWT_SECRET=${JWT_SECRET}
JWT_EXPIRATION=${JWT_EXPIRATION:-86400000}
JWT_ACCESS_TOKEN_EXPIRATION=${JWT_ACCESS_TOKEN_EXPIRATION:-3600000}
JWT_REFRESH_TOKEN_EXPIRATION=${JWT_REFRESH_TOKEN_EXPIRATION:-1209600000}
FRONTEND_URL=${FRONTEND_URL}
EOF

# default.conf 초기 생성 (없을 경우만)
if [ ! -f nginx/conf.d/default.conf ]; then
  echo "초기 설정: default.conf 생성 (Blue로 시작)"
  cat > nginx/conf.d/default.conf << 'EOFCONF'
upstream app {
  server blue:8080;
}

server {
  listen 80;
  server_name api.care-na.com;

  # 인증서 갱신
  location /.well-known/acme-challenge/ {
    root /var/lib/letsencrypt/;
  }

  location / {
    return 308 https://$host$request_uri;
  }
}
server {
  listen 443 ssl;
  server_name api.care-na.com;

  # SSL 인증서 경로
  ssl_certificate /etc/letsencrypt/live/api.care-na.com/fullchain.pem;
  ssl_certificate_key /etc/letsencrypt/live/api.care-na.com/privkey.pem;

  location / {
    proxy_pass http://app;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    proxy_set_header X-Forwarded-Proto $scheme;
  }

  location /actuator/health {
    proxy_pass http://app/actuator/health;
  }
}
EOFCONF
  docker compose up -d
  exit 0
fi

# 디스크 정리
docker image prune -f

# 새 이미지 pull
docker pull ${DOCKER_USERNAME}/carena-api:latest

# 현재 active 판단
ACTIVE=$(grep "server" nginx/conf.d/default.conf | grep -o "blue\|green" || echo "blue")

if [ "$ACTIVE" = "blue" ]; then
  NEW="green"
  PORT=8081
else
  NEW="blue"
  PORT=8082
fi

echo "현재 active: $ACTIVE"
echo "새 배포 대상: $NEW"

# 새 컨테이너 기동
docker compose up -d --no-deps $NEW

# 헬스체크
HEALTH_OK=false
for i in {1..20}; do
  if curl -f http://localhost:${PORT}/actuator/health 2>/dev/null; then
    echo "[$i/20] 헬스체크 성공"
    HEALTH_OK=true
    break
    fi
    echo "[$i/20] 헬스체크 대기중..."
    sleep 3
done

# 헬스체크 실패 시 롤백
if [ "$HEALTH_OK" = false ]; then
  echo "❌ 헬스체크 실패! 롤백합니다."
  docker-compose stop $NEW
  exit 1
fi

# nginx 스위치
echo "트래픽을 $NEW 환경으로 전환 중..."

# 설정 변경
sed -i "s/server $ACTIVE:8080;/server $NEW:8080;/" nginx/conf.d/default.conf

# 설정 검증
if ! docker exec nginx nginx -t 2>/dev/null; then
  echo "❌ Nginx 설정 오류!"
  sed -i "s/server $NEW:8080;/server $ACTIVE:8080;/" nginx/conf.d/default.conf
  docker compose stop $NEW
  exit 1
fi

# Nginx 리로드
if ! docker exec nginx nginx -s reload; then
  echo "❌ Nginx reload 실패!"
  sed -i "s/server $NEW:8080;/server $ACTIVE:8080;/" nginx/conf.d/default.conf
  docker exec nginx nginx -s reload
  docker compose stop $NEW
  exit 1
fi

echo "✅ 트래픽 전환 완료"

# 이전 컨테이너 중지
echo "이전 컨테이너($ACTIVE) 중지 중"
sleep 5
docker compose stop $ACTIVE
echo "✅ 이전 컨테이너 중지 완료"

docker system prune -f

echo "✅ 배포 완료"