package pl.kurs.cars.service;

import pl.kurs.cars.model.Car;


import java.util.*;

public class CarService {
    public Car findMostExpensiveCar(List<Car> carList) {
        return Optional.ofNullable(carList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Car::calculatePrice))
                .orElse(null);
    }
}
