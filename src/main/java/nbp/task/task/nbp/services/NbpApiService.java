package nbp.task.task.nbp.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import nbp.task.task.nbp.enums.NbpTables;
import nbp.task.task.nbp.exceptions.CannotExtractDataFromResponseBodyException;
import nbp.task.task.nbp.exceptions.ResponseIsNotSuccessfulException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NbpApiService {

    private final String HOST_URL = "api.nbp.pl";
    private final String RATES_ENDPOINT_URL = "rates";

    OkHttpClient okHttpClient;
    private final ObjectMapper mapper;

    public NbpApiService() {
        this.okHttpClient = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }

    public double getLastRateForADate(@NonNull String currency, @NonNull LocalDate newDate) {
        HttpUrl httpUrl = createHttpUrlForRates(currency.toLowerCase(), newDate, NbpTables.A.getTableName());
        Request request = createRequest(httpUrl);
        Response response = createRequest(request);

        try {
            String res = response.body().string();
            JsonNode jsonNode = mapper.readTree(res);
            return jsonNode.get("rates").get(0).get("mid").asDouble();

        } catch (IOException e) {
            throw new CannotExtractDataFromResponseBodyException(e.toString());
        }
    }

    public List<Double> getListOfRates(@NonNull String currency, @NonNull int days) {
        HttpUrl httpUrl = createHttpUrlForLastDays(currency.toLowerCase(), String.valueOf(days), NbpTables.A.getTableName());
        Request request = createRequest(httpUrl);

        Response response = createRequest(request);

        try {
            String res = response.body().string();
            JsonNode jsonNode = mapper.readTree(res);
            List<Double> list = new ArrayList<>();
            System.out.println(jsonNode.get("rates"));
            for (JsonNode node : jsonNode.get("rates")) {
                double rate = node.get("mid").asDouble();
                list.add(rate);
            }
            System.out.println(list);
            return list;
        } catch (IOException e) {
            throw new CannotExtractDataFromResponseBodyException(e.toString());
        }
    }


    private Response createRequest(Request request) {
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                return response;
            } else {
                throw new ResponseIsNotSuccessfulException();
            }
        } catch (IOException e) {
            throw new ResponseIsNotSuccessfulException(e.toString());
        }

    }

    private HttpUrl createHttpUrlForLastDays(String currency, String days, String table) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host(HOST_URL)
                .addPathSegment("api")
                .addPathSegment("exchangerates")
                .addPathSegment(RATES_ENDPOINT_URL)
                .addPathSegment(table)
                .addPathSegment(currency)
                .addPathSegment("last")
                .addPathSegment(days)
                .addPathSegment("")
                .addQueryParameter("format", "json")
                .build();
    }

    private HttpUrl createHttpUrlForRates(String currency, LocalDate newDate, String table) {
        return new HttpUrl.Builder()
                .scheme("http")
                .host(HOST_URL)
                .addPathSegment("api")
                .addPathSegment("exchangerates")
                .addPathSegment(RATES_ENDPOINT_URL)
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
