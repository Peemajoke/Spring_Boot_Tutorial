package com.jobpreparing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@SpringBootApplication
//@RestController // This makes every function that return class return as JSON instead. That's why greetJSON that return class GreetResponse is return JSON instead.
//public class Main {
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class, args);
//    }
//
//    @GetMapping("/greet")
//    public String greet(){
//        return "Hello";
//    }
//
//    @GetMapping("/greetjson")
//    public GreetResponse greetJson(){
//        return new GreetResponse("hello");
//    }
//
//    record GreetResponse(String greet){} // record is like class that is final, contain field just equal to the record constructor and has 3 function: getter methods and a hashCode() and equals() method. We can also include static variables and methods in our records.
//
//    // The following class is exactly the same as record above.
//    class GreetResponseRecordIsEqualToThis{
//        private final String greet;
//
//
//        GreetResponseRecordIsEqualToThis(String greet) {
//            this.greet = greet;
//        }
//
//        public String getGreet() { // This getter is called when we return the class out as response.
//            return greet + " haha";
//        }
//
//        @Override
//        public String toString() {
//            return "GreetResponseRecordIsEqualToThis{" +
//                    "greet='" + greet + '\'' +
//                    '}';
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            if (this == o) return true;
//            if (o == null || getClass() != o.getClass()) return false;
//            GreetResponseRecordIsEqualToThis that = (GreetResponseRecordIsEqualToThis) o;
//            return Objects.equals(greet, that.greet);
//        }
//
//        @Override
//        public int hashCode() {
//            return Objects.hash(greet);
//        }
//    }
//
//    record Person(String name, int age, double saving){}
//    record UpdatedGreetResponse(String greet, List<String> favProgrammingLangs, Person person){}
//
//    @GetMapping("/updatedGreet")
//    public UpdatedGreetResponse UpdatedGreet(){
//        return new UpdatedGreetResponse("hello", List.of("Java", "Python", "P H P"), new Person("Batman", 205, 10000000.22));
//    }
//
//    // This onward is the endpoint that connects to PostgreSQL
//    private final CustomerRepository customerRepository;
//    @GetMapping("/customers")
//    public List<Customer> getCustomers(){
//        return  List.of();
//    }
//}

// This onward is the endpoint that connects to PostgreSQL

@SpringBootApplication
@RestController // This makes every function that return class return as JSON instead. That's why greetJSON that return class GreetResponse is return JSON instead.
@RequestMapping("api/v1/customers") // Add prefix to API request.
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;
    @GetMapping("")
    public List<Customer> getCustomers(){
        return  customerRepository.findAll();
    }

    record NewCustomerRequest( // This should actuelly be stored in a seperate place.
            String name,
            String email,
            Integer age
    ){}

    @PostMapping("")
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer = new Customer();
        customer.setName(request.name);
        customer.setEmail(request.email);
        customer.setAge(request.age);
        customerRepository.save(customer);
    }

    @DeleteMapping("{customerId}") // name of the path variable
    public void deleteCustomer(@PathVariable("customerId") Integer id){ // customerId is the name of the variable to have its value = Integer id
        customerRepository.deleteById(id);
    }

    @PutMapping("{customerId}")
    public boolean updateCustomer(@RequestBody NewCustomerRequest req, @PathVariable("customerId") Integer id){
        Optional<Customer> customer = customerRepository.findById(id);
        if(customer.isPresent()){
            Customer updatedCustomer = customer.get();
            updatedCustomer.setName(req.name);
            updatedCustomer.setEmail(req.email);
            updatedCustomer.setAge(req.age);
            updatedCustomer.setId(id);
            customerRepository.save(updatedCustomer);
        return true;
        }
        return false;
    }
}