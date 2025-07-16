package model.entities;

import model.enums.ClientGender;

import java.time.LocalDate;

public class Client {
    private String name;
    private String cpf;
    private ClientGender gender;
    private LocalDate birthDate;

    public Client(String name, String cpf, ClientGender gender, LocalDate birthDate) {
        this.name = name;
        this.cpf = cpf;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientGender getGender() {
        return gender;
    }

    public void setSex(ClientGender gender) {
        this.gender = gender;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
