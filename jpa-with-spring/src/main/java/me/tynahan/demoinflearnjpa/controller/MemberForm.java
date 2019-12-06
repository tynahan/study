package me.tynahan.demoinflearnjpa.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@Getter @Setter @ToString
public class MemberForm {

    @NotEmpty(message = "name is mandatory")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
