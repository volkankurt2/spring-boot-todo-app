package com.example.todo.service.mapper;


public class RetailCustomerMapper {

    private RetailCustomerMapper(){
        throw new IllegalStateException("Mapping class!");
    }

    /*public static RetailCustomer customerDtoToRetailCustomer(CustomerDTO customerDTO) {
        return RetailCustomer.builder()
            .firstName(customerDTO.getFirstName())
            .lastName(customerDTO.getLastName())
            .email(customerDTO.getEmail())
            .phone(customerDTO.getPhone())
            .tckNo(customerDTO.getTckNo())
            .build();
    }*/
}
