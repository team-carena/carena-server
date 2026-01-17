package org.sopt.carena.healthreport.adapter.out.web.ocr;

import java.util.List;
import java.util.UUID;

import org.sopt.carena.healthreport.domain.value.ocr.OcrResponse;
import org.sopt.carena.healthreport.exception.ocr.OcrApiFailException;
import org.sopt.carena.healthreport.exception.ocr.OcrMessageSerializationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OcrClient {
	@Value(value = "${ocr.naver.url}")
	private String requestUrl;

	@Value(value = "${ocr.naver.secret-key}")
	private String apiKey;

	private final WebClient webClient;

	public OcrResponse getOcrResult(MultipartFile file, String format, String filename) {

		MultipartBodyBuilder builder = new MultipartBodyBuilder();

		builder.part("message", buildMessage(extractImageFormat(format), filename));
		builder.part("file", file.getResource());

		try {
			return webClient.post()
					.uri(requestUrl)
					.contentType(MediaType.MULTIPART_FORM_DATA)
					.header("X-OCR-SECRET", apiKey)
					.body(BodyInserters.fromMultipartData(builder.build()))
					.retrieve()
					.bodyToMono(OcrResponse.class).block();
		} catch (Exception e) {
			throw new OcrApiFailException();
		}
	}

	private String buildMessage(String format, String name) {
		OcrRequestMessage message = new OcrRequestMessage(
				"v2",
				UUID.randomUUID().toString(),
				System.currentTimeMillis(),
				"ko",
				List.of(new OcrRequestMessage.Image(format, name))
		);

		try {
			return new ObjectMapper().writeValueAsString(message);
		} catch (JsonProcessingException e) {
			throw new OcrMessageSerializationException();
		}
	}

	private record OcrRequestMessage(
			String version,
			String requestId,
			long timestamp,
			String lang,
			List<Image> images
	) {
		private record Image(String format, String name) {
		}
	}

	private String extractImageFormat(String format) {
		return format.replace("image/", "");
	}
}
