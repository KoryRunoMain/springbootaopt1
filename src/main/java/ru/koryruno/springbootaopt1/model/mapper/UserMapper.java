package ru.koryruno.springbootaopt1.model.mapper;

import org.springframework.stereotype.Component;
import ru.koryruno.springbootaopt1.model.User;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.responseDto.UserFullDto;

@Component
public class UserMapper {

    public User toUser(NewUserDto newUserDto) {
        return User.builder()
                .name(newUserDto.getName())
                .email(newUserDto.getEmail())
                .build();
    }

    public UserFullDto toUserFullDto(User user) {
        return UserFullDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
