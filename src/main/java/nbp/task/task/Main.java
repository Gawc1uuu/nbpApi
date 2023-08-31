package nbp.task.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import nbp.task.task.model.Car;
import nbp.task.task.service.CarService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static final String PATH = "src/main/resources/";

    @SneakyThrows
    public static void main(String[] args) {
        //Stwórz jsona z samochodem, samochod ma marke, model, pojemnosc silnika, info czy ma turbo oraz liste wyposazen
        //wyposazenie ma nazwe i cene

        //Stwórz 3 jsony z informacjami o autach, nastepnie wcyztaj je i wrzuc na liste i znajdz najdrozsze auto (na podstawie wyspoazenia)

        //Stwórz obiekt samochod i zapisz go do formatu json
        ObjectMapper mapper = new ObjectMapper();
        List<Car> carList = new ArrayList<>();

        Car car1 = mapper.readValue(new File(PATH + "car1.json"), Car.class);
        Car car2 = mapper.readValue(new File(PATH + "car2.json"), Car.class);
        Car car3 = mapper.readValue(new File(PATH + "car3.json"), Car.class);
        carList.add(car1);
        carList.add(car2);
        carList.add(car3);
        CarService carService = new CarService();
        System.out.println(carService.findMostExpensiveCar(carList));
    }
}
