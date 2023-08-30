package task.model;

import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@ToString(includeFieldNames = false)

@Builder
public class Car {
    private String brand;
    private String model;
    private double engineCapacity;
    private boolean turbocharged;
    private List<Equipment> equipmentList;

    public double calculatePrice() {
        return Optional.ofNullable(equipmentList)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(Objects::nonNull)
                .mapToDouble(Equipment::getPrice)
                .sum();
    }
}
