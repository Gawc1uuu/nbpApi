package nbp.task.service;

import nbp.task.exceptions.EmptyListException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class CurrencyServiceTest {

    @Mock
    NbpApiService nbpApiService;
    private CurrencyService currencyService;
    //    private CurrencyService spy;
    private List<Double> avgGBPRates;
    private int numberOfDays;


    @Before
    public void init() {
        nbpApiService = Mockito.mock(NbpApiService.class);
        currencyService = new CurrencyService(nbpApiService);
//        spy = Mockito.spy(currencyService);
        avgGBPRates = new ArrayList<>(Arrays.asList(5.1457, 5.2091, 5.224, 5.2515, 5.2317, 5.2306, 5.2539, 5.2291, 5.2128, 5.2065));
        numberOfDays = 10;
    }

    @Test
    public void shouldReturnEURWhenTryingToFindWhatIsBetterToBuyInPeriod() throws IOException {
        LocalDate dateFrom = LocalDate.of(2023, 8, 1);
        LocalDate dateTo = LocalDate.of(2023, 8, 15);

        Mockito.when(nbpApiService.getRateForADate("usd", dateFrom, "a")).thenReturn(3.5);
        Mockito.when(nbpApiService.getRateForADate("usd", dateTo, "a")).thenReturn(3.7);
        Mockito.when(nbpApiService.getRateForADate("eur", dateFrom, "a")).thenReturn(4.2);
        Mockito.when(nbpApiService.getRateForADate("eur", dateTo, "a")).thenReturn(4.5);
//        doReturn(3.5).when(spy).fetchExchangeRateForCurrencyForDate("usd", dateFrom);
//        doReturn(3.7).when(spy).fetchExchangeRateForCurrencyForDate("usd", dateTo);
//        doReturn(4.2).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateFrom);
//        doReturn(4.5).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateTo);
        String result = currencyService.findWhatIsBetterToBuyInPeriod("usd", "eur", dateFrom, dateTo);
        assertEquals("eur", result);
    }

//    @Test
//    public void shouldReturnCorrectAverage() {
//        doReturn(avgGBPRates).when(spy).fetchGBPRates(numberOfDays);
//        double result = spy.findAverageOfAverageGBPRates(numberOfDays);
//        assertEquals(5.219, result, 0.001);
//    }
//
//    @Test
//    public void shouldReturnCorrectMaxElement() {
//        doReturn(avgGBPRates).when(spy).fetchGBPRates(numberOfDays);
//        double result = spy.findMaxOfAverageGBPRates(numberOfDays);
//        assertEquals(5.2539, result, 0.001);
//    }
//
//    @Test
//    public void shouldReturnCorrectMinElement() {
//        doReturn(avgGBPRates).when(spy).fetchGBPRates(numberOfDays);
//        double result = spy.findMinOfAverageGBPRates(numberOfDays);
//        assertEquals(5.1457, result, 0.001);
//    }
//
//    @Test
//    public void shouldReturnCorrectMaxRateFromPeriodOfTime() throws IOException {
//        LocalDate dateFrom = LocalDate.of(2023, 8, 1);
//        LocalDate dateTo = LocalDate.of(2023, 8, 5);
//
//        doReturn(4.0).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateFrom);
//        doReturn(4.2).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateFrom.plusDays(1));
//        doReturn(4.8).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateFrom.plusDays(2));
//        doReturn(4.6).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateFrom.plusDays(3));
//        doReturn(4.4).when(spy).fetchExchangeRateForCurrencyForDate("eur", dateTo);
//
//        double result = spy.findBiggestRateForCurrencyInPeriod("eur", dateFrom, dateTo);
//        assertEquals(4.8, result, 0.01);
//    }
//
//    @Test
//    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingAverage() {
//        Throwable throwable = new EmptyListException();
//
//        doReturn(new ArrayList<>()).when(spy).fetchGBPRates(numberOfDays);
//        Throwable e = Assert.assertThrows(EmptyListException.class, () -> spy.findAverageOfAverageGBPRates(numberOfDays));
//        Assertions.assertThat(e)
//                .hasSameClassAs(throwable);
//    }
//
//    @Test
//    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingAverageUsingSoftAssertions() {
//        doReturn(new ArrayList<>()).when(spy).fetchGBPRates(numberOfDays);
//        Throwable e = Assert.assertThrows(EmptyListException.class, () -> spy.findAverageOfAverageGBPRates(numberOfDays));
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(e.getClass()).isEqualTo(EmptyListException.class);
//    }
//
//    @Test
//    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMax() {
//        Throwable throwable = new EmptyListException();
//
//        doReturn(new ArrayList<>()).when(spy).fetchGBPRates(numberOfDays);
//        Throwable e = Assert.assertThrows(EmptyListException.class, () -> spy.findMaxOfAverageGBPRates(numberOfDays));
//        Assertions.assertThat(e)
//                .hasSameClassAs(throwable);
//    }
//
//    @Test
//    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMaxUsingSoftAssertions() {
//        doReturn(new ArrayList<>()).when(spy).fetchGBPRates(numberOfDays);
//        Throwable e = Assert.assertThrows(EmptyListException.class, () -> spy.findMaxOfAverageGBPRates(numberOfDays));
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(e.getClass()).isEqualTo(EmptyListException.class);
//    }
//
//    @Test
//    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMin() {
//        Throwable throwable = new EmptyListException();
//
//        doReturn(new ArrayList<>()).when(spy).fetchGBPRates(numberOfDays);
//        Throwable e = Assert.assertThrows(EmptyListException.class, () -> spy.findMinOfAverageGBPRates(numberOfDays));
//        Assertions.assertThat(e)
//                .hasSameClassAs(throwable);
//    }
//
//    @Test
//    public void shouldThrowEmptyListExceptionWhenListPassedAsArgumentIsEmptyWhenFindingMinUsingSoftAssertions() {
//        doReturn(new ArrayList<>()).when(spy).fetchGBPRates(numberOfDays);
//        Throwable e = Assert.assertThrows(EmptyListException.class, () -> spy.findMinOfAverageGBPRates(numberOfDays));
//        SoftAssertions softAssertions = new SoftAssertions();
//        softAssertions.assertThat(e.getClass()).isEqualTo(EmptyListException.class);
//    }
}