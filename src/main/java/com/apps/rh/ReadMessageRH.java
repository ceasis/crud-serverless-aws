package com.apps.rh;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.apps.utilities.Utils;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Slf4j
public class ReadMessageRH implements RequestHandler<Map<String, String>, List<ReadMessageRH.Message>> {

	public List<ReadMessageRH.Message> handleRequest(Map<String, String> input, Context context) {

		log.info(input.toString());

		return this.readMessage();

	}

	public List<ReadMessageRH.Message> readMessage() {

		DynamoDbClient ddb = DynamoDbClient.builder()
				.region(Utils.region)
				.credentialsProvider(Utils.getEnvironmentCredentialsProvider())
				.build();

		DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
				.dynamoDbClient(ddb)
				.build();

		try {
			DynamoDbTable<Message> msg = enhancedClient.table(Utils.MESSAGE_TABLE, TableSchema.fromBean(Message.class));

			Iterator<Message> itr = msg.scan().items().iterator();

			List<Message> results = new ArrayList<ReadMessageRH.Message>();

			while (itr.hasNext()) {
				Message message = itr.next();
				results.add(message);
				System.out.println("The record id is " + message.toString());
			}

			log.info("Returning " + results.size() + " results");

			return results;

		} catch (DynamoDbException e) {

			System.err.println(e.getMessage());

		}
		return new ArrayList<ReadMessageRH.Message>();
	}

	@DynamoDbBean
	public static class Message {

		private String messageId;
		private String name;
		private String message;

		@DynamoDbPartitionKey
		public String getMessageId() {
			return this.messageId;
		};

		public void setMessageId(String id) {

			this.messageId = id;
		}

		@DynamoDbSortKey
		public String getName() {
			return this.name;

		}

		public void setName(String name) {

			this.name = name;
		}

		public String getMessage() {
			return this.message;
		}

		public void setMessage(String message) {

			this.message = message;
		}

		public String toString() {
			return "\nMessageId: " + this.messageId +
					"\nMessage: " + this.message +
					"\nSender: " + this.name;
		}
	}

}
