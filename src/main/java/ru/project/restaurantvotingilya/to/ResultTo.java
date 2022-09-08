package ru.project.restaurantvotingilya.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResultTo extends NamedTo {

    private String address;
    private String telephone;
    private Long votes;

    public ResultTo(int id, String name, String address, String telephone, Long votes) {
        super(id, name);
        this.address = address;
        this.telephone = telephone;
        this.votes = votes;
    }
}