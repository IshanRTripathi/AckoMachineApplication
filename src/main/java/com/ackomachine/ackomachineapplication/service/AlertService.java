package com.ackomachine.ackomachineapplication.service;

import com.ackomachine.ackomachineapplication.entity.Developer;
import com.ackomachine.ackomachineapplication.exceptionHandling.NoMatchingDeveloperFoundException;
import com.ackomachine.ackomachineapplication.repository.DeveloperRepository;
import com.ackomachine.ackomachineapplication.request.Notification;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AlertService {

    @Autowired
    DeveloperRepository developerRepository;

    private final RestTemplate restTemplate= new RestTemplate();
    private static final String NOTIFICATION_MESSAGE= "Too many 5xx!";
    private static final String ALERT_ENDPOINT= "https://run.mocky.io/v3/fd99c100-f88a-4d70-aaf7-393dbbd5d99f";

    public void alert(int teamId){
        List<Developer> developers= developerRepository.findAll().stream()
                .filter(developer -> developer.getTeam_id()==teamId).collect(Collectors.toList());
        try {
            if (developers.size() == 0) {
                throw new NoMatchingDeveloperFoundException();
            }
            else{
                sendNotification(developers.get((int)(Math.random()*100)%developers.size()));
            }
        } catch (NoMatchingDeveloperFoundException e){
            e.printStackTrace();
        }
    }

    private void sendNotification(@NonNull Developer developer) {
        Notification notification= new Notification(developer.getPhoneNumber(), NOTIFICATION_MESSAGE);
        ResponseEntity<?> responseEntity= restTemplate.postForEntity(ALERT_ENDPOINT,notification, String.class);
        log.info(String.valueOf(responseEntity));
    }
}
