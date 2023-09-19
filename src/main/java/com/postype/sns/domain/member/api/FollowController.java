package com.postype.sns.domain.member.api;

import com.postype.sns.domain.member.dto.response.FollowResponse;
import com.postype.sns.global.common.Response;
import com.postype.sns.domain.member.dto.FollowDto;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.application.FollowService;
import com.postype.sns.domain.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
//TODO :: 팔로우 취소
public class FollowController {

	private final FollowService followService;
	private final MemberService memberService;

	@Operation(summary = "팔로우", description = "로그인한 멤버가 toMemberName을 팔로우 합니다")
	@PostMapping("/{toMemberName}")
	public Response<FollowResponse> create(@ApiIgnore @AuthenticationPrincipal MemberDto fromMember, @PathVariable String toMemberName){
		MemberDto toMember = memberService.getMember(toMemberName);
		FollowDto dto = followService.create(fromMember, toMember);
		return Response.success(FollowResponse.fromFollowDto(dto));
	}

	//fromId가 팔로잉 하고 있는 목록 확인할 수 있음
	@Operation(summary = "팔로잉 리스트 가져오기", description = "로그인한 멤버의 팔로잉 리스트를 가져옵니다")
	@GetMapping
	public Response<Page<FollowResponse>> getFollowList(@ApiIgnore @AuthenticationPrincipal MemberDto memberDto, Pageable pageable){
		return Response.success(
				followService.getFollowList(memberDto, pageable)
						.map(FollowResponse::fromFollowDto));
	}

}
