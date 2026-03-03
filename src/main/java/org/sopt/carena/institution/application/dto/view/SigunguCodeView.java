package org.sopt.carena.institution.application.dto.view;

import java.util.List;

import org.sopt.carena.institution.domain.vo.SigunguCode;

public record SigunguCodeView(
		List<SigunguCodeInfo> result
) {
	public static SigunguCodeView from(List<SigunguCode> sigunguCodes) {
		return new SigunguCodeView(sigunguCodes.stream()
				.map(SigunguCodeInfo::from)
				.toList());
	}

	private record SigunguCodeInfo(
			String sidoName,
			int sidoCode,
			int sigunguCode
	) {
		private static SigunguCodeInfo from(SigunguCode sigunguCode) {
			return new SigunguCodeInfo(sigunguCode.sigunguName(), sigunguCode.sidoCode(), sigunguCode.sigunguCode());
		}
	}
}