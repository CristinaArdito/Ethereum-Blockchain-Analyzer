import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class Utils {

	/**
	 * Connect to a mongodb server
	 * 
	 * @param host
	 * @param port
	 * @return the client
	 */
	public static MongoClient connect(String host, int port) {
		MongoClient mongoClient = new MongoClient(host, port);
		return mongoClient;
	}

	/**
	 * Query a mongo database
	 * 
	 * @param db
	 *            target
	 * @param collection
	 *            chosen collection
	 * @param find
	 *            querystring
	 * @param sort
	 *            sort sring (can be null)
	 * @param limit
	 *            maximum number of results (can be null)
	 * @return a map of results
	 */
	public static FindIterable<org.bson.Document> query(MongoDatabase db, String collection, Bson find,
			BasicDBObject sort, int limit) {

		FindIterable<org.bson.Document> iterable = find != null ? db.getCollection(collection).find(find)
				: db.getCollection(collection).find();
		if (sort != null)
			iterable.sort(sort);
		if (limit != -1)
			iterable.limit(limit);
		return iterable;

	}

	/**
	 * Disconnect the client
	 * 
	 * @param client
	 */
	public static void disconnect(MongoClient client) {
		client.close();
	}

	/**
	 * Ottiene il numero corrente di blocchi presenti nella Blockchain
	 * 
	 * @return line - numbero di blocchi
	 * @throws Exception
	 */
	public static int getBlocksNumber(String driver) {
		URL website;
		int line = 0;
		if (driver.equalsIgnoreCase("INTERNET")) {
			try {
				website = new URL("https://etherscan.io/blocks");

				URLConnection connection;

				connection = website.openConnection();

				connection.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
				connection.connect();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String[] splited = null;
				String inputLine;
				boolean flag = false;
				int i = 0;

				while ((inputLine = in.readLine()) != null) {
					splited = inputLine.split("\\s+");
					for (String part : splited) {
						if (part.contains("Showing") == true) {
							flag = true;
							i = 0;
						}
						i++;
						if (i == 3 && flag == true) {
							line = Integer.parseInt(part.substring(2, part.length()));
							break;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (driver.equalsIgnoreCase("MONGODB")) {
			MongoClient client = Utils.connect("localhost", 6666);
			FindIterable<org.bson.Document> iterable = Utils.query(client.getDatabase("blockchain"), "transactions",
					null, new BasicDBObject("number", -1), 1);

			for (org.bson.Document doc : iterable) {
				JSONObject json = new JSONObject(doc.toJson());
				line = json.getInt(("number"));
			}

			disconnect(client);
		}
		return line;
	}

}
