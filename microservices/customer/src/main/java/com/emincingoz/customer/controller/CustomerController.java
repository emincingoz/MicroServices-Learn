package com.emincingoz.customer.controller;

import com.emincingoz.customer.service.CustomerService;
import com.emincingoz.customer.requests.CustomerRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customer")
public record CustomerController(CustomerService customerService) {

    @PostMapping()
    public void registerCustomer(@RequestBody CustomerRegisterRequest customerRegisterRequest) {
        log.info("New Customer Registration {}", customerRegisterRequest);
        customerService.registerCustomer(customerRegisterRequest);
    }
}
