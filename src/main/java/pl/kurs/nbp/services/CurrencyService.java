package pl.kurs.nbp.task.services;

import pl.kurs.nbp.task.enums.Currency;
import pl.kurs.nbp.task.exceptions.EmptyListException;

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

    NbpApiService nbpApiService;

    public CurrencyService() {
        nbpApiService = new NbpApiService();
    }

    public CurrencyService(NbpApiService nbpApiService) {
        this.nbpApiService = nbpApiService;
    }

    public double findMinOfAverageGBPRates(int numberOfDays) {
        List<Double> list = nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays);
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElseThrow(() -> new EmptyListException());
    }

    public double findMaxOfAverageGBPRates(int numberOfDays) {
        List<Double> list = nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays);
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .max()
                .orElseThrow(() -> new EmptyListException());
    }


    public double findAverageOfAverageGBPRates(int numberOfDays) {
        List<Double> list = nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays);
        return Optional.ofNullable(list)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Double::doubleValue)
                .average()
                .orElseThrow(() -> new EmptyListException());
    }


    public double findBiggestRateForCurrencyInPeriod(String currency, LocalDate dateFrom, LocalDate dateTo) {
        double max = nbpApiService.getLastRateForADate(currency, dateFrom);
        LocalDate date = dateFrom;
        while (!date.isEqual(dateTo)) {
            date = date.plusDays(1);
            double res = nbpApiService.getLastRateForADate(currency, date);
            if (res > max) {
                max = res;
            }
        }
        return max;
    }

    public String findWhatIsBetterToBuyInPeriod(String currencyOne, String currencyTwo, LocalDate dateFrom, LocalDate dateTo) {
        double currencyOneRateFromBeggining = nbpApiService.getLastRateForADate(currencyOne, dateFrom);
        double currencyOneRateFromEnd = nbpApiService.getLastRateForADate(currencyOne, dateTo);
        double currencyTwoRateFromBeggining = nbpApiService.getLastRateForADate(currencyTwo, dateFrom);
        double currencyTwoRateFromEnd = nbpApiService.getLastRateForADate(currencyTwo, dateTo);

        double firstDifference = currencyOneRateFromEnd - currencyOneRateFromBeggining;
        double secondDifference = currencyTwoRateFromEnd - currencyTwoRateFromBeggining;
        return firstDifference > secondDifference ? currencyOne : currencyTwo;
    }

    public double fetchExchangeRateForCurrencyForDate(String currency, LocalDate date) {
        LocalDate newDate = date;
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
            newDate = date.plusDays(2);
        } else if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
            newDate = date.plusDays(1);
        }

        return nbpApiService.getLastRateForADate(currency, date);
    }
}
