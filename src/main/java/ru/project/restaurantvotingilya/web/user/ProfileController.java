package ru.project.restaurantvotingilya.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.project.restaurantvotingilya.model.User;
import ru.project.restaurantvotingilya.to.UserTo;
import ru.project.restaurantvotingilya.util.UserUtil;
import ru.project.restaurantvotingilya.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;

import static ru.project.restaurantvotingilya.util.validation.ValidationUtil.assureIdConsistent;
import static ru.project.restaurantvotingilya.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@CacheConfig(cacheNames = "users")
public class ProfileController extends AbstractUserController {
    static final String REST_URL = "/api/profile";

    @GetMapping
    @Cacheable
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(cacheNames = "users", allEntries = true)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = prepareAndSave(UserUtil.createNewFromTo(userTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(cacheNames = "users", allEntries = true)
    @Transactional
    public void update(@RequestBody @Valid UserTo userTo, @AuthenticationPrincipal AuthUser authUser) {
        assureIdConsistent(userTo, authUser.id());
        User user = authUser.getUser();
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }
}