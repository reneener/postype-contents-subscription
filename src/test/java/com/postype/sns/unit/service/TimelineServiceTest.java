package com.postype.sns.unit.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.post.application.TimeLinePostsUseCase;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.member.repository.MemberRepository;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.domain.TimeLine;
import com.postype.sns.domain.post.repository.PostRepository;
import com.postype.sns.domain.post.repository.TimeLineRepository;
import com.postype.sns.fixture.MemberFixture;
import com.postype.sns.fixture.TimeLineFixture;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class TimelineServiceTest {

	@Autowired
	private TimeLinePostsUseCase timeLinePostsUseCase;

	@MockBean
	private TimeLineRepository timeLineRepository;

	@MockBean
	private MemberRepository memberRepository;

	@MockBean
	private PostRepository postRepository;

	@Test
	@DisplayName("포스트 저장 시 타임라인 테이블 저장 성공 테스트")
	void saveTimeLineSuccess(){
		Long postId = 1L;
		String memberId = "member";
		long id = 1L;

		Member member = MemberFixture.get(memberId, "password", id);
		List<Long> ids = new ArrayList<>();
		ids.add(2L);
		TimeLine timeLine = TimeLineFixture.get(id, postId);

		//mocking
		when(memberRepository.findByMemberId(memberId)).thenReturn(Optional.of(member));
		when(timeLineRepository.findAllByMemberIdAndOrderByIdDesc(id, 10)).thenReturn((List.of(timeLine)));
		when(postRepository.findAllByInId(ids)).thenReturn(List.of(mock(Post.class)));

		Assertions.assertDoesNotThrow(() -> timeLinePostsUseCase.executeTimeLine(
			MemberDto.fromEntity(member),
			mock(CursorRequest.class)));
	}




}
