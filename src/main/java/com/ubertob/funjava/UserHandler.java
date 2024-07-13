package com.ubertob.funjava;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class UserHandler {

    private final UserRepository userRepository;


    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        List<AppUser> users = userRepository.findAll();
        return ServerResponse.ok().bodyValue("All users here!!!");

//                .render("users", Map.of("users", users));
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {

        return ((Function<ServerRequest, Long>) request1 -> extractUserId(request1))
                .andThen(userRepository::findById)
                .andThen(user -> user.map(UserHandler::okResponse))
                .andThen(okResponse -> okResponse.orElseGet(UserHandler::notFoundResponse))
                .apply(request);
    }

    private static Mono<ServerResponse> notFoundResponse() {
        return ServerResponse.status(HttpStatus.NOT_FOUND).render("user-not-found");
    }

    private static Mono<ServerResponse> okResponse(AppUser appUser) {
        return ServerResponse.ok().render("user-details", Map.of("user", appUser));
    }

    private static Long extractUserId(ServerRequest request) {
        return Long.valueOf(request.pathVariable("id"));
    }
}