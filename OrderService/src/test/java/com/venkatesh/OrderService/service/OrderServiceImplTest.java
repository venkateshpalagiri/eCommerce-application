package com.venkatesh.OrderService.service;


import com.venkatesh.OrderService.entity.Order;
import com.venkatesh.OrderService.external.client.PaymentService;
import com.venkatesh.OrderService.external.client.ProductService;
import com.venkatesh.OrderService.model.OrderResponse;
import com.venkatesh.OrderService.repository.OrderRepository;
import com.venkatesh.ProductService.model.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;

import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    OrderService orderService=new OrderServiceImpl();

    @DisplayName("Get Order - Success Scenario")
    @Test
    void test_When_Order_Success(){

        //Mocking
        Order order=getMockOrder();
        when(orderRepository.findById(anyLong()))
                .thenReturn(Optional.of(order));

        when(restTemplate.getForObject("http://PRODUCT-SERVICE/product/"+order.getId(), ProductResponse.class))
                .thenReturn(getMockProductResponse());

        //Actual
        OrderResponse orderResponse=orderService.getOrderDetails(1);
        //Verification
        Mockito.verify(orderRepository, times(1)).findById(anyLong());
        //Assert
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(order.getId(),orderResponse.getOrderId());

    }





    private ProductResponse getMockProductResponse() {
        return ProductResponse.builder()
                .productName("iPhone")
                .productId(1)
                .quantity(10)
                .price(1200)
                .build();
    }

    private Order getMockOrder() {
        return Order.builder()
                .productId(1)
                .quantity(10)
                .orderDate(Instant.now())
                .orderStatus("Placed")
                .amount(1200)
                .build();
    }

}