package ru.project.restaurantvotingilya.util;

import lombok.experimental.UtilityClass;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.to.ResultTo;

@UtilityClass
public class ResultUtil {
    public static ResultTo convertFromRestaurantAndVote(Restaurant restaurant, Long votes) {
        return new ResultTo(restaurant.id(), restaurant.getName(), restaurant.getAddress(),
                restaurant.getTelephone(), votes);
    }
}
