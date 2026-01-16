package org.sopt.carena.healthreport.application.port.out;

import org.sopt.carena.healthreport.adapter.out.web.ocr.response.OcrResponse;
import org.sopt.carena.healthreport.application.dto.commend.ExtractTextCommand;

public interface OcrPort {
	OcrResponse extractText(ExtractTextCommand extractTextCommand);
}
