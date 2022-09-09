package ru.project.restaurantvotingilya.web.vote;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.project.restaurantvotingilya.model.Vote;
import ru.project.restaurantvotingilya.repository.VoteRepository;
import ru.project.restaurantvotingilya.util.VoteUtil;
import ru.project.restaurantvotingilya.web.AbstractControllerTest;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.project.restaurantvotingilya.web.restaurant.RestaurantTestData.*;
import static ru.project.restaurantvotingilya.web.user.UserTestData.*;
import static ru.project.restaurantvotingilya.web.vote.VoteTestData.*;


class VoteControllerTest extends AbstractControllerTest {
    private final String REST_VOTES = VoteController.REST_VOTES;

    @Autowired
    VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_VOTES)
                .param("restaurantId", String.valueOf(RESTAURANT2_ID)))
                .andDo(print())
                .andExpect(status().isCreated());
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, TODAY.toLocalDate()).orElseThrow();
        Vote expected = getVote();
        expected.setId(actual.getId());
        VOTE_MATCHER.assertMatch(actual, expected);
        assertEquals(votes.size() + 1, voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteForWrongRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_VOTES)
                .param("restaurantId", String.valueOf(WRONG_RESTAURANT_ID)))
                .andDo(print())
                .andExpect(status().isBadRequest());
        VOTE_MATCHER.assertMatch(voteRepository.findAll(), votes);
    }

    @Test
    void voteByAnonymous() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_VOTES)
                .param("restaurantId", String.valueOf(RESTAURANT3_ID)))
                .andExpect(status().isUnauthorized());
        VOTE_MATCHER.assertMatch(voteRepository.findAll(), votes);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteBeforeEleven() throws Exception {
        Vote expected = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_BEFORE_ELEVEN.toLocalDate()).orElseThrow();
        expected.setRestaurant(restaurant3);
        try (MockedStatic<LocalDateTime> dateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            dateTimeMockedStatic.when(LocalDateTime::now).thenReturn(DATE_TIME_BEFORE_ELEVEN);
            perform(MockMvcRequestBuilders.post(REST_VOTES)
                    .param("restaurantId", String.valueOf(RESTAURANT3_ID)))
                    .andExpect(status().isOk());
        }
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_BEFORE_ELEVEN.toLocalDate()).orElseThrow();
        VOTE_MATCHER.assertMatch(actual, expected);
        assertEquals(votes.size(), voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteAfterEleven() throws Exception {
        Vote expected = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_AFTER_ELEVEN.toLocalDate()).orElseThrow();
        try (MockedStatic<LocalDateTime> dateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            dateTimeMockedStatic.when(LocalDateTime::now).thenReturn(DATE_TIME_AFTER_ELEVEN);
            perform(MockMvcRequestBuilders.post(REST_VOTES)
                    .param("restaurantId", String.valueOf(RESTAURANT3_ID)))
                    .andExpect(status().isPreconditionFailed());
        }
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_AFTER_ELEVEN.toLocalDate()).orElseThrow();
        VOTE_MATCHER.assertMatch(actual, expected);
        assertEquals(votes.size(), voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void revoteOnBoundary() throws Exception {
        Vote expected = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_ELEVEN.toLocalDate()).orElseThrow();
        expected.setRestaurant(restaurant3);
        try (MockedStatic<LocalDateTime> dateTimeMockedStatic = Mockito.mockStatic(LocalDateTime.class)) {
            dateTimeMockedStatic.when(LocalDateTime::now).thenReturn(DATE_TIME_ELEVEN);
            perform(MockMvcRequestBuilders.post(REST_VOTES)
                    .param("restaurantId", String.valueOf(RESTAURANT3_ID)))
                    .andExpect(status().isOk());
        }
        Vote actual = voteRepository.findByUserIdAndDate(USER_ID, DATE_TIME_ELEVEN.toLocalDate()).orElseThrow();
        VOTE_MATCHER.assertMatch(actual, expected);
        assertEquals(votes.size(), voteRepository.count());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVotesForRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_VOTES + "/restaurants/" + RESTAURANT3_ID)
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("1"));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVotesForRestaurantWithOutVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_VOTES + "/restaurants/" + RESTAURANT2_ID)
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("0"));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getRestaurantThatVotedFor() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_VOTES)
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(VoteUtil.convertFromRestaurantAndVote(LocalDate.now(), restaurant3))));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllRestaurantThatVotedFor() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_VOTES))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(VOTE_TO_MATCHER.contentJson(voteToList));
    }
}
