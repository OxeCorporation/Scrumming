package br.com.scrumming.core.infra.util;

import java.io.IOException;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JodaDateTimeJsonSerializer extends JsonSerializer<DateTime> {
	@Override
	public void serialize(DateTime value, JsonGenerator gen,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		gen.writeString(value.toString());
	}
}
