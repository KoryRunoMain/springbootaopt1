package ru.koryruno.springbootaopt1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.koryruno.springbootaopt1.annotation.Valid;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;
import ru.koryruno.springbootaopt1.model.responseDto.UserFullDto;
import ru.koryruno.springbootaopt1.service.UserService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping(path = "/admin/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserFullDto create(@Valid @RequestBody NewUserDto newUserDto) {
        return service.createUser(newUserDto);
    }

    @PatchMapping(path = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDto update(@PathVariable Long userId,
                              @Valid @RequestBody UpdateUserDto updateUserDto) {
        return service.updateUser(userId, updateUserDto);
    }

    @GetMapping(path = "/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserFullDto get(@PathVariable Long userId) {
        return service.getUser(userId);
    }

    @GetMapping(path = "/users")
    @ResponseStatus(HttpStatus.OK)
    public List<UserFullDto> getAll() {
        return service.getAllUsers();
    }

    @DeleteMapping(path = "/admin/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long userId) {
        service.deleteUser(userId);
    }

}
