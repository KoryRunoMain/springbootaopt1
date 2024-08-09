package ru.koryruno.springbootaopt1.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.koryruno.springbootaopt1.controller.OrderController;
import ru.koryruno.springbootaopt1.exception.ErrorHandler;
import ru.koryruno.springbootaopt1.model.requestDto.NewOrderDto;
import ru.koryruno.springbootaopt1.model.requestDto.UpdateOrderDto;
import ru.koryruno.springbootaopt1.model.responseDto.OrderFullDto;
import ru.koryruno.springbootaopt1.service.OrderService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
public class OrderControllerTest {

    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;

    private MockMvc mvc;
    private final ObjectMapper mapper = new ObjectMapper();

    private static final Long ORDER_ID = 1L;
    private static final Long USER_ID = 1L;

    private final NewOrderDto newOrderDto = new NewOrderDto("order description");
    private final UpdateOrderDto updateOrderDto = new UpdateOrderDto("updated order details", "PENDING");
    private final OrderFullDto orderFullDto = new OrderFullDto(ORDER_ID, "order description", "PROCESSING", USER_ID);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(orderController)
                .setControllerAdvice(new ErrorHandler())
                .build();
    }

    @Test
    void testCreateOrder_Success() throws Exception {
        when(orderService.createOrder(eq(USER_ID), any(NewOrderDto.class))).thenReturn(orderFullDto);
        mvc.perform(post("/users/{userId}/orders", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(newOrderDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateOrder_Success() throws Exception {
        when(orderService.updateOrder(eq(USER_ID), eq(ORDER_ID), any(UpdateOrderDto.class))).thenReturn(orderFullDto);
        mvc.perform(patch("/users/{userId}/orders/{orderId}", USER_ID, ORDER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateOrderDto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOrder_Success() throws Exception {
        when(orderService.getOrder(ORDER_ID)).thenReturn(orderFullDto);
        mvc.perform(get("/users/orders/{orderId}", ORDER_ID))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllOrders_Success() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(orderFullDto));
        mvc.perform(get("/users/orders"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteOrder_Success() throws Exception {
        doNothing().when(orderService).deleteOrder(ORDER_ID);
        mvc.perform(delete("/admin/orders/{orderId}", ORDER_ID))
                .andExpect(status().isNoContent());
    }

}
