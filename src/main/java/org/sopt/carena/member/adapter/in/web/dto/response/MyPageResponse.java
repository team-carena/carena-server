package org.sopt.carena.member.adapter.in.web.dto.response;

import org.sopt.carena.member.application.dto.view.MyPageInfoView;

import java.time.LocalDate;

public record MyPageResponse(
        String name,
        LocalDate birthdate
) {
    public static MyPageResponse from(MyPageInfoView view) {
        return new MyPageResponse(
                view.name(),
                view.birthdate()
        );
    }
}