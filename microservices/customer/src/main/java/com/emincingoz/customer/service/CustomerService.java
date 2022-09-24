package com.emincingoz.customer.service;

import com.emincingoz.customer.model.Customer;
import com.emincingoz.customer.repository.CustomerRepository;
import com.emincingoz.customer.requests.CustomerRegisterRequest;
import com.emincingoz.customer.response.FraudCheckResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public record CustomerService(CustomerRepository customerRepository, RestTemplate restTemplate) {
    public void registerCustomer(CustomerRegisterRequest customerRegisterRequest) {

        Customer customer = Customer.builder()
                .firstName(customerRegisterRequest.firstName())
                .lastName(customerRegisterRequest.lastName())
                .email(customerRegisterRequest.email())
                .build();

        //TODO:: check if email valid
        //TODO:: check if email not taken

        // saveAndFlush() is used to write to the db instantly.
        // When the save() method is used, it is not written instantly.
        // It is kept in memory until flush or commit commands are received.
        customerRepository.saveAndFlush(customer);

        //TODO:: check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://localhost:8082/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster");
        }

        //TODO:: send notification
    }
}
