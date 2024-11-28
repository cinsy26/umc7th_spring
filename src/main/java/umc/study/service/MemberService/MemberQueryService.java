package umc.study.service.MemberService;

import org.springframework.data.domain.Page;
import umc.study.domain.Member;
import umc.study.domain.Review;
import umc.study.domain.mapping.MemberMission;

import java.util.Optional;

public interface MemberQueryService {

    Optional<Member> findMember(Long id);

    Page<Review> getMyReviews(Long memberId, int page);
    Page<MemberMission> getOngoingMissions(Long memberId, int page);

}
