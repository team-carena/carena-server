package org.sopt.carena.member.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 회원 도메인 서비스
 */
@Slf4j
@Service
public class MemberDomainService {

    /**
     * OAuth2 정보로 회원 생성
     */
    public Member createFromOAuth2(OAuth2UserInfo userInfo) {
        log.info("OAuth2 회원 생성 - AuthType: {}, AuthId: {}",
                userInfo.getAuthType(), userInfo.getProviderId());

        return Member.builder()
                .authId(userInfo.getProviderId())
                .authType(userInfo.getAuthType())
                // 추가 정보는 회원가입 시 입력
                .build();
    }

    /**
     * 회원이 완전한 정보를 가지고 있는지 확인
     */
    public boolean isComplete(Member member) {
        return member.getName() != null
                && member.getBirthdate() != null
                && member.getGender() != null;
    }
}