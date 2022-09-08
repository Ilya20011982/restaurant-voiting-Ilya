package ru.project.restaurantvotingilya.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.project.restaurantvotingilya.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.project.restaurantvotingilya.util.validation.ValidationUtil.checkModification;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("select d from Dish d where d.restaurant.id = ?1 and d.date = ?2 order by d.name")
    List<Dish> findAllByRestaurantIdAndDate(int restaurantId, LocalDate date);

    @Query("select d from Dish d where d.id = ?1 and d.restaurant.id = ?2")
    Optional<Dish> findByIdAndRestaurantId(int id, int restaurantId);

    @Transactional
    @Modifying
    @Query("delete from Dish d where d.id =?1 and d.restaurant.id = ?2")
    int deleteByIdAndRestaurantId(int id, int restaurantId);

    @Query("select d from Dish d where d.date =?1 order by d.name")
    List<Dish> findAllByDateAndOrderByName(LocalDate date);

    default void deleteExisted(int id, int restaurantId) {
        checkModification(deleteByIdAndRestaurantId(id, restaurantId), id, restaurantId);
    }
}