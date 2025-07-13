package org.example.springdebugger.service.customer;

import org.example.springdebugger.model.Customer;
import org.example.springdebugger.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final Random random = new Random();
    private final CustomerHelper helper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerHelper helper) {
        this.customerRepository = customerRepository;
        this.helper = helper;
    }

    @Transactional
    public void createRandomCustomer(String password) {
        String username = "user_" + UUID.randomUUID().toString().substring(0, 8);
        String email = username + "@example.com";
        String firstName = getRandomFirstName();
        String lastName = getRandomLastName();
        boolean active = random.nextBoolean();
        Customer customer = new Customer(username, email, password, firstName, lastName, active);
        customerRepository.save(customer);
        log.info("Created customer: {}", customer);
    }

    private String getRandomFirstName() {
        String[] firstNames = {"John", "Jane", "Bob", "Alice", "Charlie", "Diana", "Edward", "Fiona", "George", "Hannah"};
        return firstNames[random.nextInt(firstNames.length)];
    }

    private String getRandomLastName() {
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor"};
        return lastNames[random.nextInt(lastNames.length)];
    }

    public List<Customer> getAllCustomers() {
        List<Customer> all = customerRepository.findAll();
        helper.trace(all);
        return all;
    }
}