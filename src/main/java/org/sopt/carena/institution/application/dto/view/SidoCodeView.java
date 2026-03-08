package org.sopt.carena.institution.application.dto.view;

import java.util.List;

import org.sopt.carena.institution.domain.vo.SidoCode;

public record SidoCodeView(
		List<SidoCodeInfo> result
) {
	public static SidoCodeView from(List<SidoCode> sidoCodes) {
		return new SidoCodeView(sidoCodes.stream()
				.map(SidoCodeInfo::from)
				.toList());
	}

	private record SidoCodeInfo(
			String sidoName,
			int sidoCode
	) {
		private static SidoCodeInfo from(SidoCode sidoCode) {
			return new SidoCodeInfo(sidoCode.sidoName(), sidoCode.sidoCode());
		}
	}
}
