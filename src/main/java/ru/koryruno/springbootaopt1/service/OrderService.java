package ru.koryruno.springbootaopt1.service;

import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.responseDto.OrderFullDto;

import java.util.List;

public interface OrderService {

    OrderFullDto createOrder(Long userId, NewOrderDto newOrderDto);

    OrderFullDto updateOrder(Long userId, Long orderId, UpdateOrderDto updateOrderDto);

    OrderFullDto getOrder(Long orderId);

    List<OrderFullDto> getAllOrders();

    void deleteOrder(Long orderId);

}
