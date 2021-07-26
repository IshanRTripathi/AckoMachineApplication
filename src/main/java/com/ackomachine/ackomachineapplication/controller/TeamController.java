package com.ackomachine.ackomachineapplication.controller;

import com.ackomachine.ackomachineapplication.entity.Team;
import com.ackomachine.ackomachineapplication.request.Request;
import com.ackomachine.ackomachineapplication.service.AlertService;
import com.ackomachine.ackomachineapplication.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
@Slf4j
public class TeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    AlertService alertService;

    //create the corresponding entries in the database
    @RequestMapping(value = "/createMapping", method = RequestMethod.POST)
    public ResponseEntity<?> createMapping(@RequestBody Request request){
        log.info("creating Mapping");

        return teamService.createTeamFromRequest(request);
    }

    @RequestMapping(value = "/alert/{teamId}", method = RequestMethod.GET)
    public void alert(@PathVariable("teamId") int teamId){
        alertService.alert(teamId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> createTeam(@RequestBody Team team){
        return teamService.createTeam(team);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeams(){
        return teamService.getAllTeams();
    }
}
