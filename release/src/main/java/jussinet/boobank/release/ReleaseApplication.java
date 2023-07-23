package jussinet.boobank.release;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

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

/**
 * Main application
 */
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class })
public class ReleaseApplication implements CommandLineRunner {

	@Autowired
	public CustomerRepository customerRepository;

	@Autowired
	public TransactionRepository transactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(ReleaseApplication.class, args);
		System.out.println("Initialize an application...");

	}

	@Override
	public void run(String... args) throws Exception {

		// Init the test data
		// Comment this, if the test data has been already initialized
		// Comment this start
		LocalDate startDate = LocalDate.now().plus(1, ChronoUnit.DAYS);
		LocalDate endDate = startDate.plus(1, ChronoUnit.MONTHS);
		ZoneId defaultZoneId = ZoneId.systemDefault();

		// Multipiers
		List<Integer> randomList = Arrays.asList(5, 10, 100, 1000);

		// Test customer
		final String TEST_CUSTOMER_EMAIL = "john@doe.com";
		if (customerRepository.findCustomerByEmail(TEST_CUSTOMER_EMAIL) == null) {
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
		// Test transactions
		if (transactionRepository.count() == 0) {
			Transaction transaction = new Transaction();
			transaction.setAmount((float) 100000);
			transaction.setDate(new Date());
			transaction.setCustomer(testCustomer);
			transaction.setMessage("Init");
			transactionRepository.save(transaction);

			for (LocalDate localDate = startDate; localDate.isBefore(endDate); localDate = localDate.plusDays(1)) {
				Date newDate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

				Integer randomNumber = new Random().nextInt(20) - 10;
				if( randomNumber == 0 || randomNumber == null )
				{
					randomNumber = 1;
				}
				Integer randomElement = randomList.get(new Random().nextInt(randomList.size()));

				Transaction subSransaction = new Transaction();
				subSransaction.setAmount((float) (randomElement*randomNumber));
				subSransaction.setDate(newDate);
				subSransaction.setCustomer(testCustomer);
				subSransaction.setMessage("Custom Init");
				transactionRepository.save(subSransaction);
			}

		}
		// Comment this ends

	}
}
