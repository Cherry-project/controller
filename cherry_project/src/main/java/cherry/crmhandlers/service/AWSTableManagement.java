package cherry.crmhandlers.service;
import java.util.Map;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

//TEST TO HANDLE TABLE FROM SERVER
//TO BE CONTINUED ?

public class AWSTableManagement {

    public static void scanTable() throws Exception {

    	
    	AmazonDynamoDBClient client = new AmazonDynamoDBClient(
    		    new ProfileCredentialsProvider());

		ScanRequest scanRequest = new ScanRequest()
		    .withTableName("Users");

		ScanResult result = client.scan(scanRequest);
		
		for (Map<String, AttributeValue> item : result.getItems()){
			System.out.println("Success.  Table scan" + item.toString());
		}
    		
        /*AmazonDynamoDBClient client = new AmazonDynamoDBClient()
            .withEndpoint("http://localhost:8000")
        	.withRegion(Regions.US_WEST_2);
        
        DynamoDB dynamoDB = new DynamoDB(client);

        String tableName = "Movies";

        try {
            System.out.println("Attempting to create table; please wait...");
            Table table = dynamoDB.createTable(tableName,
                Arrays.asList(
                    new KeySchemaElement("year", KeyType.HASH),  //Partition key
                    new KeySchemaElement("title", KeyType.RANGE)), //Sort key
                    Arrays.asList(
                        new AttributeDefinition("year", ScalarAttributeType.N),
                        new AttributeDefinition("title", ScalarAttributeType.S)), 
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            System.out.println("Success.  Table status: " + table.getDescription().getTableStatus());

        } catch (Exception e) {
            System.err.println("Unable to create table: ");
            System.err.println(e.getMessage());
        }*/

    }
}