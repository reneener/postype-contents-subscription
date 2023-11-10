package com.postype.sns.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postype.sns.domain.member.domain.Member;
import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.member.domain.util.PageCursor;
import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.timeline.application.TimeLinePostsUseCase;
import com.postype.sns.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TimelineControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private TimeLinePostsUseCase timeLinePostsUseCase;

	@Test
	@WithMockUser //인증 된 유저
	@DisplayName("타임라인 조회 성공 테스트")
	void getTimeLineSuccess() throws Exception {
		CursorRequest request = new CursorRequest(1L, 4);
		Member requestedMember = MemberFixture.get();

		when(timeLinePostsUseCase.getTimeLine(MemberDto.fromEntity(requestedMember), request))
				.thenReturn(mock(PageCursor.class));

		mockMvc.perform(get("/api/v1/timeline")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsBytes(request))
				).andDo(print())
				.andExpect(status().isOk());
	}
	@Test
	@WithAnonymousUser //인증 되지 않은 유저
	@DisplayName("타임라인 조회 시 로그인 하지 않은 경우 실패 테스트")
	void getTimeLineFailCausedByNotLogin() throws Exception {
		mockMvc.perform(get("/api/v1/timeline")
				.contentType(MediaType.APPLICATION_JSON)
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}
}
