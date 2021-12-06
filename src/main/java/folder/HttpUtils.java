/*
 * Here should be licence
 */
package folder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {

    public static String get(String url) throws Exception {
        System.out.println(">get: " + url);
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setInstanceFollowRedirects(false);
        if (con.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
            return ""; // 404 throws FileNotFoundException 
        }
        try ( BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();

        }
    }
}
