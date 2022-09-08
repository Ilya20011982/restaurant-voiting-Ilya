package ru.project.restaurantvotingilya.util;

import lombok.experimental.UtilityClass;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.model.Vote;
import ru.project.restaurantvotingilya.to.VoteTo;
import ru.project.restaurantvotingilya.util.RestaurantUtil;

import java.time.LocalDate;

@UtilityClass
public class VoteUtil {
    public static VoteTo convertFromRestaurantAndVote(LocalDate date, Restaurant restaurant) {
        return new VoteTo(date, RestaurantUtil.convertFromRestaurant(restaurant));
    }

    public static VoteTo convertFromVote(Vote vote) {
        return new VoteTo(vote.getDate(), RestaurantUtil.convertFromRestaurant(vote.getRestaurant()));
    }
}
