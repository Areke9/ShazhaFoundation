package kz.arman.shazhafound.controllers;

import kz.arman.shazhafound.dao.PersonDAO;
import kz.arman.shazhafound.model.Person;
import kz.arman.shazhafound.settings.PercentageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/party")
public class PartyController {

    private final PersonDAO personDAO;
    private final PercentageHandler percentageHandler;

    @Value("${personValues.paid}")
    private String paid;
    @Value("${personValues.notPaid}")
    private String notPaid;

    @Autowired
    public PartyController(PersonDAO personDAO, PercentageHandler percentageHandler){
        this.personDAO = personDAO;
        this.percentageHandler = percentageHandler;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("party", personDAO.index());
        return "party/index";
    }

    @GetMapping("/{id}")
    public String personInfo(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.personInfo(id));
        return "party/person_info";
    }

    @GetMapping("month_progress")
    public String monthProgress(Model model){
        model.addAttribute("party", personDAO.index());
        model.addAttribute("statusPaid", personDAO.getPersonStatus(paid).size());
        model.addAttribute("statusNotPaid", personDAO.getPersonStatus(notPaid).size());
        model.addAttribute("paidPerc", percentageHandler.getStatusPercentage(paid));
        model.addAttribute("notPaidPerc", percentageHandler.getStatusPercentage(notPaid));

        return "party/month_progress";
    }

    @GetMapping("/new_person")
    public String newPerson(@ModelAttribute("person") Person person){
        return "party/new_person";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person")
                                      @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "party/new_person";
        }
        personDAO.saveNewPerson(person);

        return "redirect:/party";
    }

    @GetMapping("/{id}/edit_person")
    public String editPerson(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.personInfo(id));
        return "party/edit_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult,
                               @PathVariable("id") int id){
        if (bindingResult.hasErrors()){
            return "party/edit_person";
        }
        personDAO.updatePerson(person, id);
        return "redirect:/party";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/party";
    }
}
