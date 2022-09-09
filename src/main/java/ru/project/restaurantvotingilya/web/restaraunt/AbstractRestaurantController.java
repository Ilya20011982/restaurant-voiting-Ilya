package ru.project.restaurantvotingilya.web.restaraunt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import ru.project.restaurantvotingilya.error.IllegalRequestDataException;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.repository.RestaurantRepository;
import ru.project.restaurantvotingilya.to.RestaurantTo;
import ru.project.restaurantvotingilya.util.RestaurantUtil;


import java.util.List;

public abstract class AbstractRestaurantController {
    public static final String RESTAURANT_NOT_FOUND = "Restaurant not found";

    @Autowired
    private RestaurantRepository restaurantRepository;

    public RestaurantTo get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalRequestDataException(RESTAURANT_NOT_FOUND));
        return RestaurantUtil.convertFromRestaurant(restaurant);
    }

    public List<RestaurantTo> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "address"));
        return restaurants.stream().map(RestaurantUtil::convertFromRestaurant).toList();
    }
}
