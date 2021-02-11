package kz.arman.shazhafound.controllers;

import kz.arman.shazhafound.dao.PersonDAO;
import kz.arman.shazhafound.settings.PercentageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "party/personInfo";
    }

    @GetMapping("monthProcess")
    public String monthProgress(Model model){
        model.addAttribute("party", personDAO.index());
        model.addAttribute("statusPaid", personDAO.getPersonStatus(paid).size());
        model.addAttribute("statusNotPaid", personDAO.getPersonStatus(notPaid).size());
        model.addAttribute("paidPerc", percentageHandler.getStatusPercentage(paid));
        model.addAttribute("notPaidPerc", percentageHandler.getStatusPercentage(notPaid));

        return "party/monthProcess";
    }
}
