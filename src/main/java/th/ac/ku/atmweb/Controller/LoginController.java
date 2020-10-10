package th.ac.ku.atmweb.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import th.ac.ku.atmweb.Model.Customer;
import th.ac.ku.atmweb.Service.BankAccountService;
import th.ac.ku.atmweb.Service.CustomerService;

@Controller
@RequestMapping("/login")
public class LoginController {

    private CustomerService customerService;
    private BankAccountService bankAccountService ;

    public LoginController(CustomerService customerService, BankAccountService bankAccountService) {
        this.customerService = customerService;
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public String getLoginPage() {
        return "login";   // return login.html
    }

    @PostMapping
    public String login(@ModelAttribute Customer customer, Model model) {
        Customer matchingCustomer = customerService.checkPin(customer);

        if (matchingCustomer != null) {
            model.addAttribute("customertitle",
                    matchingCustomer.getName()+ "Bank Accounts");
            model.addAttribute("bankaccounts",
                    bankAccountService.getCustomerBankAccounts(customer.getId()));
            return "customeraccount";
        } else {
            model.addAttribute("greeting", "Can't find customer");
        }
        return "home";
    }

}
