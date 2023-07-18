package jussinet.boobank.release;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

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

		Random random = new Random();
		Customer testCustomer = customerRepository.findCustomerByEmail(TEST_CUSTOMER_EMAIL);
		for (int i = 0; i < 100; i++) {
			Transaction transaction = new Transaction();
			Integer randomAmount = new Random().nextInt(5000 + 10) - 10;
			UUID corrId = UUID.randomUUID();
			transaction.setId(corrId);
			transaction.setAmount( (float) randomAmount);

			// Generate the random dates past 12 months
			LocalDate now = LocalDate.now();
			LocalDate then = now.minusYears(1);
			
			long difference = now.toEpochDay() - then.toEpochDay();
			int randomDifference = random.nextInt((int) difference);
			
			LocalDate randomDate = then.plusDays(randomDifference);
			Date date = Date.from(randomDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			transaction.setDate(date);
			transaction.setMessage("Test commit");
			transaction.setCustomer(testCustomer);
			transactionRepository.save(transaction);
			System.out.println(i);
		}

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
