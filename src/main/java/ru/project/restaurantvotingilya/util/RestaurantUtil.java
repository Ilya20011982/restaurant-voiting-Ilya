package ru.project.restaurantvotingilya.util;

import lombok.experimental.UtilityClass;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.to.RestaurantTo;


@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress(), restaurantTo.getTelephone());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setAddress(restaurantTo.getAddress());
        restaurant.setTelephone(restaurantTo.getTelephone());
        return restaurant;
    }

    public static RestaurantTo convertFromRestaurant(Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getTelephone());
    }
}