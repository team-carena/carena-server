package org.sopt.carena.member.application.port.in;

import org.sopt.carena.member.application.dto.view.MemberInfoView;
import org.sopt.carena.member.application.dto.view.MyPageInfoView;

public interface GetMemberInfoUseCase {
    MemberInfoView getMemberInfo(Long memberId);
    MyPageInfoView getMyPageInfo(Long memberId);
}
