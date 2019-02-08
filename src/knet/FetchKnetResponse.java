package knet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchKnetResponse {

	private static final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws IOException {
		FetchKnetResponse knet= new FetchKnetResponse();
                String KNET_URL = "http://babvs67.rothamsted.ac.uk:8081/ws/rice/genome?keyword=drought+OR+tolerance&list=ASR3,BADH1";
                
                knet.sendGET(KNET_URL); // http 'get' request
                
                /* LONG TERM: split url into main_url, mode: genome/genepage/countEvidences, etc., keywords (hashmap) and gene_lists (list/map);
                 * For each combination of url, keyword and gene-lists, create the url, do the request and fetch response back;
                 * Parse/filter as shown in sendGET() and iterate/write to different objects/files.
                */
	}

	private static void sendGET(String url) throws IOException {
		URL obj = new URL(url);
                // make request
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuilder response= new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
                                inputLine= inputLine.split(",")[0].split(":")[1];
                                inputLine= inputLine.replace("\"", "").replace("\\t", "\t").replace("\\n", "\n");
				response.append(inputLine);
			}
			in.close();

			// print response
                        String knet_response= response.toString();
			System.out.println("KnetMiner api response: \n \n"+ knet_response);
			System.out.print("\n");
		} else {
			System.out.println("GET request failed...");
		}

	}

}