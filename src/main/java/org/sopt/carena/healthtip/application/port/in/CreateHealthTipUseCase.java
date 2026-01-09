package org.sopt.carena.healthtip.application.port.in;

import org.sopt.carena.healthtip.application.dto.commend.CreateHealthTipCommand;

public interface CreateHealthTipUseCase {
	void createHealthTip(CreateHealthTipCommand commend);
}
