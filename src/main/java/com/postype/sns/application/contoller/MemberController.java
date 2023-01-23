package com.postype.sns.application.contoller;

import com.postype.sns.application.contoller.dto.request.MemberLoginRequest;
import com.postype.sns.application.contoller.dto.request.MemberRegisterRequest;
import com.postype.sns.application.contoller.dto.response.MemberLoginResponse;
import com.postype.sns.application.contoller.dto.response.MemberRegisterResponse;
import com.postype.sns.application.contoller.dto.response.Response;
import com.postype.sns.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/register")
	public Response<MemberRegisterResponse> register(@RequestBody MemberRegisterRequest request){
		return Response.success(
			MemberRegisterResponse.fromMemberDto(memberService.register(request.getMemberId(), request.getPassword(), request.getMemberName(), request.getEmail())));
	}

	@PostMapping("/login")
	public Response<MemberLoginResponse> login(@RequestBody MemberLoginRequest request){
		String token = memberService.login(request.getMemberId(), request.getPassword());
		return Response.success(new MemberLoginResponse(token)); //dto <-> response 변환이 아닌 token값 반환
	}

}