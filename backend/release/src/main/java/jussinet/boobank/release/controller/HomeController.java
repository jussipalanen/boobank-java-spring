package jussinet.boobank.release.controller;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import jussinet.boobank.release.entity.Customer;
import jussinet.boobank.release.entity.Transaction;
import jussinet.boobank.release.repository.CustomerRepository;
import jussinet.boobank.release.repository.TransactionData;
import jussinet.boobank.release.repository.TransactionRepository;

@Controller
public class HomeController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    HomeController(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        Currency currency = Currency.getInstance("EUR");

        // get the cumulative sum
        Float sum = customerRepository.findCumulativeSum() != null ? customerRepository.findCumulativeSum() : 0;
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        String str = format.format(sum);
        model.addAttribute("cumulativeSum", str);

        return "index";
    }

    @GetMapping(value = "/transaction")
    public String getTransaction(Model model) {
        model.addAttribute("transaction", new Transaction());

        // adding the customers
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "transaction";
    }

    @GetMapping(value = "/apidocs")
    public String getApiDocs(Model model) {

        String baseApiUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/api";
        model.addAttribute("baseApiUrl", baseApiUrl);
        return "apidocs";
    }

    @PostMapping(value = "/transaction")
    public String postTransaction(Model model, @ModelAttribute("transaction") @Valid Transaction transaction, BindingResult result) {

        if( result.hasErrors() )
        {
            return "redirect:/transaction";
        }

        Float amount = transaction.getAmount();
        String transferMethod = transaction.getTransferMethod();
        if (transferMethod.equals("withdraw")) {
            System.out.println("Convert negative");
            amount = -amount;
        } else {
            amount = Math.abs(amount);
        }
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/monthly")
    public String monthly(Model model, @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if (month == null) {

            // Default month, if null
            month = cal.get(Calendar.MONTH) + 1;
        }

        if (year == null) {
            // Default yhear, if null
            year = cal.get(Calendar.YEAR);
        }

        System.out.println(month);

        List<TransactionData> transactions = transactionRepository.findAllTransactions(month, year, null);
        Float monthlyBalance = customerRepository.findMonthlyBalance(month, year);
        monthlyBalance = monthlyBalance != null ? monthlyBalance : 0;

        Currency currency = Currency.getInstance("EUR");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        String monthBalanceStr = format.format(monthlyBalance);

        LocalDate d = LocalDate.now();
        List<Integer> yearsList = new ArrayList<>();
        Integer currentYear = d.getYear();
        yearsList.add(currentYear);
        for (int i = 0; i <= 10; i++) {
            d = d.minusYears(1);
            yearsList.add(d.getYear());
        }

        List<Object> monthsList = new ArrayList<Object>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < (months.length - 1); i++) {
            Integer monthNumber = (i + 1);
            HashMap<String, Object> monthObject = new HashMap<String, Object>();
            monthObject.put("number", monthNumber);
            monthObject.put("name", months[i]);
            monthsList.add(monthObject);
        }

        // List of transactions
        model.addAttribute("transactions", transactions);

        // Month balance
        model.addAttribute("monthBalance", monthBalanceStr);

        // List of years and months
        model.addAttribute("years", yearsList);
        model.addAttribute("months", monthsList);

        // Current month and year
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        return "monthly";
    }
}
