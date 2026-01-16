package org.sopt.carena.healthreport.adapter.out.web.ocr.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OcrResponse(
		List<ImageInfo> images
) {
	@JsonIgnoreProperties(ignoreUnknown = true)
	public record ImageInfo(
			List<OcrField> fields
	) {
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record OcrField(
			String inferText,
			boolean lineBreak
	) {
	}
}
