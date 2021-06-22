package com.apps.rh;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.apps.utilities.Utils;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemRequest;
import software.amazon.awssdk.services.dynamodb.model.DeleteItemResponse;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

@Slf4j
public class DeleteMessageRH implements RequestHandler<Map<String, Object>, String> {

	public String handleRequest(Map<String, Object> input, Context context) {

		System.out.println(input);

		log.info("PARAMS: " + input.toString());

		String id = (String) input.get("DELETE_MESSAGE_ID");

		String status = this.deleteMessage(id);

		return status;
	}

	public String deleteMessage(String id) {

		DynamoDbClient ddb = DynamoDbClient.builder()
				.region(Utils.region)
				.credentialsProvider(Utils.getEnvironmentCredentialsProvider())
				.build();

		HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();

		keyToGet.put("messageId", AttributeValue.builder()
				.s(id)
				.build());

		DeleteItemRequest request = DeleteItemRequest.builder()
				.tableName(Utils.MESSAGE_TABLE)
				.key(keyToGet)
				.build();

		try {
			DeleteItemResponse response = ddb.deleteItem(request);

			return "Message was successfully deleted: " + id + ": " + response.toString();

		} catch (ResourceNotFoundException e) {

			e.printStackTrace();

			return "Error ResourceNotFound Encountered: " + e.getMessage();

		} catch (DynamoDbException e) {

			System.err.println(e.getMessage());

			return "Error DynamoDBException Encountered: " + e.getMessage();
		}
	}

}
