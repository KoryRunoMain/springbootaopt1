package ru.koryruno.springbootaopt1.service;

import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;
import ru.koryruno.springbootaopt1.model.responseDto.UserFullDto;

import java.util.List;

public interface UserService {

    UserFullDto createUser(NewUserDto newUserDto);

    UserFullDto updateUser(Long userId, UpdateUserDto updateUserDto);

    UserFullDto getUser(Long userId);

    List<UserFullDto> getAllUsers();

    void deleteUser(Long userId);

}
