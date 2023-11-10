package com.postype.sns.domain.timeline.api;

import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.member.domain.util.PageCursor;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.timeline.application.TimeLinePostsUseCase;
import com.postype.sns.global.common.Response;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/timeline")
@RequiredArgsConstructor
public class TimelineController {

	private final TimeLinePostsUseCase timeLinePostsUseCase;
	@Operation(summary = "타임라인 가져오기", description = "로그인한 멤버의 타임라인 목록을 가져옵니다")
	@GetMapping
	public Response<PageCursor<Post>> getTimeLine(@AuthenticationPrincipal MemberDto memberDto, @RequestBody CursorRequest request){
		return Response.success(timeLinePostsUseCase.getTimeLine(memberDto, request));
	}
}
