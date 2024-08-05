package ru.koryruno.springbootaopt1.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.aspectj.AspectJAfterThrowingAdvice;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import ru.koryruno.springbootaopt1.aspect.ValidateAspect;
import ru.koryruno.springbootaopt1.model.User;
import ru.koryruno.springbootaopt1.model.mapper.UserMapper;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;
import ru.koryruno.springbootaopt1.model.responseDto.UserFullDto;
import ru.koryruno.springbootaopt1.repository.UserRepository;
import ru.koryruno.springbootaopt1.service.UserServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
@EnableAspectJAutoProxy
@Import({ValidateAspect.class, AspectJAfterThrowingAdvice.class})
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    private final User user = User.builder().id(1L).name("user").email("user@user.ru").build();
    private final UserFullDto userFullDto  = UserFullDto.builder().id(1L).name("updatedName").email("user@user.ru").build();
    private final NewUserDto newUser = NewUserDto.builder().name("user").email("user@user.ru").build();
    private final UpdateUserDto updateUserDto  = UpdateUserDto.builder().name("updatedName").build();

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(userMapper.toUser(newUser)).thenReturn(user);
        Mockito.lenient().when(userMapper.toUserFullDto(user)).thenReturn(userFullDto);
        Mockito.lenient().when(userRepository.save(any())).thenReturn(user);
    }

    @Test
    public void createUser_withValidFields_Successfully() {
        UserFullDto newUserDto = userServiceImpl.createUser(newUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        assertNotNull(newUserDto);
        assertEquals(userFullDto.getName(), newUserDto.getName());
        assertEquals(userFullDto.getEmail(), newUserDto.getEmail());
        assertEquals(userFullDto.getId(), newUserDto.getId());
    }

    @Test
    public void updateUser_withValidNameField_Successfully() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        UserFullDto updateUser = userServiceImpl.updateUser(1L, updateUserDto);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
        assertNotNull(updateUserDto);
        assertEquals(userFullDto.getName(), updateUser.getName());
        assertEquals(userFullDto.getEmail(), updateUser.getEmail());
        assertEquals(userFullDto.getId(), updateUser.getId());
    }

    @Test
    public void deleteUser_Successfully() {
        userServiceImpl.deleteUser(1L);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    public void getUser_Successfully() {
        Mockito.when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        assertEquals(userFullDto, userServiceImpl.getUser(1L));
    }

    @Test
    public void getAllUsers_Successfully() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        List<UserFullDto> list = userServiceImpl.getAllUsers();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(userFullDto, list.get(0));
    }

}
