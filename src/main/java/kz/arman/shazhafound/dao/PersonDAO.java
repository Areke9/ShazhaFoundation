package kz.arman.shazhafound.dao;

import kz.arman.shazhafound.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM Person ORDER BY id ASC",
                new BeanPropertyRowMapper<>(Person.class));
    }

    public Person personInfo(int id){
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public List<Person> getPersonStatus(String status){
        return jdbcTemplate.query("SELECT status FROM Person WHERE status=?",
                new BeanPropertyRowMapper<>(Person.class), status);
    }

    public void saveNewPerson(Person person){
        jdbcTemplate.update("INSERT INTO Person VALUES (1, ?, ?, 'not paid')",
                person.getName(), person.getPhoneNumber());
    }

    public void updatePerson(Person person, int id){
        jdbcTemplate.update("UPDATE Person SET name=?, phone_number=? WHERE id=?",
                person.getName(), person.getPhoneNumber(), id);
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
