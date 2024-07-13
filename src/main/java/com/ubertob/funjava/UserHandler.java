package com.ubertob.funjava;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;


@Component
public class UserHandler {

    private final UserRepository userRepository;

    private final ITemplateEngine templateEngine = View.templateEngine();

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        List<AppUser> users = userRepository.findAll();

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(allUsersPage(users));
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {

        return ((Function<ServerRequest, Long>) request1 -> extractUserId(request1))
                .andThen(userRepository::findById)
                .andThen(user -> user
                        .map(this::userDetailsPage)
                        .map(UserHandler::okResponse)
                        .orElseGet(UserHandler::notFoundResponse)
                )
                .apply(request);
    }


    private static Mono<ServerResponse> notFoundResponse() {
        return ServerResponse.status(HttpStatus.NOT_FOUND).render("user-not-found");
    }

    private static Mono<ServerResponse> okResponse(String html) {
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_HTML)
                .bodyValue(html);
    }

    private static Long extractUserId(ServerRequest request) {
        return Long.valueOf(request.pathVariable("id"));
    }

    private String allUsersPage(List<AppUser> users) {
        var context = new Context();
        context.setVariable("users", users);
        return templateEngine.process("users", context);
    }

    private String userDetailsPage(AppUser user) {
        var context = new Context();
        context.setVariable("user", user);
        return templateEngine.process("user-details", context);
    }
}