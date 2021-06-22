package com.apps.utilities;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

public class Utils {

	public static String KEY = "";
	public static String SECRET = "";
	public static String MESSAGE_TABLE = "crudform-message";
	
	public static Region region = Region.US_EAST_1;

	public static AwsCredentialsProvider getEnvironmentCredentialsProvider() {
		return StaticCredentialsProvider.create(AwsBasicCredentials.create(Utils.KEY, Utils.SECRET));
	}
}
