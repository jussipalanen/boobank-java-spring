package jussinet.boobank.release.controller;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
        Float sum = customerRepository.findCumulativeBalance() != null ? customerRepository.findCumulativeBalance() : 0;
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        String str = format.format(sum);
        model.addAttribute("cumulativeSum", str);

        return "index";
    }

    @GetMapping(value = "/transaction")
    public String getTransaction(Model model) {

        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        model.addAttribute("transaction", transaction);
        model.addAttribute("defaultMethod", "deposit");

        // adding the customers
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);
        return "transaction";
    }

    @GetMapping(value = "/apidocs")
    public String getApiDocs(Model model) {

        String baseApiUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/api/v1";
        model.addAttribute("baseApiUrl", baseApiUrl);
        return "apidocs";
    }

    @PostMapping(value = "/transaction")
    public String postTransaction(Model model, @ModelAttribute("transaction") @Valid Transaction transaction,
            BindingResult result) {

        // adding the customers
        List<Customer> customers = customerRepository.findAll();
        model.addAttribute("customers", customers);

        if (result.hasErrors()) {
            return "transaction";
        }

        Float amount = transaction.getAmount();
        String transferMethod = transaction.getTransferMethod();
        if (transferMethod.equals("withdraw")) {
            amount = -amount;
        } else {
            amount = Math.abs(amount);
        }
        transaction.setAmount(amount);
        transactionRepository.save(transaction);
        return "redirect:/";
    }

    @GetMapping("/balances")
    public String balances(Model model, @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year, @RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
                
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

        // get first day and last day by month and year
        String startDateStr = YearMonth.of(year, month).atDay(1).toString();
        String endDateStr = YearMonth.of(year, month).atEndOfMonth().toString();
        size = size != null && size > 0 ? size : 10;

        Pageable pageable = PageRequest.of((page != null && page > 0 ? (page - 1) : 0), size);
       
        Page<TransactionData> transactions = transactionRepository.findAllPaged(startDateStr, endDateStr, pageable);
        Float monthlyBalance = customerRepository.findMonthlyBalance(startDateStr, endDateStr);
        monthlyBalance = monthlyBalance != null ? monthlyBalance : 0;

        Currency currency = Currency.getInstance("EUR");
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setCurrency(currency);
        String monthBalanceStr = format.format(monthlyBalance);

        // Years
        LocalDate d = LocalDate.now();
        List<Integer> yearsList = new ArrayList<>();
        Integer currentYear = d.getYear();
        yearsList.add(currentYear);
        for (int i = 0; i <= 10; i++) {
            d = d.minusYears(1);
            yearsList.add(d.getYear());
        }

        // Months
        List<Object> monthsList = new ArrayList<Object>();
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < (months.length - 1); i++) {
            Integer monthNumber = (i + 1);
            HashMap<String, Object> monthObject = new HashMap<String, Object>();
            monthObject.put("number", monthNumber);
            monthObject.put("name", months[i]);
            monthsList.add(monthObject);
        }

        // list of transactions
        model.addAttribute("transactions", transactions);

        // month balance
        model.addAttribute("monthBalance", monthBalanceStr);

        // list of years and months
        model.addAttribute("years", yearsList);
        model.addAttribute("months", monthsList);

        // current month and year
        model.addAttribute("month", month);
        model.addAttribute("year", year);

        // pagination
        Integer totalPages = transactions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("size", size);
        return "balances";
    }
}
