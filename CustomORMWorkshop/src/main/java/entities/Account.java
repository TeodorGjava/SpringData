package entities;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.time.LocalDate;

@Entity(name = "accounts")
public class Account {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "created_on")
    private LocalDate created_on;
    @Column(name = "age")
    private Integer age;
    @Column(name = "nickname")
    private String nickname;

    public Account() {
    }

    public Account(String name, LocalDate created_on, Integer age, String nickname) {
        this.name = name;
        this.created_on = created_on;
        this.age = age;
        this.nickname = nickname;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getId() {
        return id;
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
