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
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.responseDto.OrderFullDto;
import ru.koryruno.springbootaopt1.service.OrderService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping(path = "/users/{userId}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderFullDto create(@PathVariable Long userId,
                               @Valid @RequestBody NewOrderDto newOrderDto) {
        return service.createOrder(userId, newOrderDto);
    }

    @PatchMapping(path = "/users/{userId}/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderFullDto update(@PathVariable Long userId,
                                    @PathVariable Long orderId,
                                    @Valid @RequestBody UpdateOrderDto updateOrderDto) {
        return service.updateOrder(userId, orderId, updateOrderDto);
    }

    @GetMapping(path = "/users/orders/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderFullDto get(@PathVariable Long orderId) {
        return service.getOrder(orderId);
    }

    @GetMapping(path = "/users/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderFullDto> getAll() {
        return service.getAllOrders();
    }

    @DeleteMapping(path = "/admin/orders/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
    }

}
