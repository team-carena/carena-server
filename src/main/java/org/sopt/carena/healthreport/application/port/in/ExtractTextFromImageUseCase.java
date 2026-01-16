package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.command.ExtractTextCommand;
import org.sopt.carena.healthreport.application.dto.view.ExtractedTextView;

public interface ExtractTextFromImageUseCase {
	ExtractedTextView extractTextFromImage(ExtractTextCommand command);
}
