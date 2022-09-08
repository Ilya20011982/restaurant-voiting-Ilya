package ru.project.restaurantvotingilya.to;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@Getter
@Setter
public class DishTo extends NamedTo {

    @NotNull
    private LocalDate date;

    @NotNull
    @PositiveOrZero
    private int price;

    public DishTo(Integer id, String name, LocalDate date, int price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }
}
