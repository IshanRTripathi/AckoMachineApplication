package com.ackomachine.ackomachineapplication.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Notification {
    String phoneNumber;
    String message;
}
