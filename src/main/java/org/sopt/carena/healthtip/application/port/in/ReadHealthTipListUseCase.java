package org.sopt.carena.healthtip.application.port.in;

import org.sopt.carena.healthtip.application.dto.view.ReadHealthTipListView;

public interface ReadHealthTipListUseCase {
	ReadHealthTipListView readHealthTipList(int page);
}
