package nbp.task.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import nbp.task.exceptions.EmptyListException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class CurrencyService {
    // http://api.nbp.pl/api/exchangerates/rates/c/usd/2016-04-04/?format=json
    //  http://api.nbp.pl/api/exchangerates/rates/a/gbp/last/10/?format=json
    /*
        napisz srwis dla kursow, przykladowe metody:
        co lepiej byloby kupic od daty do daty (na cyzm bysmy lepiej zarobili, np czy euro czy dolary)
        znalezc najwiekszy kurs w zakresie dat np dla euro czy dolara
        znalezc srednia srednich kursow GBP
        znalezx max i min sredni kurs GBP

        rozwinac apke: )
     */

    NbpApiService nbpApiService = new NbpApiService();

    public CurrencyService(NbpApiService nbpApiService) {
        this.nbpApiService = nbpApiService;
    }

    public double findMinOfAverageGBPRates(int numberOfDays) {
        List<Double> list = fetchGBPRates(numberOfDays);
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElseThrow(() -> new EmptyListException());
    }

    public double findMaxOfAverageGBPRates(int numberOfDays) {
        List<Double> list = fetchGBPRates(numberOfDays);
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .max()
                .orElseThrow(() -> new EmptyListException());
    }


    public double findAverageOfAverageGBPRates(int numberOfDays) {
        List<Double> list = fetchGBPRates(numberOfDays);
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() -> new EmptyListException());
    }

    public List<Double> fetchGBPRates(int numberOfDays) {
        String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/gbp/last/" + numberOfDays + "/?format=json";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .method("GET", null)
                .build();
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String res = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(res);
                List<Double> list = new ArrayList<>();
//                List<JsonNode> list = jsonNode.get("rates").findValues("mid");
                System.out.println(jsonNode.get("rates"));
                for (JsonNode node : jsonNode.get("rates")) {
                    double rate = node.get("mid").asDouble();
                    list.add(rate);
                }
                System.out.println(list);
                return list;
            } else {
                System.out.println("unsuccesful fetch");
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public double findBiggestRateForCurrencyInPeriod(String currency, LocalDate dateFrom, LocalDate dateTo) throws IOException {
        double max = nbpApiService.getRateForADate(currency, dateFrom, "a");
        LocalDate date = dateFrom;
        while (!date.isEqual(dateTo)) {
            date = date.plusDays(1);
            double res = nbpApiService.getRateForADate(currency, date, "a");
            if (res > max) {
                max = res;
            }
        }
        return max;
    }

    //    public String findWhatIsBetterToBuyInPeriod(String currencyOne, String currencyTwo, LocalDate dateFrom, LocalDate dateTo) throws IOException {
//        double currencyOneRateFromBeggining = fetchExchangeRateForCurrencyForDate(currencyOne, dateFrom);
//        double currencyOneRateFromEnd = fetchExchangeRateForCurrencyForDate(currencyOne, dateTo);
//        double currencyTwoRateFromBeggining = fetchExchangeRateForCurrencyForDate(currencyTwo, dateFrom);
//        double currencyTwoRateFromEnd = fetchExchangeRateForCurrencyForDate(currencyTwo, dateTo);
//
//        double firstDifference = currencyOneRateFromEnd - currencyOneRateFromBeggining;
//        double secondDifference = currencyTwoRateFromEnd - currencyTwoRateFromBeggining;
//        return firstDifference > secondDifference ? currencyOne : currencyTwo;
//    }
    public String findWhatIsBetterToBuyInPeriod(String currencyOne, String currencyTwo, LocalDate dateFrom, LocalDate dateTo) throws IOException {
        double currencyOneRateFromBeggining = nbpApiService.getRateForADate(currencyOne, dateFrom, "a");
        double currencyOneRateFromEnd = nbpApiService.getRateForADate(currencyOne, dateTo, "a");
        double currencyTwoRateFromBeggining = nbpApiService.getRateForADate(currencyTwo, dateFrom, "a");
        double currencyTwoRateFromEnd = nbpApiService.getRateForADate(currencyTwo, dateTo, "a");

        double firstDifference = currencyOneRateFromEnd - currencyOneRateFromBeggining;
        double secondDifference = currencyTwoRateFromEnd - currencyTwoRateFromBeggining;
        return firstDifference > secondDifference ? currencyOne : currencyTwo;
    }

//    public double fetchExchangeRateForCurrencyForDate(String currency, LocalDate date) throws IOException {
//        LocalDate newDate = date;
//        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
//            newDate = date.plusDays(2);
//        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
//            newDate = date.plusDays(1);
//        }
//        String apiUrl = "http://api.nbp.pl/api/exchangerates/rates/a/" + currency + "/" + newDate + "/?format=json";
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url(apiUrl)
//                .method("GET", null)
//                .build();
//        try (Response response = okHttpClient.newCall(request).execute()) {
//            if (response.isSuccessful()) {
//                String res = response.body().string();
//                ObjectMapper mapper = new ObjectMapper();
//                JsonNode jsonNode = mapper.readTree(res);
//                return jsonNode.get("rates").get(0).get("mid").asDouble();
//            } else {
//                System.out.println("unsuccesful fetch");
//                return -1.0;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return -1.0;
//    }
}
