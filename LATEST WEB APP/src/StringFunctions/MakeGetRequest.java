package StringFunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;




public class MakeGetRequest {
	
	private final String USER_AGENT = "Mozilla/5.0";
	// HTTP GET request
		public String sendGet(char c, String query) throws Exception {
	 
			query = query.toLowerCase().trim();
			
			URL obj = new URL("http://translate.google.com/translate_a/t?client="+c+"&text="+URLEncoder.encode(query, "UTF-8")+"&sl=en&tl=hi");
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
	 
			// optional default is GET
			con.setRequestMethod("GET");
		
			//add request header
			con.setRequestProperty("User-Agent", USER_AGENT);
	 
	 
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream(),"UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();
	 
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
	 
			//print result
			return response.toString();
	 
		}
	
}
