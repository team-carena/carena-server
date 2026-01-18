package org.sopt.carena.infrastructure.ocr.port;

import org.sopt.carena.infrastructure.ocr.dto.OcrResponse;
import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;

public interface OcrPort {
	OcrResponse extractText(ExtractTextCommand extractTextCommand);
}
