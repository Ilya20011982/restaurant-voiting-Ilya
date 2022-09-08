package ru.project.restaurantvotingilya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.model.User;
import ru.project.restaurantvotingilya.model.Vote;
import ru.project.restaurantvotingilya.repository.RestaurantRepository;
import ru.project.restaurantvotingilya.repository.VoteRepository;
import ru.project.restaurantvotingilya.to.RestaurantTo;
import ru.project.restaurantvotingilya.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VoteService {
    public static final LocalTime TIME_BEFORE_CAN_REVOTE = LocalTime.of(11, 0);

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    public ResponseEntity<String> vote(int restaurantId, User user) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            return new ResponseEntity<>("No restaurant available!", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime dateTime = LocalDateTime.now();
        Optional<Vote> optionalVote = voteRepository.findByUserIdAndDate(user.id(), dateTime.toLocalDate());
        if (optionalVote.isEmpty()) {
            Vote vote = new Vote(null, dateTime.toLocalDate(), restaurant.get(), user);
            voteRepository.save(vote);
            return new ResponseEntity<>("Your vote was counted.", HttpStatus.CREATED);
        }
        if (dateTime.toLocalTime().isAfter(TIME_BEFORE_CAN_REVOTE)) {
            return new ResponseEntity<>("Too late, vote can't be changed!", HttpStatus.PRECONDITION_FAILED);
        } else {
            optionalVote.get().setRestaurant(restaurant.get());
            return new ResponseEntity<>("Your vote was changed.", HttpStatus.OK);
        }
    }

    public List<VoteTo> findAllByUserId(Integer id) {
        return voteRepository.findAllByUserId(id).stream()
                .map(o -> new VoteTo((LocalDate) o[0],
                        new RestaurantTo((Integer) o[1], (String) o[2], (String) o[3], (String) o[4])))
                .toList();
    }
}
