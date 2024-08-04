package ru.koryruno.springbootaopt1;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.event.EventListener;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.NewUserDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateUserDto;
import ru.koryruno.springbootaopt1.service.OrderService;
import ru.koryruno.springbootaopt1.service.UserService;
import ru.koryruno.springbootaopt1.utils.ThreadUtils;
import ru.koryruno.springbootaopt1.utils.UserContext;

@SpringBootApplication
@EnableAspectJAutoProxy
@RequiredArgsConstructor
public class SpringbootaopApplication {
	private final UserService userService;
	private final OrderService orderService;

	NewUserDto newUser = NewUserDto.builder()
			.name("user")
			.email("user@user.ru")
			.build();

	NewUserDto newUser2 = NewUserDto.builder()
			.name("user2")
			.email("user2@user.ru")
			.build();

	UpdateUserDto updateUserDto = UpdateUserDto.builder()
			.name("updatedUser")
			.build();

	NewOrderDto newOrder = NewOrderDto.builder()
			.description("orderDescription")
			.build();

	UpdateOrderDto updateOrder = UpdateOrderDto.builder()
			.description("orderDescription")
			.status("PROCESSING")
			.build();

	public static void main(String[] args) {
		SpringApplication.run(SpringbootaopApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		UserContext.setUSERNAME("admin");

		System.out.println(userService.createUser(newUser));
		ThreadUtils.waitTime(200L);
		System.out.println(userService.getUser(1L));


		System.out.println(userService.createUser(newUser2));
		ThreadUtils.waitTime(200L);
		System.out.println(userService.getUser(2L));


		System.out.println(orderService.createOrder(1L, newOrder));
		ThreadUtils.waitTime(200L);
		System.out.println(orderService.getOrder(1L));

		System.out.println(orderService.updateOrder(1L, 1L, updateOrder));
		ThreadUtils.waitTime(200L);
		System.out.println(orderService.getOrder(1L));



		orderService.deleteOrder(1L);
//		System.out.println(orderService.getOrder(1L));

		userService.deleteUser(1L);
		System.out.println(userService.getAllUsers());
	}

}
