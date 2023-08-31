package books.task;

import books.task.model.Book;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import pl.kurs.model.Car;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static final String PATH = "src/main/resources/";

    @SneakyThrows
    public static void main(String[] args) {
            /*
                    Stworz klase ksiazka ktora ma autora, tytul, kategorie, cene.
                    STworz kilka obiektów i zapisz je jako json nody a nastepnie zapisz je jako plik jsonowy.
                    Wczytaj nastepnie ktoras ksiazke jako obiekt i wyswietl jej wartosci
                 */
        Book b1 = Book.builder()
                .author("Adam Mickiewicz")
                .title("Dziady")
                .category("Lektura")
                .price(39.99)
                .build();

        Book b2 = Book.builder()
                .author("George Orwell")
                .title("Folwark Zwierzecy")
                .category("Lektura")
                .price(19.99)
                .build();

        Book b3 = Book.builder()
                .author("Agatha Christie")
                .title("Morderstwo w Orient Ekspresie")
                .category("Kryminalna")
                .price(39.99)
                .build();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode j1 = mapper.valueToTree(b1);
        JsonNode j2 = mapper.valueToTree(b2);
        JsonNode j3 = mapper.valueToTree(b3);

        mapper.writeValue(new File(PATH + "books.json"), new ArrayList<JsonNode>(Arrays.asList(j1, j2, j3)));
        List<Book> books = mapper.readValue(new File(PATH + "books.json"), List.class);
        System.out.println(books.get(1));


    }
}
