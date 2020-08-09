package com.hexad.library.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.hexad.library.enums.Gender;

public class GenderDeserializer extends JsonDeserializer<Gender> {

	@Override
	public Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

		ObjectCodec oc = jsonParser.getCodec();
		JsonNode node = oc.readTree(jsonParser);

		if (node == null) {
			return null;
		}

		int val = node.intValue(); // gives "A" from the request

		if (val == 0) {
			return null;
		}

		return Gender.parse(val);
	}
}