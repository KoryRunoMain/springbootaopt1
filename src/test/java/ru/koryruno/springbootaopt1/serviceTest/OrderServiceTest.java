package ru.koryruno.springbootaopt1.serviceTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import ru.koryruno.springbootaopt1.aspect.ValidateAspect;
import ru.koryruno.springbootaopt1.model.OrderDetails;
import ru.koryruno.springbootaopt1.model.OrderStatus;
import ru.koryruno.springbootaopt1.model.User;
import ru.koryruno.springbootaopt1.model.mapper.OrderMapper;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.responseDto.OrderFullDto;
import ru.koryruno.springbootaopt1.repository.OrderRepository;
import ru.koryruno.springbootaopt1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import ru.koryruno.springbootaopt1.service.OrderServiceImpl;

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
@ContextConfiguration(classes = {OrderServiceImpl.class, ValidateAspect.class})
public class OrderServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    private final User user = User.builder().id(1L).name("user").email("user@user.ru").build();
    private final OrderDetails order = OrderDetails.builder().id(1L).description("description").status(OrderStatus.PENDING).initiator(user).build();
    private final OrderFullDto orderFullDto = OrderFullDto.builder().id(1L).description("description").status("PENDING").initiator(user.getId()).build();
    private final NewOrderDto newOrder = NewOrderDto.builder().description("orderDescription").build();
    private final UpdateOrderDto updateOrderDto = UpdateOrderDto.builder().description("updatedOrderDescription").status("PROCESSING").build();

    @BeforeEach
    void setUp() {
        Mockito.lenient().when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        Mockito.lenient().when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        Mockito.lenient().when(orderMapper.toOrder(any())).thenReturn(order);
        Mockito.lenient().when(orderRepository.save(any())).thenReturn(order);
        Mockito.lenient().when(orderMapper.toOrderFullDto(any())).thenReturn(orderFullDto);
    }

    @Test
    public void createOrder_withValidFields_Successfully() {
        OrderFullDto newOrderDto = orderServiceImpl.createOrder(1L, newOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
        assertNotNull(newOrderDto);
        assertEquals(orderFullDto.getDescription(), newOrderDto.getDescription());
        assertEquals(orderFullDto.getStatus(), newOrderDto.getStatus());
        assertEquals(orderFullDto.getInitiator(), newOrderDto.getInitiator());
        assertEquals(orderFullDto.getId(), newOrderDto.getId());
    }

    @Test
    public void updateOrder_withValidFields_Successfully() {
        OrderFullDto updatedOrder = orderServiceImpl.updateOrder(1L, 1L, updateOrderDto);
        Mockito.verify(orderRepository, Mockito.times(1)).save(order);
        assertNotNull(updateOrderDto);
        assertEquals(orderFullDto.getDescription(), updatedOrder.getDescription());
        assertEquals(orderFullDto.getStatus(), updatedOrder.getStatus());
        assertEquals(orderFullDto.getInitiator(), updatedOrder.getInitiator());
        assertEquals(orderFullDto.getId(), updatedOrder.getId());
    }

    @Test
    public void getOrder_Successfully() {
        assertEquals(orderFullDto, orderServiceImpl.getOrder(1L));
    }

    @Test
    public void getAllOrders_Successfully() {
        Mockito.when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));
        List<OrderFullDto> list = orderServiceImpl.getAllOrders();
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
        assertEquals(orderFullDto, list.get(0));
    }

    @Test
    public void deleteOrder_Successfully() {
        orderServiceImpl.deleteOrder(1L);
        Mockito.verify(orderRepository, Mockito.times(1)).deleteById(1L);
    }

}
