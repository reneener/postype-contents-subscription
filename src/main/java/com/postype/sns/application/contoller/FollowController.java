package com.postype.sns.application.contoller;

import com.postype.sns.application.contoller.dto.response.FollowResponse;
import com.postype.sns.application.contoller.dto.response.Response;
import com.postype.sns.application.contoller.dto.FollowDto;
import com.postype.sns.application.contoller.dto.MemberDto;
import com.postype.sns.application.exception.ApplicationException;
import com.postype.sns.application.exception.ErrorCode;
import com.postype.sns.domain.member.service.FollowService;
import com.postype.sns.domain.member.service.MemberService;
import com.postype.sns.utill.ClassUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/follow")
@RequiredArgsConstructor
public class FollowController {

	private final FollowService followService;
	private final MemberService memberService;

	@PostMapping("/{toMemberName}")
	public Response<FollowResponse> create(@AuthenticationPrincipal MemberDto fromMember, @PathVariable String toMemberName){
		MemberDto toMember = memberService.getMember(toMemberName);
		FollowDto dto = followService.create(fromMember, toMember);
		return Response.success(FollowResponse.fromFollowDto(dto));
	}

	//fromId가 팔로잉 하고 있는 목록 확인할 수 있음
	@GetMapping
	public Response<Page<FollowResponse>> getFollowList(@AuthenticationPrincipal MemberDto memberDto, Pageable pageable){
		return Response.success(followService.getFollowList(memberDto, pageable).map(FollowResponse::fromFollowDto));
	}

}
