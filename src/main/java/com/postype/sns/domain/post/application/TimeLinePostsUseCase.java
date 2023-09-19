package com.postype.sns.domain.post.application;

import com.postype.sns.domain.member.dto.MemberDto;
import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.member.domain.util.PageCursor;
import com.postype.sns.domain.post.domain.Post;
import com.postype.sns.domain.post.domain.TimeLine;
import com.postype.sns.domain.post.application.PostService;
import com.postype.sns.domain.post.application.TimeLineService;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeLinePostsUseCase {
	final private PostService postService;
	final private TimeLineService timeLineService;

	public PageCursor<Post> executeTimeLine(MemberDto member, CursorRequest request){//push
		PageCursor<TimeLine> pagedTimeLines = timeLineService.getTimeLine(member.getId(), request);

		List<Long> postIds = pagedTimeLines.getContents().stream()
				.map(TimeLine::getPostId)
				.collect(Collectors.toList());

		List<Post> posts = postService.getPostsByIds(postIds);

		return new PageCursor(pagedTimeLines.getNextCursorRequest(), posts);
	}

}
