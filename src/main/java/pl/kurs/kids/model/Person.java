package pl.kurs.kids.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter

@JsonTypeInfo( include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CLASS)
public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Boolean isMarried;
    private List<String> hobbies;
    private List<Kid> kids;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthday;


}
