package org.sopt.carena.global.api.code;

import org.springframework.http.HttpStatus;

public interface ResultCode {
	HttpStatus getStatus();

	String getMessage();
}
