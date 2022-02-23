package com.bigmwaj.tuto.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import static com.bigmwaj.tuto.config.Constants.*;
import com.bigmwaj.tuto.dto.Student;
import com.bigmwaj.tuto.serializer.StudentSerializer;

public class CustomSerializerProducer {

	public static void main(String[] args) throws Exception {
		KafkaProducer<String, Student> producer = getKafkaProducer(TOPIC_NAME);
		producer.send(new ProducerRecord<String, Student>(TOPIC_NAME, "2-ff-01",
				new Student(10, "Fire and forget sending!")));

		producer.send(new ProducerRecord<String, Student>(TOPIC_NAME, "2-asynch-01",
				new Student(20, "Asynchronized sending!")), new Callback() {
					@Override
					public void onCompletion(RecordMetadata metadata, Exception exception) {
						System.out.println("TestProducer.main(...).new Callback() {...}.onCompletion()");
					}
				});

		producer.send(new ProducerRecord<String, Student>(TOPIC_NAME, "2-synch-01",
				new Student(23, "Synchronized sending!"))).get();

	}

	private static KafkaProducer<String, Student> getKafkaProducer(String topic) {
		Properties props = new Properties();
		props.put(BOOTSTRAP_SERVERS_PARAM_NAME, BOOTSTRAP_SERVERS_VALUE);
		props.put(KEY_SERIALIZER_PARAM_NAME, StringSerializer.class.getName());
		props.put(VALUE_SERIALIZER_PARAM_NAME, StudentSerializer.class.getName());
		return new KafkaProducer<>(props);
	}

}
