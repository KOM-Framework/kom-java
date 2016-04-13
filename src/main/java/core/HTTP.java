package core;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.Base64;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;

public class HTTP {

	private final static String USER_AGENT = "Mozilla/5.0";
	
	public static ArrayList<String> outResults= new ArrayList<String>();

	public static String sendGet(String url, String request) throws Exception {
		String fullURL=url+"?"+request;
		System.out.println(fullURL);
		HttpURLConnection con = (HttpURLConnection)  new URL(fullURL).openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	;
	public static String sendPost(String url, String request, boolean isEncode, int connectTimeOut, int readTimeOut) throws Exception{
		DataOutputStream wr;
		String resp = "";
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

		con.setRequestMethod("POST");
		if(isEncode)
			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		con.setRequestProperty("Content-Language", "en-US");

		con.setConnectTimeout(connectTimeOut);
		con.setReadTimeout(readTimeOut);

		con.setDoInput(true);
		con.setDoOutput(true);
		wr = new DataOutputStream(con.getOutputStream());

		wr.writeBytes(request);
		wr.flush();
		wr.close();

		InputStream is = con.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		String line;
		StringBuffer response = new StringBuffer();

		while ((line = rd.readLine()) != null) {
			response.append(line);
		}
		rd.close();

		resp = response.toString();
		return resp;
	}
	
	public static ArrayList<String> sendTransactions(final String url, final String request, int numberOfTransactions, int threadsNumber, final String regExtToParseResponds) throws Exception {
		final int transactionToSendInThread=numberOfTransactions/threadsNumber;
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 1; i <= threadsNumber; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < transactionToSendInThread; i++) {
						try {
							String respond = HTTP.sendGet(url,request);
							System.out.println(respond);
							Pattern pattern = Pattern.compile(regExtToParseResponds);
							Matcher matcher = pattern.matcher(respond);
							matcher.find();
							outResults.add(matcher.group(1));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
		es.shutdown();
		try {
			es.awaitTermination(20, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return outResults;
	}
	
	public static String sendPostJSONRequest(String url, String request) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Content-Type", "application/json");
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(request);
		wr.flush();
		wr.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		ObjectMapper mapper = new ObjectMapper();
		Object json = mapper.readValue(response.toString(), Object.class);
		String indented = mapper.writeValueAsString(json);
		return indented;
	}
	
	public static JSONObject sendGetRESTJSONRequest(String url, String authentication) {
		try {
			ClientResponse response;
			Client client = Client.create();
			WebResource webResource = client.resource(url);

			if (!authentication.isEmpty())
				response = webResource.header("Authorization", "Basic " + new String(Base64.encode(authentication)))
						.type("application/json").accept("application/json").get(ClientResponse.class);
			else
				response = webResource.type("application/json").accept("application/json").get(ClientResponse.class);

			if (response.getStatus() != 200) {
				Log.info("Exceprtion happens when sending REST request: " + url + ".");
				return null;
			}

			JSONObject json;

			json = new JSONObject(response.getEntity(String.class));

			return json;
		} catch (ClientHandlerException | UniformInterfaceException | JSONException e) {
			// TODO Auto-generated catch block
			Log.info("Exceprtion happens when sending REST request: " + url + ".");
			return null;
		}
	}
	
	
	//not finished
	public static boolean sendPutRESTJSONRequest(String url, String authentication, JSONObject putObject) {
		boolean isFinished = true;
		try {
			ClientResponse response;
			Client client = Client.create();
			WebResource webResource = client.resource(url);

			if (!authentication.isEmpty())
				response = webResource.header("Authorization", "Basic " + new String(Base64.encode(authentication)))
						.type("application/json").accept("application/json").put(ClientResponse.class, putObject.toString());
			else
				response = webResource.type("application/json").accept("application/json").put(ClientResponse.class, putObject.toString());

			if (response.getStatus() != 200) {
				Log.info("Exceprtion happens when sending REST request: " + url + ".");
				isFinished=false;
			}

			
		} catch (ClientHandlerException | UniformInterfaceException e) {
			// TODO Auto-generated catch block
			Log.info("Exceprtion happens when sending REST request: " + url + ".");
			isFinished=false;
		}
		
		return isFinished;
	}
	//not finished
}

