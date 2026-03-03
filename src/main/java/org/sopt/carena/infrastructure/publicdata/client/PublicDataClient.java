package org.sopt.carena.infrastructure.publicdata.client;

import org.sopt.carena.infrastructure.publicdata.dto.InstitutionInfoResponse;
import org.sopt.carena.infrastructure.publicdata.dto.SidoCodeResponse;
import org.sopt.carena.infrastructure.publicdata.dto.SigunguCodeResponse;
import org.sopt.carena.institution.exception.PublicDataAccessFailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PublicDataClient {
	private static final int numOfPage = 10;
	@Value("${public-data.base-url}")
	private String baseUrl;

	@Value("${public-data.path.address-code.sido}")
	private String sidoCodeRequestPath;

	@Value("${public-data.path.address-code.sigungu}")
	private String sigunguRequestPath;

	@Value("${public-data.path.institution}")
	private String institutionRequestPath;

	@Value("${public-data.secret-key}")
	private String apiKey;

	private final WebClient webClient;

	// 전체 18개
	public SidoCodeResponse getSidoCode() {
		try {
			return webClient.get()
					.uri(uriBuilder -> uriBuilder
							.scheme("https")
							.host(baseUrl)
							.path(sidoCodeRequestPath)
							.queryParam("serviceKey", apiKey)
							.queryParam("numOfRows", 20)
							.build())
					.retrieve()
					.bodyToMono(SidoCodeResponse.class).block();

		} catch (Exception e) {
			throw new PublicDataAccessFailException();
		}
	}

	// 전체 45개
	public SigunguCodeResponse getSigunguCode(final int sidoCode) {
		try {
			return webClient.get()
					.uri(uriBuilder -> uriBuilder
							.scheme("https")
							.host(baseUrl)
							.path(sigunguRequestPath)
							.queryParam("serviceKey", apiKey)
							.queryParam("siDoCd", sidoCode)
							.queryParam("numOfRows", 45)
							.build()
					)
					.retrieve()
					.bodyToMono(SigunguCodeResponse.class).block();
		} catch (Exception e) {
			throw new PublicDataAccessFailException();
		}
	}

	public InstitutionInfoResponse getInstitutionInfo(
			final int page,
			final Integer sidoCode,
			final Integer sigunguCode,
			final int type,
			final String institutionName
	) {
		try {
			return webClient.get()
					.uri(uriBuilder -> uriBuilder
							.scheme("https")
							.host(baseUrl)
							.path(institutionRequestPath)
							.queryParam("serviceKey", apiKey)
							.queryParam("pageNo", page)
							.queryParam("numOfRows", 20)
							.queryParam("siDoCd", sidoCode)
							.queryParam("siGunGuCd", sigunguCode)
							.queryParam("hchType", type)
							.queryParam("hmcNm", institutionName)
							.build()
					)
					.retrieve()
					.bodyToMono(InstitutionInfoResponse.class).block();
		} catch (Exception e) {
			throw new PublicDataAccessFailException();
		}
	}
}
