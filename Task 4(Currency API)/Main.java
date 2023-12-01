import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import org.json.JSONObject;

public class Main {
    public static void main(String[] args) {
        try {
            // API URL with your API key
            String apiKey = "8e1256d21ccad92a0547ecc30eaa1c11";
            String apiUrl = "http://api.exchangeratesapi.io/v1/latest/" + apiKey;

            // Make an HTTP GET request to fetch exchange rates
            URI uri = new URI(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");


            // Read and parse the JSON response
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONObject json = new JSONObject(response.toString());
                JSONObject rates = json.getJSONObject("rates");

                // Take user input for base currency, target currency, and amount
                try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in))) {
                    System.out.print("Enter the base currency (e.g., USD): ");
                    String baseCurrency = inputReader.readLine().toUpperCase();

                    System.out.print("Enter the target currency (e.g., EUR): ");
                    String targetCurrency = inputReader.readLine().toUpperCase();

                    System.out.print("Enter the amount to convert: ");
                    double amount = Double.parseDouble(inputReader.readLine());

                    // Perform the currency conversion
                    double exchangeRate = rates.getDouble(targetCurrency) / rates.getDouble(baseCurrency);
                    double convertedAmount = amount * exchangeRate;

                    // Display the result
                    System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
