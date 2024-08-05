package ru.koryruno.springbootaopt1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.koryruno.springbootaopt1.annotation.Asynchronously;
import ru.koryruno.springbootaopt1.annotation.PreInvoke;
import ru.koryruno.springbootaopt1.annotation.SuccessLogging;
import ru.koryruno.springbootaopt1.exception.NotFoundException;
import ru.koryruno.springbootaopt1.model.RoleType;
import ru.koryruno.springbootaopt1.model.User;
import ru.koryruno.springbootaopt1.model.mapper.UserMapper;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;
import ru.koryruno.springbootaopt1.model.responseDto.UserFullDto;
import ru.koryruno.springbootaopt1.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@SuccessLogging
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Asynchronously
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public UserFullDto createUser(NewUserDto newUserDto) {
        User user = userRepository.save(userMapper.toUser(newUserDto));
        return userMapper.toUserFullDto(user);
    }

    @Override
    @Asynchronously
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public UserFullDto updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id:{} not found", userId)));
        user.setName(updateUserDto.getName());
        User updatedUser = userRepository.save(user);
        return userMapper.toUserFullDto(updatedUser);
    }

    @Override
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public UserFullDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id:{} not found", userId)));
        return userMapper.toUserFullDto(user);
    }

    @Override
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public List<UserFullDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserFullDto).toList();
    }

    @Override
    @PreInvoke(roles = {RoleType.ADMIN})
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}
