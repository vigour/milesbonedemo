package com.milesbone.kafka;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductTest.class);

	@Test
	public void sendProducerMsg(){
		Properties props = new Properties();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.4.128:19092");
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		try {
			for (int i = 0; i < 10; i++) {
				// ProducerRecord<K, V>
				// K对应Partition Key的类型
				// V对应消息本身的类型
				// topic: "test", key: "key", message: "message"
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss SSS");
				Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
				String curTime = formatter.format(curDate);

				String msg = "milesbone send message:" + i + "=" + curTime;
				String key = new Random().nextLong()+"";
				producer.send(new ProducerRecord<String, String>("singletest", key, msg));
				logger.info("producer send message :{}", msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			producer.close();
		}
	}
	
	

}
