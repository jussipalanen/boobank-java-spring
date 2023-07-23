package jussinet.boobank.release;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import jussinet.boobank.release.entity.Customer;
import jussinet.boobank.release.entity.Transaction;
import jussinet.boobank.release.repository.CustomerRepository;
import jussinet.boobank.release.repository.TransactionRepository;
import jussinet.boobank.release.service.CustomerService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class ReleaseApplication implements CommandLineRunner {

	@Autowired
	public CustomerRepository customerRepository;
	public CustomerService customerService;

	@Autowired
	public TransactionRepository transactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(ReleaseApplication.class, args);
		System.out.println("Initialize an application...");
	}

	@Override
	public void run(String... args) throws Exception {

		final String TEST_CUSTOMER_EMAIL = "john@doe.com";
		if( customerRepository.findCustomerByEmail(TEST_CUSTOMER_EMAIL) == null )
		{
			Customer customer = new Customer();
			customer.setFirstname("John");
			customer.setLastname("Doe");
			customer.setEmail(TEST_CUSTOMER_EMAIL);
			customer.setUsername("johndoe");
			customer.setPassword("1234");
			customer.setIsActive(true);
			customer.setBalance((float) 1000);
			customerRepository.save(customer);
		}

		Customer testCustomer = customerRepository.findCustomerByEmail(TEST_CUSTOMER_EMAIL);

		if( transactionRepository.count() == 0)
		{
			Transaction transaction = new Transaction();
			transaction.setAmount( (float) 1000000 );
			transaction.setDate(new Date());
			transaction.setCustomer(testCustomer);
			transaction.setMessage("Init");
			transactionRepository.save( transaction );
		}
	}
}
