# CareNA <img width="100" alt="로고 이미지" src="https://github.com/user-attachments/assets/83d269e3-0bcb-46ab-878d-d6da50dcd3d5" align="left" />
<h3>건강검진 결과를 쉽게, 관리까지 한 번에!</h3>

<br/>

> 복잡한 건강검진 결과를 이해하기 쉽게 해설하고, 개인 상태에 맞춘 건강 관리 방향을 제안하는 헬스케어 서비스

<br/>
<img alt="102" src="https://github.com/user-attachments/assets/153510ba-c251-4f74-b131-39b28759d918" />

<br>
<br>
<br>

## 🔑 Key Features

### 1️⃣ 건강검진 데이터 분석 및 시각화
- OCR 기능을 지원하여 사용자가 건강검진 정보를 간편하게 입력할 수 있습니다.
- 사용자 기본 정보와 최근 건강검진 결과를 종합 분석하여 개인별 건강 점수를 산출하고 시각화하여 제공합니다.

### 2️⃣ 건강 팁 정보 및 건강 식단 큐레이션
- 사용자의 연령대에 맞춘 필터링을 적용하여 신뢰도 높은 건강 팁 아티클을 제공합니다.
- 최근 건강검진 결과를 기반으로 유사도 검색을 수행하여 개인 상태와 유사한 건강 식단을 큐레이션합니다.

### 3️⃣ RAG를 활용한 개인 맞춤형 건강 식단 추천
- 최근 건강검진 결과와 의료 기관에서 제공하는 공신력 있는 영양 정보를 결합하여 AI 기반 맞춤 식단을 추천합니다.
- 추천 과정에서 사용자 정보, 검진 결과, 그리고 건강 식단 큐레이션 데이터 간의 유사도를 비교하여 보다 정밀한 개인 맞춤형 식단을 제공합니다.

<br>

## 🧑‍🤝‍🧑 Members



<div align="center">
  <table width="100%">
  <tr>
    <td align="center"><a href="https://github.com/Kyoung-M1N">박경민 👑</a></td>
    <td align="center"><a href=https://github.com/byunhm02">변희민</a></td>
  </tr>
  <tr>
    <td align="center"><img width="243" height="387" alt="박경민_명찰" src="https://github.com/user-attachments/assets/e53d9966-ae17-4520-9075-ca000836b66a" /></td>
    <td align="center"><img width="243" height="387" alt="변희민_명찰" src="https://github.com/user-attachments/assets/1451f5f5-d4ea-4c6b-a1e0-cce09b6b2126" /></td>
  </tr>
  <tr>
    <td align="center"><a href="https://github.com/Kyoung-M1N">@Kyoung-M1N</a></td>
    <td align="center"><a href="https://github.com/byunhm02">@byunhm02</a></td>
  </tr>
</table>
</div>

<br>

## 🛠 Tech Stack

### 🧩 Backend
- Java 21
  - 가상 스레드를 이용한 비동기 처리 구현을 위해 사용
- Spring Boot 3.5.9
  - Spring AI와의 호환을 위한 버전 선정
- Spring Data JPA
  - 추상화된 인터페이스를 이용한 데이터베이스 접근을 위해 사용
- Spring Security
  - 필터링 방식의 인증 인가 방식의 구현을 위해 사용
- JWT (Access / Refresh Token)
  - 무상태성 인증 방식 구현을 위해 사용
- Spring AI
  - 임베딩 및 LLM 호출을 위해 사용

### 💾 Database
- PostgreSQL
  - 관계형 데이터베이스에서의 벡터 스키마 활용을 위해 사용
- PGVector
  - PostgreSQL에서의 벡터 연산을 위해 사용
- Redis
  - JWT 및 임시 토큰의 블랙리스팅 및 만료 처리를 위해 사용

### 🎛️ Infra
- AWS EC2
  - 서버 애플리케이션 동작을 위해 사용
- AWS RDS
  - 데이터베이스 운영을 위해 사용
- Docker
  - 운영 환경에서의 프로세스 격리 및 배포를 위해 사용
- NginX
  - 무중단 배포 시 라우팅 및 프록시 설정을 위해 사용

<br>

## 🧷 ERDiagram

<img width="2590" height="1102" alt="image" src="https://github.com/user-attachments/assets/8f7445f9-0e4c-47e5-ad8d-890093d5c75c" />

<br>

## 🏗 Service Architecture

<img width="729" alt="케어나 서비스 아키텍쳐" src="https://github.com/user-attachments/assets/5daa7d4f-4427-44ec-ae3b-93b08339e413" />

<br>

## 🧩 Project Architecture

### 🧱Application Architecture

- 헥사고날 기반 DDD 지향 클린 아키텍쳐

<img width="3962" height="1095" alt="image" src="https://github.com/user-attachments/assets/c9fd8a91-67dd-411e-8dc1-8e0ad1f1e82b" />


### 🗂️ Package Structure

```
├── ▶️ CarenaApplication
├── 📦 diet
│   ├── 🔌 adapter
│   │   ├── 📥 in
│   │   └── 📤 out
│   ├── 🧠 application
│   │   ├── 🚪 port
│   │   │   ├── 📂 in
│   │   │   └── 📂 out
│   │   └── 📂 service
│   ├── 💎 domain
│   └── ⚠️ exception
├── 📦 global
│   ├── 📡 api
│   ├── 📂 common
│   ├── ⚙️ config
│   └── ⚠️ exception
├── 📦 healthreport
│   ├── 🔌 adapter
│   │   ├── 📥 in
│   │   └── 📤 out
│   ├── 🧠 application
│   │   ├── 🚪 port
│   │   │   ├── 📂 in
│   │   │   └── 📂 out
│   │   └── 📂 service
│   ├── 💎 domain
│   └── ⚠️ exception
├── 📦 healthtip
│   ├── 🔌 adapter
│   │   ├── 📥 in
│   │   └── 📤 out
│   ├── 🧠 application
│   │   ├── 🚪 port
│   │   │   ├── 📂 in
│   │   │   └── 📂 out
│   │   └── 📂 service
│   ├── 💎 domain
│   └── ⚠️ exception
├── 📦 infrastructure
│   ├── 🗂️ embedding
│   ├── 🗂️ exception
│   ├── 🗂️ llm
│   └── 🗂️ ocr
├── 📦 member
│   ├── 🔌 adapter
│   │   ├── 📥 in
│   │   └── 📤 out
│   ├── 🧠 application
│   │   ├── 🚪 port
│   │   │   ├── 📂 in
│   │   │   └── 📂 out
│   │   └── 📂 service
│   ├── 💎 domain
│   └── ⚠️ exception
└── 📦 recommend
    ├── 🔌 adapter
    │   ├── 📥 in
    │   └── 📤 out
    ├── 🧠 application
    │   ├── 🚪 port
    │   │   ├── 📂 in
    │   │   └── 📂 out
    │   └── 📂 service
    ├── 💎 domain
    └── ⚠️ exception
```
