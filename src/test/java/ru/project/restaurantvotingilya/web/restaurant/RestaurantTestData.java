package ru.project.restaurantvotingilya.web.restaurant;

import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.to.RestaurantTo;
import ru.project.restaurantvotingilya.web.MatcherFactory;

import static ru.project.restaurantvotingilya.web.dish.AdminDishTestData.menu1;
import static ru.project.restaurantvotingilya.web.dish.AdminDishTestData.menu3;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER_WITH_MENU =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "vote", "menu.restaurant");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int WRONG_RESTAURANT_ID = 555;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Tutto Bento", "Ufa", "279-09-03", menu1);
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Ашхана", "Сызрань", "54-95-56", null);
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Мясо", "проспект Салавата Юлаева, 1", "+7(347) 777-11-33", menu3);

    public static final RestaurantTo restaurantTo1 = new RestaurantTo(RESTAURANT1_ID, "Tutto Bento", "Ufa", "279-09-03");
    public static final RestaurantTo restaurantTo2 = new RestaurantTo(RESTAURANT2_ID, "Ашхана", "Сызрань", "54-95-56");
    public static final RestaurantTo restaurantTo3 = new RestaurantTo(RESTAURANT3_ID, "Мясо", "проспект Салавата Юлаева, 1", "+7(347) 777-11-33");

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant", "Somewhere", "111-22-33");
    }

    public static Restaurant getNewWithWrongData() {
        return new Restaurant(null, "NewRestaurant", "", "111-22-33");
    }

    public static Restaurant getNotUniqueName() {
        return new Restaurant(null, "Tutto Bento", "Ufa44", "(347) 547-98-65");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName", "newAddress", "new phone");
    }
}
