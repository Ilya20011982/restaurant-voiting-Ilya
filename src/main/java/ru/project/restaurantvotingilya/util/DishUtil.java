package ru.project.restaurantvotingilya.util;

import lombok.experimental.UtilityClass;
import ru.project.restaurantvotingilya.model.Dish;
import ru.project.restaurantvotingilya.to.DishTo;


@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getDate(), dishTo.getPrice());
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
        dish.setDate(dishTo.getDate());
        return dish;
    }

    public static DishTo convertFromDish(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getDate(), dish.getPrice());
    }
}
