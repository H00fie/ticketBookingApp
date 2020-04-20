package bm.app.controller;


import bm.app.model.Customer;
import bm.app.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class Controller {

    private int seatnumber = 0;

    CustomerService customerService;
    private Controller(CustomerService customerService){
        this.customerService = customerService;
    }


    @RequestMapping(value = "/landingpage", method = RequestMethod.GET)
    public String landingPage(){
        return "/landingpage";
    }
    @RequestMapping(value = "/choosingseats2", method = RequestMethod.GET)
    public String choosingseats2(){
        return "/choosingseats2";
    }
    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public String results(){
        return "/results";
    }

    @RequestMapping(value = "/choosingseat", method = RequestMethod.POST)
    public String getToChoosingSeat(){
        return "/choosingseat";
    }


    @RequestMapping(value = "/choosingseats2", method = RequestMethod.POST)
    public String getToChoosingSeatHtml1(@RequestParam int seatNumber){
        this.seatnumber = seatNumber;
        return "choosingseats2";
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    public String getToChoosingSeatHtml2(@RequestParam String name,
                                         @RequestParam String email,
                                         @RequestParam String ticketType,
                                         Model model){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setTicketType(ticketType);
        customer.setSeatnumber(seatnumber);
        customerService.insert(customer);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("ticketType", ticketType);
        return "results";
    }

    @RequestMapping(value = "/bookedseats", method = RequestMethod.POST)
    public String getToBookedSeatsHtmls(){
        return "/bookedseats";
    }

    @RequestMapping(value = "/complaint", method = RequestMethod.POST)
    public String getToComplaintHtml(){
        return "/complaint";
    }

}
