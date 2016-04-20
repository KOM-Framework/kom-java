package core.elasticsearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import core.HTTP;

public class ESConnect {
	@SuppressWarnings("rawtypes")
	public static ArrayList<Map> executeQuery(String dbServer, String dbPort, String indexName, String esQuery)
			throws Exception {
		String url = "http://" + dbServer + ":" + dbPort + "/" + indexName + "/_search";
		String response = HTTP.sendPostJSONRequest(url, esQuery);
		ArrayList<Map> out = new ArrayList<Map>();
		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = jfactory.createJsonParser(response);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode input = mapper.readTree(jParser);
		JsonNode results = input.get("hits");
		results = results.get("hits");

		for (final JsonNode element : results) {
			JsonNode fieldname = element.get("_source");
			Iterator<Map.Entry<String, JsonNode>> fieldsIterator = fieldname.getFields();
			Map<String, String> map = new HashMap<String, String>();
			while (fieldsIterator.hasNext()) {
				Map.Entry<String, JsonNode> field = fieldsIterator.next();
				map.put(field.getKey(), field.getValue().toString());
			}
			out.add(map);
		}
		return out;
	}
	
}
