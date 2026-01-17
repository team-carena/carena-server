package org.sopt.carena.healthreport.application.port.in;

import org.sopt.carena.healthreport.application.dto.view.HealthReportDateListView;

public interface GetReportDateListUseCase {
	HealthReportDateListView getReportDateList(long memberId, int index);
}
