package books.task.cars.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(includeFieldNames = false)

@Builder
public class Equipment {
    private String name;
    private double price;
}
