package ru.project.restaurantvotingilya.to;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.project.restaurantvotingilya.util.validation.NoHtml;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class RestaurantTo extends NamedTo {

    @NotBlank
    @NoHtml
    @Size(max = 255)
    private String address;

    @NoHtml
    @Size(max = 20)
    private String telephone;

    public RestaurantTo(Integer id, String name, String address, String telephone) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
    }
}
