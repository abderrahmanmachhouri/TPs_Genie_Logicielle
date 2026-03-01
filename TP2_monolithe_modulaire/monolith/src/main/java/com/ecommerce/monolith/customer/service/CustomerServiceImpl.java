package com.ecommerce.monolith.customer.service;

import com.ecommerce.monolith.customer.model.Customer;
import com.ecommerce.monolith.customer.repository.CustomerRepository;
import com.ecommerce.monolith.customer.dto.CreateCustomerRequest;
import com.ecommerce.monolith.customer.dto.CustomerDTO;
import com.ecommerce.monolith.customer.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return mapper.toDTOList(repository.findAll());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        return mapper.toDTO(customer);
    }

    @Override
    public CustomerDTO createCustomer(CreateCustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        customer.setCreatedAt(LocalDateTime.now());
        Customer saved = repository.save(customer);
        return mapper.toDTO(saved);
    }

    @Override
    @Transactional
    public CustomerDTO updateCustomer(Long id, CreateCustomerRequest request) {
        Customer customer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        mapper.updateEntity(request, customer);
        return mapper.toDTO(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
