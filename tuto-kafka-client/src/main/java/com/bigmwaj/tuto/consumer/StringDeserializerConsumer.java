package com.bigmwaj.tuto.consumer;

import static com.bigmwaj.tuto.config.Constants.AUTO_OFFSET_RESET_LATEST;
import static com.bigmwaj.tuto.config.Constants.AUTO_OFFSET_RESET_PARAM_NAME;
import static com.bigmwaj.tuto.config.Constants.BOOTSTRAP_SERVERS_PARAM_NAME;
import static com.bigmwaj.tuto.config.Constants.BOOTSTRAP_SERVERS_VALUE;
import static com.bigmwaj.tuto.config.Constants.GROUP_ID_PARAM_NAME;
import static com.bigmwaj.tuto.config.Constants.GROUP_ID_VALUE;
import static com.bigmwaj.tuto.config.Constants.KEY_DESERIALIZER_PARAM_NAME;
import static com.bigmwaj.tuto.config.Constants.TOPIC_NAME;
import static com.bigmwaj.tuto.config.Constants.VALUE_DESERIALIZER_PARAM_NAME;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class StringDeserializerConsumer {
	
    public static void main(String[] args) throws Exception {
    	consumeFromKafka(TOPIC_NAME);
    }
    
    private static void consumeFromKafka(String topic) {
    	Properties props = new Properties();
    	props.put(BOOTSTRAP_SERVERS_PARAM_NAME, BOOTSTRAP_SERVERS_VALUE);
    	props.put(KEY_DESERIALIZER_PARAM_NAME, StringDeserializer.class.getName());
    	props.put(VALUE_DESERIALIZER_PARAM_NAME, StringDeserializer.class.getName());
    	props.put(AUTO_OFFSET_RESET_PARAM_NAME, AUTO_OFFSET_RESET_LATEST);
    	props.put(GROUP_ID_PARAM_NAME, GROUP_ID_VALUE);
    	
//    	Map<TopicPartition, OffsetAndMetadata> consumerOffset = new HashMap<>();
    	KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    	consumer.subscribe(Arrays.asList(topic));
    	try {
    		while(true) {
    			System.out.println("Consumer ...");
    			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
    			for (ConsumerRecord<String, String> record : records) {
					System.out.println(String.format("Key=%s, value=%s", record.key(), record.value()));
				}
    		}
    	} catch(Exception ex) {
    		ex.printStackTrace();
		} finally {
			consumer.close();
		}
    }
}
