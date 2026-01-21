package org.sopt.carena.healthreport.adapter.out.persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.sopt.carena.healthreport.adapter.out.persistence.mapper.HealthReportMapper;
import org.sopt.carena.healthreport.adapter.out.persistence.repository.HealthReportRepository;
import org.sopt.carena.healthreport.application.port.out.HealthReportPersistencePort;
import org.sopt.carena.healthreport.domain.HealthReport;
import org.sopt.carena.member.adapter.out.persistence.entity.MemberEntity;
import org.sopt.carena.member.adapter.out.persistence.repository.MemberJpaRepository;
import org.sopt.carena.recommend.application.port.out.LoadHealthReportPort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthReportPersistenceAdapter implements HealthReportPersistencePort, LoadHealthReportPort {
	private final HealthReportRepository healthReportRepository;
	private final MemberJpaRepository memberJpaRepository;

	@Transactional
	public HealthReport saveHealthReport(final HealthReport healthReport) {
		MemberEntity memberEntityProxy = memberJpaRepository.getReferenceById(healthReport.getMemberId());

		return HealthReportMapper.toDomain(healthReportRepository.save(HealthReportMapper.toEntity(healthReport, memberEntityProxy)));
	}

	public boolean existsByMemberIdAndHealthCheckDate(long memberId, LocalDate healthCheckDate) {
		return healthReportRepository.existsByMemberEntityIdAndHealthCheckDate(memberId, healthCheckDate);
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

	public Optional<HealthReport> findLatestHealthReportByMemberId(final long memberId) {
		return healthReportRepository.findTopByMemberEntityIdOrderByHealthCheckDateDesc(memberId)
				.map(HealthReportMapper::toDomain);
	}

	public List<HealthReport> findLatestHealthReportsHeightIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndHeightIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsWeightIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndWeightIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsWaistCircumferenceIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndWaistCircumferenceIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsBmiIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndBmiIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsSystolicBpIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndSystolicBpIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsDiastolicBpIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndDiastolicBpIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsHemoglobinIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndHemoglobinIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsFastingGlucoseIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndFastingGlucoseIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsAstIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndAstIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsAltIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndAltIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsGammaGtpIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndGammaGtpIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsSerumCreatinineIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndSerumCreatinineIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public List<HealthReport> findLatestHealthReportsEgfrIsNotNullByMemberId(final long memberId, final LocalDate healthCheckDate) {
		return healthReportRepository.findTop5ByMemberEntityIdAndEgfrIsNotNullAndHealthCheckDateBeforeOrderByHealthCheckDateDesc(memberId, healthCheckDate)
				.stream()
				.map(HealthReportMapper::toDomain)
				.toList();
	}

	public Optional<HealthReport> findLatestByMemberId(final long memberId) {
		return healthReportRepository
				.findTopByMemberEntityIdOrderByHealthCheckDateDesc(memberId)
				.map(HealthReportMapper::toDomain);
	}
}
