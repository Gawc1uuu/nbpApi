package nbp.task;

import nbp.task.service.CurrencyService;
import nbp.task.service.NbpApiService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {
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
    public static void main(String[] args) throws IOException {

        NbpApiService nbpApiService = new NbpApiService();
        CurrencyService service = new CurrencyService(nbpApiService);

        System.out.println(service.findWhatIsBetterToBuyInPeriod("eur", "usd", LocalDate.of(2020, 6, 1), LocalDate.now()));
//        System.out.println(service.fetchExchangeRateForCurrencyForDate("usd", LocalDate.now()));
//        System.out.println(service.findWhatIsBetterToBuyInPeriod("eur", "usd", LocalDate.of(2020, 6, 1), LocalDate.now()));
//        System.out.println(service.findBiggestRateForCurrencyInPeriod("usd", LocalDate.of(2023, 8, 24), LocalDate.now()));
//
//        System.out.println(service.findAverageOfAverageGBPRates(10));
//        System.out.println(service.findMaxOfAverageGBPRates(10));
//        System.out.println(service.findMinOfAverageGBPRates(10));
    }
}
