package final_exam.ezechiel_jolie.GalacTicket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Customer;
import final_exam.ezechiel_jolie.GalacTicket.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Feedback saveCustomer(Customer customer) {
        if (customer == null) {
            return new Feedback("Invalid customer data");
        }

        if (customerRepository.existsByEmail(customer.getEmail())) {
            return new Feedback("Customer with email " + customer.getEmail() + " already exists");
        }

        if (customerRepository.existsByContactNumber(customer.getContactNumber())) {
            return new Feedback("Customer with contactNumber " + customer.getContactNumber() + " already exists");
        }

        Customer savedCustomer = customerRepository.save(customer);
        return new Feedback("Customer saved successfully: " + savedCustomer.getFullName());
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> searchCustomersByFullName(String name) {
        return customerRepository.findByFullNameContainingIgnoreCase(name);
    }

    public List<Customer> getCustomersByResidence(String residence) {
        return customerRepository.findByEmailContainingIgnoreCase(residence);
    }

    public List<Customer> getCustomersWithHeldReservations() {
        return customerRepository.findByHeldReservationsIsNotEmpty();
    }

    public Feedback updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            return new Feedback("Customer not found");
        }

        Customer existingCustomer = optionalCustomer.get();
        existingCustomer.setFullName(updatedCustomer.getFullName());
        existingCustomer.setEmail(updatedCustomer.getEmail());
        existingCustomer.setContactNumber(updatedCustomer.getContactNumber());
        existingCustomer.setResidence(updatedCustomer.getResidence());

        customerRepository.save(existingCustomer);
        return new Feedback("Customer updated successfully");
    }

    public Feedback deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            return new Feedback("Customer not found");
        }

        customerRepository.deleteById(id);
        return new Feedback("Customer deleted successfully");
    }
}
