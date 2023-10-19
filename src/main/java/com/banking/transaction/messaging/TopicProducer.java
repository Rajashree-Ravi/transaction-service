package com.banking.transaction.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.banking.transaction.model.TransactionDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

	@Value("${producer.config.initiate.topic.name}")
	private String initiateTopicName;

	@Value("${producer.config.success.topic.name}")
	private String successTopicName;

	@Value("${producer.config.failed.topic.name}")
	private String failedTopicName;

	private final KafkaTemplate<String, TransactionDto> kafkaTemplate;

	public void send(TransactionDto transaction) {
		log.info("Payload : {}", transaction.toString());

		if (transaction.getTransactionStatus().equals("Success")) {
			kafkaTemplate.send(successTopicName, transaction);
		} else if (transaction.getTransactionStatus().equals("Failed")) {
			kafkaTemplate.send(failedTopicName, transaction);
		} else if (transaction.getTransactionStatus().equals("Pending")) {
			kafkaTemplate.send(initiateTopicName, transaction);
		}

	}

}