package com.postype.sns.domain.post.service;

import com.postype.sns.domain.member.model.util.CursorRequest;
import com.postype.sns.domain.member.model.util.PageCursor;
import com.postype.sns.domain.post.model.Post;
import com.postype.sns.domain.post.model.TimeLine;
import com.postype.sns.domain.post.repository.TimeLineRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeLineService {

	private final TimeLineRepository timeLineRepository;

	public void deliveryToTimeLine(Long postId, List<Long> toMemberIds){
		List<TimeLine> timelines = toMemberIds.stream()
				.map((memberId) -> TimeLine.builder().memberId(memberId).postId(postId).build())
				.collect(Collectors.toList());

		timeLineRepository.saveAll(timelines);
	}

	public PageCursor<TimeLine> getTimeLine(Long memberId, CursorRequest request){
		List<TimeLine> timeLines = findAllByMemberId(memberId, request);
		Long nextKey = timeLines.stream()
			.mapToLong(TimeLine::getId)
			.min()
			.orElse(CursorRequest.DEFAULT_KEY);

		return new PageCursor<>(request.next(nextKey), timeLines);
	}
	public List<TimeLine> findAllByMemberId(Long memberId, CursorRequest request){
		if(request.hasKey()){
			return timeLineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(request.getKey(), memberId,
				request.getSize());
		}
		return timeLineRepository.findAllByMemberIdAndOrderByIdDesc(memberId, request.getSize());
	}


}
