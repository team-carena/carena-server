package org.sopt.carena.healthtip.application.port.in;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipDetailView;

public interface ReadHealthTipDetailUseCase {
	ReadHealthTipDetailView readHealthTipDetail(long id);
}
