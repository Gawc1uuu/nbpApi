package pl.kurs.kids;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import pl.kurs.DateKeeper;
import pl.kurs.kids.model.Kid;
import pl.kurs.kids.model.Person;
import pl.kurs.model.Car;
import pl.kurs.utils.ObjectMapperHolder;

import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public class Main {

    private static final String PATH = "src/main/resources/";

    @SneakyThrows
    public static void main(String[] args) {
        //    ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = ObjectMapperHolder.INSTANCE.getMapper();

        // tworzenie obiektu java na podstawie pliku jsonowego
        // deserializacja
//        Person adamNowak = mapper.readValue(new File(PATH + "adamnowak.json"), Person.class);
//        System.out.println(adamNowak);

//        // serializacja
        Person janKowalski = new Person("Jan", "Kowalski", 50, false, List.of("pilka nozna", "znaczki"),
                List.of(new Kid("Krzys", "Kowalski", 5), new Kid("Lukasz", "Kowalski", 6)),
                LocalDate.now());
//        Person mariaKowalski = new Person("Maria", "Kowalska", 50, false, List.of("pilka nozna", "znaczki"),
//                List.of(new Kid("Krzys", "Kowalski", 5), new Kid("Lukasz", "Kowalski", 6)));
//
        mapper.writeValue(new File(PATH + "jankowalski.json"), janKowalski);
//        mapper.writeValue(new File(PATH + "jankowalski.json"), mariaKowalski);
//
//        // zapisanie obiektu java do stringa z jsonem
//        Kid kid = new Kid("Franek", "Kiwatkowski", 7);
//        String kidJdonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(kid);
//        System.out.println(kidJdonString);
//
//        // stwrozenie obiektu na podstawie sitnrga z jsonem
//        String kidJson = "{\"firstName\":\"Franek\",\"lastName\":\"Kiwatkowski\",\"age\":7}";
//        Kid kidFromString = mapper.readValue(kidJson, Kid.class);
//        System.out.println(kidFromString);
//
//        // zamiana stringa z jsonem w json noda
//        JsonNode jsonNode = mapper.readTree(kidJson);
//        System.out.println(jsonNode.get("firstName").asText());
//        System.out.println(jsonNode.get("age").asInt());
//
//        System.out.println(jsonNode.toPrettyString());
//
//        // zamiana obiektu java 'a
//        JsonNode jsonNode1 = mapper.valueToTree(kid);
//        System.out.println(jsonNode1);
//
//        // zamiana jsonnoda na obiekt javowy
//        Kid kidFromJsonNode = mapper.treeToValue(jsonNode1, Kid.class);
//        System.out.println(kidFromJsonNode);
//
//        // zamiana json noda na stringa z jsonem
//        String stringFromJsonNode = jsonNode1.toPrettyString();
//
//        mapper.writeValue(new File(PATH + "node.json"), jsonNode1);

        DateKeeper dateKeeper = new DateKeeper(new Date());
        mapper.writeValue(new File(PATH + "date.json"), dateKeeper);

        Car car = new Car("polonez", 5);
        mapper.writeValue(new File(PATH + "polonez.json"), car);
    }
}
