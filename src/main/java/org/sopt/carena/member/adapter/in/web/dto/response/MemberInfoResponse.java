package org.sopt.carena.member.adapter.in.web.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.carena.member.application.dto.view.MemberInfoView;
import org.sopt.carena.member.domain.Gender;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberInfoResponse(
        String name,
        int age,
        Gender gender,
        Long score
) {
    public static MemberInfoResponse from(final MemberInfoView view) {
        return new MemberInfoResponse(
                view.name(),
                view.age(),
                view.gender(),
                view.score()
        );
    }
}
