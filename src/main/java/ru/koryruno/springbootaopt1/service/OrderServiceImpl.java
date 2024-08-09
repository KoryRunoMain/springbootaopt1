package ru.koryruno.springbootaopt1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.koryruno.springbootaopt1.annotation.Asynchronously;
import ru.koryruno.springbootaopt1.annotation.PreInvoke;
import ru.koryruno.springbootaopt1.annotation.SuccessLogging;
import ru.koryruno.springbootaopt1.exception.NotFoundException;
import ru.koryruno.springbootaopt1.model.OrderDetails;
import ru.koryruno.springbootaopt1.model.OrderStatus;
import ru.koryruno.springbootaopt1.model.RoleType;
import ru.koryruno.springbootaopt1.model.User;
import ru.koryruno.springbootaopt1.model.mapper.OrderMapper;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.responseDto.OrderFullDto;
import ru.koryruno.springbootaopt1.repository.OrderRepository;
import ru.koryruno.springbootaopt1.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@SuccessLogging
public class OrderServiceImpl implements OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    @Asynchronously
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public OrderFullDto createOrder(Long userId, NewOrderDto newOrderDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("User with id:{} not found", userId)));
        OrderDetails order = orderMapper.toOrder(newOrderDto);
        order.setInitiator(user);
        order.setStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return orderMapper.toOrderFullDto(order);
    }

    @Override
    @Asynchronously
    @PreInvoke(roles = {RoleType.ADMIN})
    public OrderFullDto updateOrder(Long userId, Long orderId, UpdateOrderDto updateOrderDto) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id:{} not found", userId)));
        OrderDetails order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id:{} not found", orderId)));
        order.setDescription(updateOrderDto.getDescription());
        order.setStatus(OrderStatus.valueOf(updateOrderDto.getStatus()));
        orderRepository.save(order);
        return orderMapper.toOrderFullDto(order);
    }

    @Override
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public OrderFullDto getOrder(Long orderId) {
        OrderDetails order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException(String.format("Order with id:{} not found", orderId)));
        return orderMapper.toOrderFullDto(order);
    }

    @Override
    @PreInvoke(roles = {RoleType.ADMIN, RoleType.USER})
    public List<OrderFullDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toOrderFullDto)
                .toList();
    }

    @Override
    @PreInvoke(roles = {RoleType.ADMIN})
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

}
