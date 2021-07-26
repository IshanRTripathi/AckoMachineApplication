package com.ackomachine.ackomachineapplication.entity;

import com.ackomachine.ackomachineapplication.service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TestUser {

    private TeamService teamService;
    @BeforeEach
    public void init(){
        teamService= new TeamService();
    }

    @Test
    public void testTeamCreate(){
        Team testTeam= new Team(1, "team1");
        ResponseEntity<?> responseEntity= teamService.createTeam(testTeam);
        assert(responseEntity.getStatusCode()== HttpStatus.OK);
    }
}
