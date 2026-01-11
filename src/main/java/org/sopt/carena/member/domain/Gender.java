package org.sopt.carena.member.domain;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String code;
    Gender(String code) {
        this.code = code;
    }
}
