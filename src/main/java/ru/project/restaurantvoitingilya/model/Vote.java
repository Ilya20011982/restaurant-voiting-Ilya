package ru.project.restaurantvoitingilya.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(name = "one_vote_per_day", columnNames = {"user_id", "vote_date"})},
        indexes = {@Index(name = "fn_restaurant_id", columnList = "restaurant_id")})
@NoArgsConstructor
@Getter
@Setter
public class Vote extends BaseEntity {

    @Column(name = "vote_date", columnDefinition = "date default now()", nullable = false)
    @NotNull
    private LocalDate date;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Vote(Integer id, LocalDate date, Restaurant restaurant, User user) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                ", restaurant=" + (restaurant != null ? restaurant.getId() : null) +
                ", user=" + (user != null ? user.getId() : null) +
                '}';
    }
}