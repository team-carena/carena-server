package org.sopt.carena.healthreport.adapter.out.web.ocr;

import org.sopt.carena.healthreport.adapter.out.web.ocr.response.OcrResponse;
import org.sopt.carena.healthreport.application.dto.commend.ExtractTextCommand;
import org.sopt.carena.healthreport.application.port.out.OcrPort;
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
