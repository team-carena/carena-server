package org.sopt.carena.infrastructure.publicdata.dto;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record InstitutionInfoResponse(
		Body body
) {
	public record Body(
			Items items,
			int numOfRows,
			int pageNo,
			int totalCount
	) {}

	public record Items(
			@JacksonXmlElementWrapper(useWrapping = false)
			@JacksonXmlProperty(localName = "item")
			List<Item> item
	) {}

	// 검진 담당 구분 코드가 1인 경우 해당, 0인 경우 비해당
	public record Item(
			int bcExmdChrgTypeCd,	// 유방암검진담당구분코드
			int ccExmdChrgTypeCd,	// 대장암검진담당구분코드
			int cvxcaExmdChrgTypeCd,	// 자궁경부암검진담당구분코드
			double cxVl,	// 경도 좌표
			double cyVl,	// 위도 좌표
			int grenChrgTypeCd,		// 일반검진담당구분코드
			String hmcNm,	// 이름
			String hmcNo,	// 고유 번호(식별자)
			int ichkChrgTypeCd,		// 영유아검진담당구분코드
			String locAddr,	// 주소
			int lvcaExmdChrgTypeCd,	// 간암검진담당구분코드
			int mchkChrgTypeCd,		// 구강검진담당구분코드
			int stmcaExmdChrgTypeCd	// 위암검진담당구분코드
	) {}
}