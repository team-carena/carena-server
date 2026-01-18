package org.sopt.carena.healthtip.application.port.in;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipTickerView;

public interface ReadHealthTipTickerUseCase {
	ReadHealthTipTickerView readHealthTipTicker(long memberId);
}
