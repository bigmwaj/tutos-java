package com.bigmwaj.tuto.producer;

import static com.bigmwaj.tuto.config.Constants.BOOTSTRAP_SERVERS_PARAM_NAME;
import static com.bigmwaj.tuto.config.Constants.BOOTSTRAP_SERVERS_VALUE;
import static com.bigmwaj.tuto.config.Constants.KEY_SERIALIZER_PARAM_NAME;
import static com.bigmwaj.tuto.config.Constants.TOPIC_NAME;
import static com.bigmwaj.tuto.config.Constants.VALUE_SERIALIZER_PARAM_NAME;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerWithAvroSerializer {
	
	private static Schema getSchema() throws IOException {
		InputStream is = ProducerWithAvroSerializer.class.getClassLoader().getResourceAsStream("student.avsc");
    	return new Schema.Parser().parse(is);
	}
	
	private static byte[] getRecord(Schema schema, int age, String name) throws IOException {
		GenericRecord record = new GenericData.Record(schema);
		record.put("age", age);
		record.put("name", name);
		
		SpecificDatumWriter<GenericRecord> writer = new SpecificDatumWriter<>(schema);
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
    	
    	writer.write(record, encoder);
    	encoder.flush();
    	out.close();
    	
		return out.toByteArray();
	}
	
    public static void main(String[] args) throws Exception {
    	Schema schema = getSchema();
    	KafkaProducer<String, byte[]> producer = getKafkaProducer(TOPIC_NAME);
    	
    	producer.send(new ProducerRecord<String, byte[]>(TOPIC_NAME, "2-ff-01", getRecord(schema, 10, "Fire and forget sending!")));
    	
    	producer.send(new ProducerRecord<String, byte[]>(TOPIC_NAME, "2-asynch-01", getRecord(schema, 20, "Asynchronized sending!")), new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				System.out.println("TestProducer.main(...).new Callback() {...}.onCompletion()");
			}
		});

    	producer.send(new ProducerRecord<String, byte[]>(TOPIC_NAME, "2-synch-01", getRecord(schema, 213, "Synchronized sending!"))).get();
    }
    
    private static KafkaProducer<String, byte[]> getKafkaProducer(String topic) {
    	Properties props = new Properties();
    	props.put(BOOTSTRAP_SERVERS_PARAM_NAME, BOOTSTRAP_SERVERS_VALUE);
    	props.put(KEY_SERIALIZER_PARAM_NAME, StringSerializer.class.getName());
    	props.put(VALUE_SERIALIZER_PARAM_NAME, ByteArraySerializer.class.getName());
    	return new KafkaProducer<>(props);
    }
}
