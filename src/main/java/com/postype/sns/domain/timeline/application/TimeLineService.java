package com.postype.sns.domain.timeline.application;

import com.postype.sns.domain.member.domain.util.CursorRequest;
import com.postype.sns.domain.member.domain.util.PageCursor;
import com.postype.sns.domain.timeline.domain.TimeLine;
import com.postype.sns.domain.timeline.repository.TimeLineRepository;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeLineService {

	private final TimeLineRepository timeLineRepository;

	public void deliveryToTimeLine(Long postId, List<Long> toMemberIds){
		List<TimeLine> timelines = toMemberIds.stream()
				.map((memberId) -> new TimeLine(memberId, postId))
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
