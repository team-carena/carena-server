package org.sopt.carena.healthreport.adapter.out.persistence;

import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.mapper.HealthReportMapper;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthReportPersistenceAdapter implements HealthReportPersistencePort {
	private final HealthReportRepository healthReportRepository;
	private final MemberJpaRepository memberJpaRepository;

	@Transactional
	public void saveHealthReport(final HealthReport healthReport) {
		MemberEntity memberEntityProxy = memberJpaRepository.getReferenceById(healthReport.getMemberId());

		healthReportRepository.save(HealthReportMapper.toEntity(healthReport, memberEntityProxy));
	}

	public Slice<HealthReport> findAllByMemberIdOrderByHealthCheckDateDesc(final long memberId, final int index) {
		Pageable pageable = PageRequest.of(index - 1, 10);
		return healthReportRepository.findAllByMemberEntityIdOrderByHealthCheckDateDesc(memberId, pageable)
				.map(HealthReportMapper::toDomain);

	}

	public Optional<HealthReport> findByMemberIdAndHealthReportId(final long memberId, final long healthReportId) {
		return healthReportRepository.findByMemberEntityIdAndId(memberId, healthReportId)
				.map(HealthReportMapper::toDomain);
	}

	public List<HealthReport> findLatestHealthReportsHeightIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndHeightIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsWeightIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndWeightIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsWaistCircumferenceIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndWaistCircumferenceIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsBmiIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndBmiIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsSystolicBpIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndSystolicBpIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsDiastolicBpIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndDiastolicBpIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsHemoglobinIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndHemoglobinIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsFastingGlucoseIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndFastingGlucoseIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsAstIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndAstIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsAltIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndAltIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsGammaGtpIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndGammaGtpIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsSerumCreatinineIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndSerumCreatinineIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsEgfrIsNotNullByMemberId(final long memberId) {
		return healthReportRepository.findTop5ByMemberEntityIdAndEgfrIsNotNullOrderByHealthCheckDateDesc(memberId)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}
}
