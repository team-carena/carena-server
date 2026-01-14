package org.sopt.carena.member.application.port.in;

public interface LogoutUseCase {
    void logout(Long memberId,String accessToken);
}
