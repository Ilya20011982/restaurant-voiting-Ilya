package ru.project.restaurantvotingilya.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.project.restaurantvotingilya.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Query("select r from Restaurant r left join r.vote v where v.date =?1 and v.user.id =?2")
    Optional<Restaurant> getByDateAndUserId(LocalDate date, Integer id);

    @Query("select r from Restaurant r join fetch r.menu m where r.id =?1 and m.date =?2 order by r.name, r.address")
    Optional<Restaurant> findByIdWithMenu(int id, LocalDate date);
}
