package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.Review;
import umc.study.domain.enums.Gender;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberConverter {

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequestDTO.JoinDto request){

        Gender gender = null;

        switch (request.getGender()){
            case 1:
                gender = Gender.MALE;
                break;
            case 2:
                gender = Gender.FEMALE;
                break;
            case 3:
                gender = Gender.NONE;
                break;
        }

        return Member.builder()
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .gender(gender)
                .name(request.getName())
                .memberPreferList(new ArrayList<>())
                .build();
    }

    public static MemberResponseDTO.MyReviewDTO toMyReviewDTO(Review review) {
        return MemberResponseDTO.MyReviewDTO.builder()
                .score(review.getScore())
                .reviewBody(review.getBody())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }

    public static MemberResponseDTO.MyReviewListDTO toMyReviewListDTO(Page<Review> reviews) {
        List<MemberResponseDTO.MyReviewDTO> reviewDTOList = reviews.stream()
                .map(MemberConverter::toMyReviewDTO)
                .collect(Collectors.toList());

        return MemberResponseDTO.MyReviewListDTO.builder()
                .reviewList(reviewDTOList)
                .listSize(reviewDTOList.size())
                .totalPage(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .isFirst(reviews.isFirst())
                .isLast(reviews.isLast())
                .build();
    }

    public static MemberResponseDTO.OngoingMissionDTO toOngoingMissionDTO(MemberMission memberMission) {
        Mission mission = memberMission.getMission();
        return MemberResponseDTO.OngoingMissionDTO.builder()
                .title(mission.getTitle())
                .description(mission.getMissionSpec())
                .deadline(mission.getDeadline())
                .reward(mission.getReward())
                .build();
    }

    public static MemberResponseDTO.OngoingMissionListDTO toOngoingMissionListDTO(Page<MemberMission> memberMissions) {
        List<MemberResponseDTO.OngoingMissionDTO> missionDTOs = memberMissions.stream()
                .map(MemberConverter::toOngoingMissionDTO)
                .collect(Collectors.toList());

        return MemberResponseDTO.OngoingMissionListDTO.builder()
                .missions(missionDTOs)
                .listSize(missionDTOs.size())
                .totalPage(memberMissions.getTotalPages())
                .totalElements(memberMissions.getTotalElements())
                .isFirst(memberMissions.isFirst())
                .isLast(memberMissions.isLast())
                .build();
    }
}
