package ru.project.restaurantvotingilya.web.restaurant;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.project.restaurantvotingilya.model.Restaurant;
import ru.project.restaurantvotingilya.repository.DishRepository;
import ru.project.restaurantvotingilya.repository.RestaurantRepository;
import ru.project.restaurantvotingilya.to.RestaurantTo;
import ru.project.restaurantvotingilya.util.JsonUtil;
import ru.project.restaurantvotingilya.web.AbstractControllerTest;
import ru.project.restaurantvotingilya.web.GlobalExceptionHandler;
import ru.project.restaurantvotingilya.web.restaraunt.AdminRestaurantController;
import ru.project.restaurantvotingilya.web.restaraunt.UserRestaurantController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.project.restaurantvotingilya.util.RestaurantUtil.convertFromRestaurant;
import static ru.project.restaurantvotingilya.web.dish.AdminDishTestData.DISH1_ID;
import static ru.project.restaurantvotingilya.web.restaurant.RestaurantTestData.*;
import static ru.project.restaurantvotingilya.web.user.UserTestData.ADMIN_MAIL;
import static ru.project.restaurantvotingilya.web.user.UserTestData.USER_MAIL;


class RestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantController.REST_URL + '/';
    private static final String REST_URL_FOR_USER = UserRestaurantController.REST_URL_FOR_USER + '/';
    private static final String REST_URL_MENU = "/menu";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTo1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_USER + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTo1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenuForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_USER + RESTAURANT1_ID + REST_URL_MENU))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER_WITH_MENU.contentJson(restaurant1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(restaurantRepository.findById(RESTAURANT1_ID).isPresent());
        assertFalse(dishRepository.findById(DISH1_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteByUser() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTo1, restaurantTo2, restaurantTo3));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllForUser() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_FOR_USER))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(restaurantTo1, restaurantTo2, restaurantTo3));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.getById(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithWrongData() throws Exception {
        long count = restaurantRepository.count();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNewWithWrongData())))
                .andExpect(status().isUnprocessableEntity());
        assertEquals(count, restaurantRepository.count());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithNotUniqueName() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNotUniqueName())))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(Matchers.containsString(GlobalExceptionHandler.EXCEPTION_DUPLICATE_RESTAURANT_NAME)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createByUser() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .content(JsonUtil.writeValue(getNew())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        RestaurantTo updatedRestaurantTo = convertFromRestaurant(getUpdated());
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurantTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        updatedRestaurantTo.setId(RESTAURANT1_ID);
        RESTAURANT_TO_MATCHER.assertMatch(convertFromRestaurant(restaurantRepository.getById(RESTAURANT1_ID)), updatedRestaurantTo);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateWithWrongData() throws Exception {
        RestaurantTo updatedRestaurantTo = convertFromRestaurant(getNewWithWrongData());
        updatedRestaurantTo.setId(RESTAURANT1_ID);
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedRestaurantTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateByUser() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(getNew())))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
