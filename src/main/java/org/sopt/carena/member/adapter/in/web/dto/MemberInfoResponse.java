
package org.sopt.carena.member.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.sopt.carena.member.appliacation.dto.view.MemberInfoView;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberInfoResponse(
        Long memberId,
        String name,
        LocalDate birthdate,
        Long score
) {
    public static MemberInfoResponse from(MemberInfoView view, boolean withScore) {
        return new MemberInfoResponse(
                view.id(),
                view.name(),
                view.birthdate(),
                withScore ? view.score() : null
        );
    }
}
