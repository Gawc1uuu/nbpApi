package nbp.task.service;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AdditionalCurrencyService {

    public String getApiResponse(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder response = new StringBuilder();

            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }

            scanner.close();
            return response.toString();
        } else {
            throw new IOException("API request failed with response code: " + responseCode);
        }
    }

    public double averageMidRateOfGBPCurrencyFromLast10Days(String apiUrl) {
        try {
            String json = getApiResponse(apiUrl);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray ratesArray = jsonObject.getJSONArray("rates");

            double sumOfMidRates = 0.0;
            for (int i = 0; i < ratesArray.length(); i++) {
                JSONObject rateObject = ratesArray.getJSONObject(i);
                double midRate = rateObject.getDouble("mid");
                sumOfMidRates += midRate;
            }

            return sumOfMidRates / ratesArray.length();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isTodayGoodDayToBuyUSD(String apiUrl){
        try {
            String json = getApiResponse(apiUrl);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray ratesArray = jsonObject.getJSONArray("rates");

            if (ratesArray.length() > 0) {
                JSONObject rateObject = ratesArray.getJSONObject(0);
                double bidRate = rateObject.getDouble("bid");

                return bidRate < 4.10;
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean isTodayGoodDayToSellUSD(String apiUrl){
        try {
            String json = getApiResponse(apiUrl);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray ratesArray = jsonObject.getJSONArray("rates");

            if (ratesArray.length() > 0) {
                JSONObject rateObject = ratesArray.getJSONObject(0);
                double askRate = rateObject.getDouble("ask");

                return askRate > 4.20;
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean isAverageGBPMidRateFrom10DaysBiggerThanTodaysRate(String apiUrl){
        try {
            String json = getApiResponse(apiUrl);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray ratesArray = jsonObject.getJSONArray("rates");

            if (ratesArray.length() > 0) {
                JSONObject rateObject = ratesArray.getJSONObject(0);
                double midRate = rateObject.getDouble("mid");

                double averageMidRate = averageMidRateOfGBPCurrencyFromLast10Days("http://api.nbp.pl/api/exchangerates/rates/a/gbp/last/10/?format=json");

                return averageMidRate > midRate;
            }

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
