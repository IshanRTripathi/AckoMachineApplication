package com.ackomachine.ackomachineapplication.service;

import com.ackomachine.ackomachineapplication.entity.Developer;
import com.ackomachine.ackomachineapplication.entity.Team;
import com.ackomachine.ackomachineapplication.exceptionHandling.IncorrectPhoneNumberException;
import com.ackomachine.ackomachineapplication.repository.DeveloperRepository;
import com.ackomachine.ackomachineapplication.repository.TeamRepository;
import com.ackomachine.ackomachineapplication.request.Request;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TeamService {

    private static final String COUNTRY_CODE = "+91";
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    DeveloperRepository developerRepository;

    public ResponseEntity<?> createTeamFromRequest(@NonNull Request request) {
        String teamName= request.getTeam();
        Optional<Team> teamOptional= teamRepository.findByName(teamName);
        if(!teamOptional.isPresent()){
            log.info("Team not present, creating new Team");
            teamRepository.save(new Team(teamName));
        }
        Team team= teamRepository.findByName(teamName).get();

        List<Developer> developers= request.getDevelopers();
        return createMapping(team, developers);

    }

    private ResponseEntity<?> createMapping(@NonNull Team team, @NonNull List<Developer> developers) {
        log.info("Mapping each dev to team");
        developers.stream().forEach(developer -> {
            List<Developer> developersInDatabase= developerRepository.findAll().stream().filter(dev->dev.getName()
                    .equals(developer.getName()) &&
                    dev.getPhoneNumber().equals(developer.getPhoneNumber())).collect(Collectors.toList());
            if(developersInDatabase.size()==0){
                try {
                    if (!developer.getPhoneNumber().startsWith(COUNTRY_CODE) || developer.getPhoneNumber().length() != 13) {
                        throw new IncorrectPhoneNumberException();
                    }
                } catch(IncorrectPhoneNumberException e) {
                    e.printStackTrace();
                }

                developerRepository.save(new Developer(developer.getName(), developer.getPhoneNumber(), team.getId()));
            }
            else if(developersInDatabase.size()>1){
                log.error("Multiple developers with same details are present !");
            }
            else{
                developersInDatabase.get(0).setTeam_id(team.getId());
            }
        }
        );
        return new ResponseEntity<>(developers, HttpStatus.OK);
    }

    public ResponseEntity<?> createTeam(@NonNull Team team) {
        Optional<Team> teamOptional= teamRepository.findByName(team.getName());
        if(teamOptional.isPresent()){
            log.info("Team already present");
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(teamRepository.save(team), HttpStatus.OK);
    }

    public ResponseEntity<?> getAllTeams() {
        return new ResponseEntity<>(teamRepository.findAll(), HttpStatus.OK);
    }
}
