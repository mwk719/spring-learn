package com.mwk.annotation;

import lombok.NonNull;
import org.springframework.lang.Nullable;

/**
 * @author MinWeikai
 * @date 2020/3/28 10:51
 */
public class NullTest {

	public static void main(String[] args) {
		nullable(1);
		nullable(null);

		notNull(2);
		//notNull(null);

		@NonNull Integer b = rtNullable(null);

		System.out.println(b);

	}

	private static void nullable(@Nullable Integer a) {
		System.out.println("允许为空：" + a);
	}

	private static void notNull(@NonNull Integer a) {
		System.out.println("不允许为空：" + a);
	}

	@NonNull
	private static Integer rtNullable(@Nullable Integer a) {
		return a;
	}
}
