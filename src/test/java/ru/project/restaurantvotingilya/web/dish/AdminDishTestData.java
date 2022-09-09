package ru.project.restaurantvotingilya.web.dish;

import ru.project.restaurantvotingilya.model.Dish;
import ru.project.restaurantvotingilya.to.DishTo;
import ru.project.restaurantvotingilya.util.DishUtil;
import ru.project.restaurantvotingilya.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static ru.project.restaurantvotingilya.web.restaurant.RestaurantTestData.restaurant1;

public class AdminDishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);
    public static final MatcherFactory.Matcher<DishTo> DISH_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(DishTo.class);

    public static final int DISH1_ID = 11;
    public static final int DISH2_ID = 12;
    public static final int DISH3_ID = 13;
    public static final int DISH4_ID = 14;
    public static final int DISH5_ID = 15;
    public static final int DISH6_ID = 16;
    public static final int DISH7_ID = 17;

    public static final int DISH11_ID = 21;
    public static final LocalDate TODAY = LocalDate.now();


    public static final Dish dish1 = new Dish(DISH1_ID, "Суп", TODAY, 50);
    public static final Dish dish2 = new Dish(DISH2_ID, "Чай", TODAY, 25);
    public static final Dish dish3 = new Dish(DISH3_ID, "Пшенка", TODAY, 60);
    public static final Dish dish4 = new Dish(DISH4_ID, "Рыба", TODAY, 150);

    public static final Dish dish5 = new Dish(DISH5_ID, "Солянка", TODAY, 100);
    public static final Dish dish6 = new Dish(DISH6_ID, "Котлета", TODAY, 160);
    public static final Dish dish7 = new Dish(DISH7_ID, "Кофе", TODAY, 60);

    public static final Set<Dish> menu1 = Set.of(dish1, dish2, dish3, dish4);
    public static final Set<Dish> menu3 = Set.of(dish5, dish6, dish7);

    public static final List<DishTo> todayDishes = Stream.of(dish6, dish7, dish3, dish4, dish5, dish1, dish2).map(DishUtil::convertFromDish).toList();

    public static DishTo getNew() {
        return new DishTo(null, "New", TODAY, 99);
    }

    public static DishTo getWithNotUniqueDateAndName() {
        return new DishTo(null, dish1.getName(), TODAY, 99);
    }

    public static DishTo getNewWithWrongData() {
        return new DishTo(null, "N", TODAY, 99);
    }

    public static DishTo getNewAfterSaveInRepo() {
        return new DishTo(DISH11_ID, "New", TODAY, 99);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "New", TODAY, 99, restaurant1);
    }
}
