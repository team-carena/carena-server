package org.sopt.carena.healthtip.adapter.out.persistence.repository;

import java.util.Collections;
import java.util.List;

import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.QHashtagEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.QHealthTipEntity;
import org.sopt.carena.healthtip.adapter.out.persistence.entity.QHealthTipHashtagEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HealthTipSearchRepositoryImpl implements HealthTipSearchRepository {
	private final JPAQueryFactory queryFactory;

	public Slice<HealthTipEntity> findHealthTipListWithHashTags(
			String hashtagName, Pageable pageable) {

		QHealthTipEntity ht = QHealthTipEntity.healthTipEntity;
		QHealthTipHashtagEntity hth = QHealthTipHashtagEntity.healthTipHashtagEntity;
		QHashtagEntity h = QHashtagEntity.hashtagEntity;

		List<Long> ids = queryFactory
				.select(ht.id)
				.distinct()
				.from(ht)
				.join(ht.hashtags, hth)
				.join(hth.hashtag, h)
				.where(hashtagNameEq(hashtagName))
				.orderBy(ht.id.desc())
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize() + 1)
				.fetch();

		if (ids.isEmpty()) {
			return new SliceImpl<>(Collections.emptyList(), pageable, false);
		}

		List<HealthTipEntity> healthTips = queryFactory
				.selectFrom(ht)
				.leftJoin(ht.hashtags, hth).fetchJoin()
				.leftJoin(hth.hashtag, h).fetchJoin()
				.where(
						ht.id.in(ids)
				)
				.orderBy(ht.id.desc())
				.fetch();

		boolean hasNext = ids.size() > pageable.getPageSize();
		if (hasNext) {
			healthTips.removeLast();
		}

		return new SliceImpl<>(healthTips, pageable, hasNext);
	}

	private BooleanExpression hashtagNameEq(String hashtagName) {
		if (hashtagName == null || hashtagName.isBlank()) {
			return null;
		}
		return QHashtagEntity.hashtagEntity.name.eq(hashtagName);
	}

	public List<HealthTipEntity> findRandomHealthTipsWithHashtags(final String hashtagName, final int limit) {
		QHealthTipEntity ht = QHealthTipEntity.healthTipEntity;
		QHealthTipHashtagEntity hth = QHealthTipHashtagEntity.healthTipHashtagEntity;
		QHashtagEntity h = QHashtagEntity.hashtagEntity;

		// 해시태그 조건을 만족하는 ID 중 랜덤 N개
		List<Long> ids = queryFactory
				.select(ht.id)
				.from(ht)
				.join(ht.hashtags, hth)
				.join(hth.hashtag, h)
				.where(h.name.eq(hashtagName))
				.orderBy(Expressions.numberTemplate(
						Double.class, "random()").asc())
				.limit(limit)
				.fetch();

		if (ids.isEmpty()) {
			return Collections.emptyList();
		}

		// fetch join으로 연관 엔티티 한방에 로딩
		return queryFactory
				.selectFrom(ht)
				.leftJoin(ht.hashtags, hth).fetchJoin()
				.leftJoin(hth.hashtag, h).fetchJoin()
				.where(ht.id.in(ids))
				.fetch();
	}
}
