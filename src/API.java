import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

public class API {

    static ArrayList<String> countrySet = new ArrayList<String>();
    
    public static void APICountries() {
        String apiUrl = "https://restcountries.com/v3.1/all";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("HTTP error code : " + conn.getResponseCode());
            }
            
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder json = new StringBuilder();
            
            while ((output = br.readLine()) != null) {
                json.append(output);
            }
            
            conn.disconnect();
            
            Gson gson = new Gson();
            Countries countriesArr[] = gson.fromJson(json.toString(), Countries[].class);
            
            // Use myObj for further processing
            for(int i=0; i< countriesArr.length; i++) {
            	Countries newCountry = countriesArr[i];
            	System.out.println("Common: " + newCountry.name.common);
            	System.out.println("Official: " + newCountry.name.official);

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
