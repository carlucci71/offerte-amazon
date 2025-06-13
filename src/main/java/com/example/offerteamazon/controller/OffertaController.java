package com.example.offerteamazon.controller;

import com.example.offerteamazon.model.Cerca;
import com.example.offerteamazon.model.OffertaAmazon;
import com.example.offerteamazon.repository.OffertaAmazonRepository;
import com.example.offerteamazon.telegram.OfferteBOT;
import com.fasterxml.jackson.annotation.OptBoolean;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@Controller
public class OffertaController {

    private final OffertaAmazonRepository repository;

    public OffertaController(OffertaAmazonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }

    @GetMapping("/elenco")
    public String elenco(Model model) {
        List<OffertaAmazon> offerte = repository.findAll();
        model.addAttribute("offerte", offerte);
        return "elenco";
    }

    @GetMapping("/ricerca")
    public String ricerca(Model model) {
        model.addAttribute("cerca", new Cerca(25));
        return "ricerca";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("offerta", new OffertaAmazon());
        model.addAttribute("offerte", repository.findAll());
        return "admin";
    }

    @PostMapping("/admin/add")
    public String addOfferta(@ModelAttribute OffertaAmazon offerta) {
        repository.save(offerta);
        return "redirect:/admin";
    }

    @PostMapping("/cerca")
    public String cerca(@ModelAttribute Cerca cerca) {
        //repository.save(offerta);
        System.out.println(cerca);
        return "redirect:/";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteOfferta(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/admin";
    }

    @Autowired
    OfferteBOT offerteBOT;

}
