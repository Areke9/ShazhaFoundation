package kz.arman.shazhafound.settings;

import kz.arman.shazhafound.dao.PersonDAO;
import kz.arman.shazhafound.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PercentageHandler {
    private final PersonDAO personDAO;

    @Value("${personValues.paid}")
    private String paid;
    @Value("${personValues.notPaid}")
    private String notPaid;

    private double statusPerc;

    @Autowired
    public PercentageHandler(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public double getStatusPercentage(String status) {
        List<Person> paidList = personDAO.getPersonStatus(paid);
        List<Person> notPaidList = personDAO.getPersonStatus(notPaid);

        double total = paidList.size() + notPaidList.size();

        switch (status){
            case "paid": statusPerc = (paidList.size() / total) * 100;
            break;
            case  "not paid": statusPerc = (notPaidList.size() / total) * 100;
            break;
        }
        return statusPerc;
    }
}
