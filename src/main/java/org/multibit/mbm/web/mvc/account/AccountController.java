package org.multibit.mbm.web.mvc.account;

import org.multibit.mbm.domain.Customer;
import org.multibit.mbm.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * <p>Restricted Controller for Account page</p>
 *
 * @since 1.0.0
 */
@Controller
public class AccountController {

  @Resource
  private CustomerService customerService = null;

  @RequestMapping("/account.html")
  public String getView(Model model, Principal principal) {

    // Retrieve the Customer to form the model (if they are authenticated then they will be present)
    Customer customer=customerService.getCustomerFromPrincipal(principal);


    model.addAttribute("emailAddress", customer.getEmailAddress());
    return "account/index";
  }

  public void setCustomerService(CustomerService customerService) {
    this.customerService = customerService;
  }
}