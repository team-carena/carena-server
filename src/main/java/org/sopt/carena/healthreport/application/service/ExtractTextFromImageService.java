package org.sopt.carena.healthreport.application.service;

import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;
import org.sopt.carena.healthreport.application.port.in.ExtractTextFromImageUseCase;
import org.sopt.carena.infrastructure.ocr.port.OcrPort;
import org.sopt.carena.healthreport.application.service.util.OcrHealthReportParser;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExtractTextFromImageService implements ExtractTextFromImageUseCase {
	private final OcrPort ocrPort;

	public ExtractedTextView extractTextFromImage(final ExtractTextCommand command) {
		return OcrHealthReportParser.parse(ocrPort.extractText(command));
	}
}
