package com.postype.sns.unit.service;

import static org.mockito.Mockito.when;

import com.postype.sns.domain.member.domain.util.PageCursor;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.application.PostService;
import com.postype.sns.domain.timeline.application.TimeLinePostsUseCase;
import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.timeline.application.TimeLineService;
import com.postype.sns.domain.timeline.domain.TimeLine;
import com.postype.sns.domain.post.repository.PostRepository;
import com.postype.sns.domain.timeline.repository.TimeLineRepository;
import com.postype.sns.fixture.TimeLineFixture;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TimelineServiceTest {

	@InjectMocks
	private TimeLinePostsUseCase timeLinePostsUseCase;
	@Mock
	private TimeLineRepository timeLineRepository;
	@Mock
	private TimeLineService timeLineService;
	@Mock
	private PostService postService;
	@Mock
	private PostRepository postRepository;

	@Test
	@DisplayName("타임라인 조회 성공 테스트")
	void getTimeLineSuccess(){
		MemberDto memberDto = MemberDto.builder()
				.id(1L)
				.memberId("id")
				.password("password")
				.memberName("name")
				.build();
 		CursorRequest request = new CursorRequest(1L, 4);
		List<TimeLine> timeLineList = List.of(TimeLineFixture.get(memberDto.getId(), 1L));
		PageCursor<TimeLine> pageCursor = new PageCursor<>(request, timeLineList);

		when(timeLineService.getTimeLine(memberDto.getId(), request)).thenReturn(pageCursor);

		Assertions.assertDoesNotThrow(() -> timeLinePostsUseCase.getTimeLine(
				memberDto, request));
	}

}
