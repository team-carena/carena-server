package org.sopt.carena.healthreport.application.dto.command;

import org.springframework.web.multipart.MultipartFile;

public record ExtractTextCommand(
		MultipartFile image,
		String type,
		String fileName

) {
	public static ExtractTextCommand from(final MultipartFile file){
		return new ExtractTextCommand(
				file,
				file.getContentType(),
				file.getOriginalFilename()
		);
	}
}
