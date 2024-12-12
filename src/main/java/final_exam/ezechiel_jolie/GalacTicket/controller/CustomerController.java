package final_exam.ezechiel_jolie.GalacTicket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import final_exam.ezechiel_jolie.GalacTicket.feedback.Feedback;
import final_exam.ezechiel_jolie.GalacTicket.model.Customer;
import final_exam.ezechiel_jolie.GalacTicket.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Feedback> saveCustomer(@RequestBody Customer customer) {
        Feedback feedback = customerService.saveCustomer(customer);
        return ResponseEntity.ok(feedback);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email")
    public ResponseEntity<Customer> getCustomerByEmail(@RequestParam String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<Customer>> searchCustomersByName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.searchCustomersByName(name));
    }

    @GetMapping("/search/address")
    public ResponseEntity<List<Customer>> getCustomersByAddress(@RequestParam String address) {
        return ResponseEntity.ok(customerService.getCustomersByAddress(address));
    }

    @GetMapping("/with-reservations")
    public ResponseEntity<List<Customer>> getCustomersWithReservations() {
        return ResponseEntity.ok(customerService.getCustomersWithReservations());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Feedback> updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Feedback feedback = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(feedback);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Feedback> deleteCustomer(@PathVariable Long id) {
        Feedback feedback = customerService.deleteCustomer(id);
        return ResponseEntity.ok(feedback);
    }
}
