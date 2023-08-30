package nbp.task.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;

public class NbpApiService {

    private final String HOST_URL = "api.nbp.pl";
    private final String ENDPOINT_URL = "api/exchangerates/rates";

    OkHttpClient okHttpClient;
    private final ObjectMapper mapper;

    public NbpApiService() {
        this.okHttpClient = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }


//    public double callApiForConversion(@NonNull String currencyFrom, @NonNull String currencyTo, @NonNull double amount) {
//        HttpUrl httpUrl = createHttpUrl(currencyFrom, currencyTo, amount);
//        Request request = createRequest(httpUrl, apiKey);
//
//        Response response;
//        try {
//            response = client.newCall(request).execute();
//            ResponseBody responseBody = response.body();
//            if (!response.isSuccessful()) {
//                throw new ApiCallFailedException("API call failed with status: " + response.code());
//            }
//
//            ExchangeResponseBody exResponseBody;
//            try {
//                exResponseBody = objectMapper.readValue(responseBody.string(), ExchangeResponseBody.class);
//                return exResponseBody.getResult();
//            } catch (IOException e) {
//                throw new ResponseDeserializationException("Error while deserializing API response" + e);
//            }
//        } catch (IOException e) {
//            throw new ApiCallFailedException("Error while calling the exchange API" + e);
//        }
//    }

    public double getRateForADate(String currency, LocalDate newDate, String table) {
        HttpUrl httpUrl = createHttpUrl(currency, newDate, table);
        Request request = createRequest(httpUrl);
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String res = response.body().string();
                JsonNode jsonNode = mapper.readTree(res);
                return jsonNode.get("rates").get(0).get("mid").asDouble();
            } else {
                System.out.println("unsuccesful fetch");
                return -1.0;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1.0;
    }

    private HttpUrl createHttpUrl(String currency, LocalDate newDate, String table) {
//        "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + newDate + "/?format=json";
        return new HttpUrl.Builder()
                .scheme("http")
                .host(HOST_URL)
                .addPathSegment("api")
                .addPathSegment("exchangerates")
                .addPathSegment("rates")
                .addPathSegment(table)
                .addPathSegment(currency)
                .addPathSegment(String.valueOf(newDate))
                .addPathSegment("")
                .addQueryParameter("format", "json")
                .build();

    }

    private Request createRequest(HttpUrl httpUrl) {
        return new Request.Builder()
                .url(httpUrl)
                .method("GET", null)
                .build();
    }
}
