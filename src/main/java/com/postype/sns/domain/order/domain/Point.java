package com.postype.sns.domain.order.domain;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class Point {
	private int value;
	public static final int DEFAULT_POINT = 1000; // 1포인트는 1000원

	public Point(int value){
		this.value = value;
	}

	public Point result(){
		return new Point(this.value * DEFAULT_POINT);
	}

	public Point add(Point point){
		return new Point(this.value + point.value);
	}

}
