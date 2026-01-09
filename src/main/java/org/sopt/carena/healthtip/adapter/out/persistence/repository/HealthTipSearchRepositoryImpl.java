// package org.sopt.carena.healthtip.adapter.out.persistence.repository;
//
// import java.util.List;
//
// import org.sopt.carena.healthtip.adapter.out.persistence.entity.HealthTipEntity;
// import org.sopt.carena.healthtip.adapter.out.persistence.entity.QHashtagEntity;
// import org.sopt.carena.healthtip.adapter.out.persistence.entity.QHealthTipEntity;
// import org.sopt.carena.healthtip.adapter.out.persistence.entity.QHealthTipHashtagEntity;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Slice;
// import org.springframework.data.domain.SliceImpl;
// import org.springframework.stereotype.Repository;
//
// import com.querydsl.jpa.impl.JPAQueryFactory;
//
// import lombok.RequiredArgsConstructor;
//
// @Repository
// @RequiredArgsConstructor
// public class HealthTipSearchRepositoryImpl implements HealthTipSearchRepository {
// 	private final JPAQueryFactory queryFactory;
//
// 	public Slice<HealthTipEntity> getHealthTipListWithHashTags(Pageable pageable) {
// 		QHealthTipEntity qHealthTipEntity = QHealthTipEntity.healthTipEntity;
// 		QHealthTipHashtagEntity qHealthTipHashtagEntity = QHealthTipHashtagEntity.healthTipHashtagEntity;
// 		QHashtagEntity qHashtagEntity = QHashtagEntity.hashtagEntity;
//
// 		List<HealthTipEntity> healthTips = queryFactory.selectDistinct(qHealthTipEntity)
// 				.from(qHealthTipEntity)
// 				.leftJoin(qHealthTipEntity.hashtags, qHealthTipHashtagEntity).fetchJoin()
// 				.leftJoin(qHealthTipHashtagEntity.hashtag, qHashtagEntity).fetchJoin()
// 				.orderBy(qHealthTipEntity.id.desc())
// 				.offset(pageable.getOffset())
// 				.limit(pageable.getPageSize()+1)
// 				.fetch();
//
// 		boolean hasNext = false;
// 		if (healthTips.size() > pageable.getPageSize()) {
// 			hasNext = true;
// 			healthTips.removeLast();
// 		}
//
// 		return new SliceImpl<>(healthTips, pageable, hasNext);
// 	}
// }
