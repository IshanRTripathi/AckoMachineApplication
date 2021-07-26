package com.ackomachine.ackomachineapplication.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Developer {
    @Id
    int id;

    String name;
    int team_id;
    String phoneNumber;

    public Developer(String name, String phoneNumber, int team_id) {
        this.name= name;
        this.phoneNumber=phoneNumber;
        this.team_id=team_id;
    }
    public Developer(String name, String phoneNumber) {
        this.name= name;
        this.phoneNumber=phoneNumber;
    }
}
