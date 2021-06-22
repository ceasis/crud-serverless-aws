package com.apps.rh;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.apps.utilities.Utils;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

@Slf4j
public class CreateMessageRH implements RequestHandler<Map<String, String>, String> {

	public String handleRequest(Map<String, String> input, Context context) {

		System.out.println(input);

		log.info(input.toString());

		String name = input.get("NAME");
		String message = input.get("MESSAGE");

		this.createMessage(name, message);

		log.info("Successfully created a message: " + message);

		return "Success";
	}

	public void createMessage(String name, String message) {

		String id = UUID.randomUUID().toString();

		DynamoDbClient ddb = DynamoDbClient.builder()
				.region(Utils.region)
				.credentialsProvider(Utils.getEnvironmentCredentialsProvider())
				.build();

		HashMap<String, AttributeValue> itemMap = new HashMap<String, AttributeValue>();

		itemMap.put("messageId", AttributeValue.builder().s(id).build());

		itemMap.put("name", AttributeValue.builder().s(name).build());

		itemMap.put("message", AttributeValue.builder().s(message).build());

		PutItemRequest request = PutItemRequest.builder()
				.tableName(Utils.MESSAGE_TABLE)
				.item(itemMap)
				.build();

		try {
			ddb.putItem(request);

			System.out.println("Message was successfully added: " + name + ": " + message);

		} catch (ResourceNotFoundException e) {

			e.printStackTrace();

		} catch (DynamoDbException e) {

			System.err.println(e.getMessage());

		}
	}

}
