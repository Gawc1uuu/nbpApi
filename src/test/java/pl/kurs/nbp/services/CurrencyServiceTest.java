package nbp.task.services;

import pl.kurs.nbp.task.enums.Currency;
import pl.kurs.nbp.task.exceptions.EmptyListException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import pl.kurs.nbp.task.services.CurrencyService;
import pl.kurs.nbp.task.services.NbpApiService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

public class CurrencyServiceTest {

    @Mock
    NbpApiService nbpApiService;
    private CurrencyService currencyService;
    private List<Double> avgGBPRates;
    private int numberOfDays;

    @Before
    public void init() {
        nbpApiService = Mockito.mock(NbpApiService.class);
        currencyService = new CurrencyService(nbpApiService);
        avgGBPRates = new ArrayList<>(Arrays.asList(5.1457, 5.2091, 5.224, 5.2515, 5.2317, 5.2306, 5.2539, 5.2291, 5.2128, 5.2065));
        numberOfDays = 10;
    }

    @Test
    public void shouldReturnEURWhenTryingToFindWhatIsBetterToBuyInPeriod() throws IOException {
        LocalDate dateFrom = LocalDate.of(2023, 8, 1);
        LocalDate dateTo = LocalDate.of(2023, 8, 15);

        Mockito.when(nbpApiService.getLastRateForADate(Currency.USD.getCode(), dateFrom)).thenReturn(3.5);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.USD.getCode(), dateTo)).thenReturn(3.7);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.USD.getCode(), dateFrom)).thenReturn(4.2);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.USD.getCode(), dateTo)).thenReturn(4.5);
        String result = currencyService.findWhatIsBetterToBuyInPeriod(Currency.USD.getCode(), Currency.USD.getCode(), dateFrom, dateTo);
        assertEquals(Currency.USD.getCode(), result);
    }

    @Test
    public void shouldReturnCorrectAverage() {
        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(avgGBPRates);
        double result = currencyService.findAverageOfAverageGBPRates(numberOfDays);
        assertEquals(5.219, result, 0.001);
    }

    @Test
    public void shouldReturnCorrectMaxElement() {
        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(avgGBPRates);
        double result = currencyService.findMaxOfAverageGBPRates(numberOfDays);
        assertEquals(5.2539, result, 0.001);
    }

    @Test
    public void shouldReturnCorrectMinElement() {
        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(avgGBPRates);
        double result = currencyService.findMinOfAverageGBPRates(numberOfDays);
        assertEquals(5.1457, result, 0.001);
    }

    @Test
    public void shouldReturnCorrectMaxRateFromPeriodOfTime() throws IOException {
        LocalDate dateFrom = LocalDate.of(2023, 8, 1);
        LocalDate dateTo = LocalDate.of(2023, 8, 5);

        Mockito.when(nbpApiService.getLastRateForADate(Currency.EUR.getCode(), dateFrom)).thenReturn(4.0);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.EUR.getCode(), dateFrom.plusDays(1))).thenReturn(4.2);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.EUR.getCode(), dateFrom.plusDays(2))).thenReturn(4.8);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.EUR.getCode(), dateFrom.plusDays(3))).thenReturn(4.6);
        Mockito.when(nbpApiService.getLastRateForADate(Currency.EUR.getCode(), dateTo)).thenReturn(4.4);

        double result = currencyService.findBiggestRateForCurrencyInPeriod(Currency.EUR.getCode(), dateFrom, dateTo);
        assertEquals(4.8, result, 0.01);
    }

    @Test
    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingAverage() {
        Throwable throwable = new EmptyListException();
        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(Collections.emptyList());
        Throwable e = Assert.assertThrows(EmptyListException.class, () -> currencyService.findAverageOfAverageGBPRates(numberOfDays));
        Assertions.assertThat(e)
                .hasSameClassAs(throwable);
    }


    @Test
    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMax() {
        Throwable throwable = new EmptyListException();

        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(Collections.emptyList());
        Throwable e = Assert.assertThrows(EmptyListException.class, () -> currencyService.findMaxOfAverageGBPRates(numberOfDays));
        Assertions.assertThat(e)
                .hasSameClassAs(throwable);
    }

    @Test
    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMaxUsingSoftAssertions() {
        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(Collections.emptyList());
        Throwable e = Assert.assertThrows(EmptyListException.class, () -> currencyService.findMaxOfAverageGBPRates(numberOfDays));
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(e.getClass()).isEqualTo(EmptyListException.class);
    }

    @Test
    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMin() {
        Throwable throwable = new EmptyListException();

        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(Collections.emptyList());
        Throwable e = Assert.assertThrows(EmptyListException.class, () -> currencyService.findMinOfAverageGBPRates(numberOfDays));
        Assertions.assertThat(e)
                .hasSameClassAs(throwable);
    }

    @Test
    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMinUsingSoftAssertions() {
        Mockito.when(nbpApiService.getListOfRates(Currency.GBP.getCode(), numberOfDays)).thenReturn(Collections.emptyList());
        Throwable e = Assert.assertThrows(EmptyListException.class, () -> currencyService.findMinOfAverageGBPRates(numberOfDays));
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(e.getClass()).isEqualTo(EmptyListException.class);
    }
}