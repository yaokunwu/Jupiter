package db.mongodb;

import java.text.ParseException;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;

public class MongoDBTableCreation {
	
	public static void main(String[] args) {
		// step 1. connection to MongoDB
		MongoClient mongoClient = new MongoClient();
		MongoDatabase db = mongoClient.getDatabase(MongoDBUtil.DB_NAME);
		
		// step 2. remove old collection
		db.getCollection("users").drop();
		db.getCollection("items").drop();
		
		// step 3. create new collection
		IndexOptions options = new IndexOptions().unique(true);
		db.getCollection("users").createIndex(new Document("user_id", 1), options);
		db.getCollection("items").createIndex(new Document("item_id", 1), options);
		
		// step 4. insert fake user doc
		db.getCollection("users").insertOne(new Document().append("user_id", "1111").append("password", "123o4ijoijpfda234123").append("first_name", "YYYY").append("last_name", "KKKK"));
		
		mongoClient.close();
		System.out.println("Import is successfully");
	}

}
