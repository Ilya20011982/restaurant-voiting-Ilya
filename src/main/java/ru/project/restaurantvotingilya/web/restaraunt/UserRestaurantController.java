package ru.project.restaurantvotingilya.web.restaraunt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.project.restaurantvotingilya.error.IllegalRequestDataException;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.repository.RestaurantRepository;
import ru.project.restaurantvotingilya.to.RestaurantTo;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL_FOR_USER, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "restaurants")
public class UserRestaurantController extends AbstractRestaurantController {

    public static final String REST_URL_FOR_USER = "/api/restaurants";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/{id}")
    @Cacheable
    public RestaurantTo get(@PathVariable int id) {
        log.info("get restaurant {}", id);
        return super.get(id);
    }

    @GetMapping("/{id}/menu")
    @Cacheable
    public Restaurant getWithMenu(@PathVariable int id, @RequestParam @Nullable LocalDate date) {
        log.info("get restaurant {} with menu", id);
        date = Objects.requireNonNullElseGet(date, LocalDate::now);
        return restaurantRepository.findByIdWithMenu(id, date)
                .orElseThrow(() -> new IllegalRequestDataException(RESTAURANT_NOT_FOUND));
    }

    @GetMapping
    @Cacheable
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }
}
