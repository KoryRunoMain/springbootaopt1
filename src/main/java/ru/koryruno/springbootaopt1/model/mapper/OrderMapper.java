package ru.koryruno.springbootaopt1.model.mapper;

import org.springframework.stereotype.Component;
import ru.koryruno.springbootaopt1.model.OrderDetails;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.responseDto.OrderFullDto;

@Component
public class OrderMapper {

    public OrderDetails toOrder(NewOrderDto newOrderDto) {
        return OrderDetails.builder()
                .description(newOrderDto.getDescription())
                .build();
    }

    public OrderFullDto toOrderFullDto(OrderDetails order) {
        return OrderFullDto.builder()
                .id(order.getId())
                .description(order.getDescription())
                .status(order.getStatus().toString())
                .initiator(order.getInitiator().getId())
                .build();
    }

}
