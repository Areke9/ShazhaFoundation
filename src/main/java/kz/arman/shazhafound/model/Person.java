package kz.arman.shazhafound.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Esimin engiziniz")
    @Size(min=2, max=20,
            message = "Adam esimi 2 zhane 20 arip arasinda boluy tiys")
    private String name;

    @NotEmpty(message = "Telefon nomerin engiziniz")
    @Pattern(regexp="(^$|[0-9]{10})",
            message = "Nomerdi durys engiziniz")
    private String phoneNumber;

    private String status;

    public Person(){}

    public Person(int id, String name, String phoneNumber, String status) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}