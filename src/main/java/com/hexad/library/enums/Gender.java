package com.hexad.library.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hexad.library.util.GenderDeserializer;

@JsonDeserialize(using = GenderDeserializer.class)
public enum Gender {

	MALE(1),

	FEMALE(2);

	private int value;

	@JsonValue
	public int getValue() {
		return value;
	}

	Gender(int value) {
		this.value = value;
	}

	@JsonCreator
	public static Gender parse(int value) {
		for (Gender e : Gender.values()) {
			if (e.getValue() == value)
				return e;
		}
		return Gender.MALE;
	}

	@Override
	public String toString() {
		return this.name();
	}

}
