package bm.app.controller;


import bm.app.service.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@org.springframework.stereotype.Controller
public class Controller {

    private Service service;
    public Controller(){
        this.service = service;
    }

    @RequestMapping(value = "/landingpage", method = RequestMethod.GET)
    public String landingPage(){
        return "/landingpage";
    }

    @RequestMapping(value = "/choosingseat", method = RequestMethod.GET)
    public String choosingSeat(){
        return "/choosingseat";
    }
}
