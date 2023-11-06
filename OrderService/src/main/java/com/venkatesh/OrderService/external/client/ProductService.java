package com.venkatesh.OrderService.external.client;

import com.venkatesh.OrderService.exception.CustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.Path;
@CircuitBreaker(name = "external",fallbackMethod = "fallback")
@FeignClient(name="PRODUCT-SERVICE/product")
public interface ProductService {
    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(@PathVariable("id") long productId, @RequestParam long quantity);
    default void fallback(Exception e){
        throw new CustomException("reduce quantitiy method is down","SERVICE_DOWN",500);
    }
}
