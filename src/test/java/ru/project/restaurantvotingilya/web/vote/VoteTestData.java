package ru.project.restaurantvotingilya.web.vote;



import ru.project.restaurantvotingilya.model.Vote;
import ru.project.restaurantvotingilya.to.VoteTo;
import ru.project.restaurantvotingilya.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.project.restaurantvotingilya.util.VoteUtil.convertFromVote;
import static ru.project.restaurantvotingilya.web.restaurant.RestaurantTestData.*;
import static ru.project.restaurantvotingilya.web.user.UserTestData.admin;
import static ru.project.restaurantvotingilya.web.user.UserTestData.user;


public class VoteTestData {
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user.password", "user.registered", "restaurant.menu", "restaurant.vote");

    public static final LocalDateTime TODAY = LocalDateTime.now();
    public static final LocalDateTime DATE_TIME_BEFORE_ELEVEN = LocalDateTime.of(2022, 9, 5, 10, 55);
    public static final LocalDateTime DATE_TIME_AFTER_ELEVEN = LocalDateTime.of(2022, 9, 5, 11, 05);
    public static final LocalDateTime DATE_TIME_ELEVEN = LocalDateTime.of(2022, 9, 5, 11, 0);

    public static final int VOTE1_ID = 1;
    public static final int VOTE2_ID = 2;
    public static final int VOTE3_ID = 3;
    public static final int VOTE4_ID = 4;
    public static final int VOTE5_ID = 5;
    public static final int VOTE6_ID = 6;
    public static final int VOTE7_ID = 7;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2022, 9, 5), restaurant1, user);
    public static final Vote vote2 = new Vote(VOTE2_ID, LocalDate.of(2022, 9, 5), restaurant3, admin);
    public static final Vote vote3 = new Vote(VOTE3_ID, LocalDate.of(2022, 9, 4), restaurant2, user);
    public static final Vote vote4 = new Vote(VOTE4_ID, LocalDate.of(2022, 9, 4), restaurant2, admin);
    public static final Vote vote5 = new Vote(VOTE5_ID, LocalDate.of(2022, 9, 3), restaurant1, user);
    public static final Vote vote6 = new Vote(VOTE6_ID, LocalDate.of(2022, 9, 3), restaurant1, admin);

    public static final Vote voteToday = new Vote(VOTE7_ID, LocalDate.now(), restaurant3, admin);

    public static final List<VoteTo> voteToList = List.of(convertFromVote(voteToday), convertFromVote(vote2), convertFromVote(vote4), convertFromVote(vote6));

    public static final List<Vote> votes = List.of(vote1, vote2, vote3, vote4, vote5, vote6, voteToday);

    public static Vote getVote() {
        return new Vote(null, LocalDate.now(), restaurant2, user);
    }
}
