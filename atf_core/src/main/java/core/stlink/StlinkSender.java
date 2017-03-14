package core.stlink;

import core.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class StlinkSender {
	
	public static StringOut send(String stlinkUrl, StringIn stringIn) {
		byte[] sendBytes = stringIn.build().getBytes(StandardCharsets.ISO_8859_1);
		
		try {
			URL obj = new URL(stlinkUrl);
			
			HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", "ISO-8859-1");
			conn.setRequestProperty("Content-Length", String.valueOf(sendBytes.length));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			
			try (OutputStream os = conn.getOutputStream()) {
				os.write(sendBytes);
				Log.info("Sending transaction to Stlink [" + stlinkUrl + "].");
				Log.info("StringIn:\n" + stringIn.buildMasked());
			}
			
			StringBuffer response = new StringBuffer();
			try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
			}
			
			StringOut stringOut = new StringOut(response.toString(), stringIn.getFormat());
			Log.info("StringOut:\n" + stringOut.build());
			
			return stringOut;
		} catch (Exception e) {
			Log.error(e.toString());
		}
		
		return null;
	}
	
}
