package org.sopt.carena.healthreport.application.service;

import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.sopt.carena.healthreport.application.port.in.ExtractTextFromImageUseCase;
import org.sopt.carena.healthreport.application.port.out.OcrPort;
import org.sopt.carena.healthreport.application.parser.OcrHealthReportParser;
import org.sopt.carena.healthreport.exception.ocr.OcrApiFailException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExtractTextFromImageService implements ExtractTextFromImageUseCase {
	private final OcrPort ocrPort;

	@Retryable(
			retryFor = OcrApiFailException.class,
			maxAttempts = 3,
			backoff = @Backoff(delay = 5000, multiplier = 2),
			recover = "recoverOcrApi"
	)
	public ExtractedTextView extractTextFromImage(final ExtractTextCommand command) {
		return OcrHealthReportParser.parse(ocrPort.extractText(command));
	}

	@Recover
	public ExtractedTextView recoverOcrApi(final OcrApiFailException e, final ExtractTextCommand command) {
		log.error(e.getMessage());
		throw e;
	}
}
