package org.sopt.carena.member.application.port.in;

public interface WithdrawalUseCase {
    void withdrawal(Long memberId,String accessToken,String refreshToken);
}
