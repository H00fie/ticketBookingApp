package bm.app.controller;


import bm.app.model.Complaint;
import bm.app.model.Customer;
import bm.app.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private int seatNumber = 0;

    CustomerService customerService;


    private Controller(CustomerService customerService) {
        this.customerService = customerService;
    }




    @RequestMapping(value = "/landingpage", method = RequestMethod.GET)
    public String landingPage() {
        return "/landingpage";
    }

    @RequestMapping(value = "/choosingseats2", method = RequestMethod.GET)
    public String choosingseats2() {
        return "/choosingseats2";
    }

    @RequestMapping(value = "/results", method = RequestMethod.GET)
    public String results() {
        return "/results";
    }

    @RequestMapping(value = "/nameSearchResult", method = RequestMethod.GET)
    public String nameSearchResult() {
        return "emailSearchResult";
    }

    @RequestMapping(value = "/choosingseat", method = RequestMethod.POST)
    public String getToChoosingSeat() {
        return "/choosingseat";
    }


    @RequestMapping(value = "/choosingseats2", method = RequestMethod.POST)
    public String getToChoosingSeatHtml1(@RequestParam int seatNumber) {
        this.seatNumber = seatNumber;
        return "choosingseats2";
    }

    @RequestMapping(value = "/results", method = RequestMethod.POST)
    public String getToChoosingSeatHtml2(@RequestParam String name,
                                         @RequestParam String email,
                                         @RequestParam String ticketType,
                                         Model model) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setTicketType(ticketType);
        customer.setSeatnumber(seatNumber);
        customerService.insert(customer);
        int id = customerService.getId(customer.getName());
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("tickettype", ticketType);
        model.addAttribute("seatnumber", seatNumber);
        model.addAttribute("id", id);
        return "results";
    }

    @RequestMapping(value = "/bookedseats", method = RequestMethod.POST)
    public String getToBookedSeatsHtmls() {
        return "/bookedseats";
    }

    @RequestMapping(value = "/searchByEmail", method = RequestMethod.POST)
    public String getTicketsByEmail() {
        return "searchByEmail";
    }

    @RequestMapping(value = "/searchByName", method = RequestMethod.POST)
    public String getTicketsByName() {
        return "searchByName";
    }

    @RequestMapping(value = "/searchById", method = RequestMethod.POST)
    public String getTicketsById() {
        return "searchById";
    }

    @RequestMapping(value = "emailSearchResult", method = RequestMethod.POST)
    public String emailSearch(@RequestParam String email,
                              Model model) {
        Customer customer = customerService.getCustomerByEmail(email);
        model.addAttribute("email", email);
        model.addAttribute("name", customer.getName());
        model.addAttribute("tickettype", customer.getTicketType());
        model.addAttribute("seatnumber", customer.getSeatnumber());
        model.addAttribute("id", customer.getId());
        return "emailSearchResult";
    }

    @RequestMapping(value = "nameSearchResult", method = RequestMethod.POST)
    public String nameSearch(@RequestParam String name,
                             Model model) {
        Customer customer = customerService.getCustomerByName(name);
        model.addAttribute("name", name);
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("tickettype", customer.getTicketType());
        model.addAttribute("seatnumber", customer.getSeatnumber());
        model.addAttribute("id", customer.getId());
        return "nameSearchResult";

    }

    @RequestMapping(value = "idSearchResult", method = RequestMethod.POST)
    public String idSearch(@RequestParam int id,
                           Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("id", id);
        model.addAttribute("name", customer.getName());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("tickettype", customer.getTicketType());
        model.addAttribute("seatnumber", customer.getSeatnumber());
        return "idSearchResult";

    }

    @RequestMapping(value = "removeById", method = RequestMethod.POST)
    public String removingRecord() {
        return "removeById";
    }

    @RequestMapping(value = "removedRecord", method = RequestMethod.POST)
    public String removedARecord(@RequestParam int id,
                                 Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("name", customer.getName());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("tickettype", customer.getTicketType());
        model.addAttribute("seatnumber", customer.getSeatnumber());
        customerService.deleteById(id);
        model.addAttribute("id", id);
        return "removedRecord";
    }

    @RequestMapping(value = "updateEmailById", method = RequestMethod.POST)
    public String updatingRecord() {
        return "updateEmailById";
    }

    @RequestMapping(value = "emailUpdateResult", method = RequestMethod.POST)
    public String emailUpdateResult(@RequestParam int id,
                                    @RequestParam String email,
                                    Model model) {
        Customer customer = customerService.getCustomerById(id);
        model.addAttribute("foundEmail", customer.getEmail());
        customerService.updateEmail(id, email);
        model.addAttribute("id", id);
        model.addAttribute("email", email);
        return "emailUpdateResult";
    }


    @RequestMapping(value = "/complaint", method = RequestMethod.POST)
    public String getToComplaintHtml() {
        return "/complaint";
    }

    @RequestMapping(value = "complaintTicket", method = RequestMethod.POST)
    public String lostTicketComplaint() {
        return "complaintTicket";
    }

    @RequestMapping(value = "complaintTicketResult", method = RequestMethod.POST)
    public String lostTicketComplaintResult(@RequestParam int id,
                                            @RequestParam String content,
                                            Model model) {
        customerService.insertLostTicketComplaint(id, content);

        model.addAttribute("id", id);
        model.addAttribute("email", customerService.getEmailById(id));
        return "complaintTicketResult";
    }

    @RequestMapping(value = "complaintFlightsDetails", method = RequestMethod.POST)
    public String lackOfDetailsComplaint(){
        return "complaintFlightsDetails";
    }

    @RequestMapping(value = "complaintFlightsDetailsResult", method = RequestMethod.POST)
    public String lackOfDetailsComplaintResult(@RequestParam int id,
                                               @RequestParam String content,
                                               Model model){
        customerService.insertDidntReceiveDetailsComplaint(id, content);
        model.addAttribute("id", id);
        model.addAttribute("email", customerService.getEmailById(id));
        return "complaintFlightsDetailsResult";
    }

    @RequestMapping(value = "complaintSearchMalfunctional", method = RequestMethod.POST)
    public String searchEngineComplaint(){
        return "complaintSearchMalfunctional";
    }

    @RequestMapping(value = "complaintSearchMalfunctionalResult", method = RequestMethod.POST)
    public String searchEngineComplaintResult(@RequestParam int id,
                                              @RequestParam String content,
                                              Model model){
        customerService.insertSearchEngineComplaint(id, content);
        model.addAttribute("id", id);
        model.addAttribute("email", customerService.getEmailById(id));
        return "complaintSearchMalfunctionalResult";
    }

    @RequestMapping(value = "showMyComplaints", method = RequestMethod.POST)
    public String showMyComplaints(){
        return "showMyComplaints";
    }

    @RequestMapping(value = "showMyComplaintsResult", method = RequestMethod.POST)
    public String showMyComplaintsResult(@RequestParam int id,
                                         Model model){

        Complaint complaint = customerService.selectAComplaintById(id);
        model.addAttribute("id", id);
        model.addAttribute("complaint", complaint);
        return "showMyComplaintsResult";
    }

    @RequestMapping(value = "/showAllComplaints", method = RequestMethod.POST)
    public String showAllComplaints(Model model) {
        List<Complaint> complaintList = customerService.selectAllComplaints();

        model.addAttribute("complaintList", complaintList);


        // TODO możesz zrobić iterację po liście w thymeleafie chyba... Tutaj TRZEBA zrobić iterację w thymeleafie

        return "showAllComplaints";

    }

}
