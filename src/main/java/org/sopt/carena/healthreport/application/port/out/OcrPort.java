package org.sopt.carena.healthreport.application.port.out;

import org.sopt.carena.healthreport.domain.value.ocr.OcrResponse;
import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;

public interface OcrPort {
	OcrResponse extractText(ExtractTextCommand extractTextCommand);
}
