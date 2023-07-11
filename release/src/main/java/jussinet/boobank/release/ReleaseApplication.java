package jussinet.boobank.release;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import jussinet.boobank.release.repository.CustomerRepository;
import jussinet.boobank.release.service.CustomerService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class ReleaseApplication implements CommandLineRunner {

	@Autowired
	public CustomerRepository customerRepository;
	public CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(ReleaseApplication.class, args);
		System.out.println("Initialize an application...");
	}

	@Override
	public void run(String... args) throws Exception {
		// System.out.println("Creating the customer...");
		// Customer customer = new Customer();
		// customer.setEmail("john@doe.com");
		// customer.setPassword("1234");
		// customer.setFirstname("Darke");
		// customer.setLastname("Enms");
		// customer.setBalance((float) 1000.00);
		// customer.setIsActive(true);
		// customerRepository.save(customer);
	}
}
