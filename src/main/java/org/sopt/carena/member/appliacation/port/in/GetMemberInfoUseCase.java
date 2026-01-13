package org.sopt.carena.member.appliacation.port.in;

import org.sopt.carena.member.appliacation.dto.view.MemberInfoView;

public interface GetMemberInfoUseCase {
    MemberInfoView getMemberInfo(Long memberId, boolean withScore);
}
