package com.bigmwaj.tuto.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.bigmwaj.tuto.dto.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StudentSerializer implements Serializer<Student> {

	@Override
	public byte[] serialize(String topic, Student data) {
		try {
			return new ObjectMapper().writeValueAsBytes(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
}