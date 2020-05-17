package com.hlag.fis.db.attribute;

import java.util.Arrays;
import java.util.Optional;

public enum TimeSign  {
	APPROXIMATE(""),
	AT("="),
	BEFORE("<"),
	AFTER(">"),
	ILLEGAL1("1"),
	ILLEGAL2("2"),
	ILLEGAL("-"),
	ILLEGALY("Y"),
	ILLEGALPLUS("+"),
	ILLEGALMARK("!"),
	ILLEGALMARK2("&"),
	ILLEGALSTAR("*"),
	ILLEGALMARK3("?"),
	ILLEGALMARK4(":"),
	ILLEGALA("A"),
	ILLEGALB("B"),
	ILLEGALS("S"),
	ILLEGAL3("3"),
	ILLEGAL4("4"),
	ILLEGAL9("9"),
	ILLEGAL0("0");

	private String id;

	TimeSign(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public static Optional<TimeSign> tryOfId(String id) {
		return Arrays.stream(values()).filter(pft -> pft.getId().equalsIgnoreCase(id)).findAny();
	}
}
