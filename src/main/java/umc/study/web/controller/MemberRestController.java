package umc.study.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayload.ApiResponse;
import umc.study.converter.MemberConverter;
import umc.study.domain.Member;
import umc.study.service.MemberService.MemberCommandService;
import umc.study.service.MemberService.MemberQueryService;
import umc.study.validation.annotation.Page;
import umc.study.web.dto.MemberRequestDTO;
import umc.study.web.dto.MemberResponseDTO;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDto request){
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @GetMapping("/{memberId}/reviews")
    public ApiResponse<MemberResponseDTO.MyReviewListDTO> getMyReviews(
            @PathVariable Long memberId,
            @RequestParam @Page int page) {
        var reviewsPage = memberQueryService.getMyReviews(memberId, page);
        return ApiResponse.onSuccess(MemberConverter.toMyReviewListDTO(reviewsPage));
    }
    @GetMapping("/{memberId}/missions/ongoing")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API", description = "회원이 진행 중인 미션 목록을 페이징 처리하여 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = MemberResponseDTO.OngoingMissionListDTO.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "회원 정보를 찾을 수 없음", content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "memberId", description = "회원의 ID", required = true),
            @Parameter(name = "page", description = "조회할 페이지 번호 (1부터 시작)", required = true)
    })
    public ApiResponse<MemberResponseDTO.OngoingMissionListDTO> getOngoingMissions(
            @PathVariable Long memberId,
            @RequestParam @Page int page) {
        var ongoingMissionPage = memberQueryService.getOngoingMissions(memberId, page);
        return ApiResponse.onSuccess(MemberConverter.toOngoingMissionListDTO(ongoingMissionPage));
    }
}
