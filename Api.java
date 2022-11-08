//import core java http client
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class Api {
    public String API_URL = "https://rightofwaydev.wpengine.com/wp-json/jwt-auth/v1/token";
    public String API_VALIDATION_URL = "https://rightofwaydev.wpengine.com/wp-json/jwt-auth/v1/token/validate";
    //make a post request to the api_url to get a token

    public String getToken() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"username\":\"amaram2@uic.edu\",\"password\":\"Ajayuic?1998\"}";

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            String token = response.toString().split(":")[1].split(",")[0].replace("\"", "");
            return token.toString();
        }
    }

    //make a post request to the api_validation_url to validate the token
    public String validateToken(String token) throws Exception {
        URL url = new URL(API_VALIDATION_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        //set Authorization header to Bearer token
        con.setRequestProperty("Authorization", "Bearer " + token);

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            //print the response
            System.out.println(response.toString());
            return response.toString();
        }
    }





    public static void main(String[] args) throws Exception {
        Api api = new Api();
        String token = api.getToken();
        String validate_response = api.validateToken(token);
        System.out.println(validate_response);
    }


}
