package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.commend.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;

public interface ExtractTextFromImageUseCase {
	ExtractedTextView extractTextFromImage(ExtractTextCommand command);
}
