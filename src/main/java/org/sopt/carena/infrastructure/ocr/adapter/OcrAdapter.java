package org.sopt.carena.infrastructure.ocr.adapter;

import org.sopt.carena.infrastructure.ocr.adapter.client.OcrClient;
import org.sopt.carena.infrastructure.ocr.dto.OcrResponse;
import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;
import org.sopt.carena.infrastructure.ocr.port.OcrPort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OcrAdapter implements OcrPort {
	private final OcrClient ocrClient;

	public OcrResponse extractText(ExtractTextCommand command) {
		return ocrClient.getOcrResult(command.image(), command.type(), command.fileName());
	}
}
