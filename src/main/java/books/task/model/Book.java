package books.task.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
@ToString(includeFieldNames = false)
@EqualsAndHashCode
@Builder
public class Book {
    private String author;
    private String title;
    private String category;
    private double price;

}
