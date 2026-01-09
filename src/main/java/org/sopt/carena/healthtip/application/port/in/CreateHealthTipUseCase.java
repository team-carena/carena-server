package org.sopt.carena.healthtip.application.port.in;

import org.sopt.carena.healthtip.application.dto.commend.CreateHealthTipCommend;

public interface CreateHealthTipUseCase {
	void createHealthTip(CreateHealthTipCommend commend);
}
