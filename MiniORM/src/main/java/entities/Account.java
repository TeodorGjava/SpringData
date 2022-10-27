package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.time.LocalDate;

@Entity(name = "accounts")
public class Account {
    @Id(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_on")
    private LocalDate created_on;
    @Column(name = "age")
    private Integer age;

    public Account() {
    }

    public Account(String name, LocalDate created_on, Integer age) {
        this.name = name;
        this.created_on = created_on;
        this.age = age;
    }

    public long getId() {
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

    public LocalDate getCreated_on() {
        return created_on;
    }

    public void setCreated_on(LocalDate created_on) {
        this.created_on = created_on;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
