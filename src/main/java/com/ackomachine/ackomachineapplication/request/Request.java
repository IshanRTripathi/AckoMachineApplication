package com.ackomachine.ackomachineapplication.request;

import com.ackomachine.ackomachineapplication.entity.Developer;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Request {
    String team;
    List<Developer> developers;
}
