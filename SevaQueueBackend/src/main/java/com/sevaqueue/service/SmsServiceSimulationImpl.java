package com.sevaqueue.service;

import org.springframework.stereotype.Service;

@Service
public class SmsServiceSimulationImpl implements SmsService {

    @Override
    public void sendSms(String mobileNumber, String message) {
        System.out.println("----------------------------------------------------------------");
        System.out.println("📱 [SMS SIMULATION]");
        System.out.println("TO: " + mobileNumber);
        System.out.println("MESSAGE: " + message);
        System.out.println("----------------------------------------------------------------");
    }
}
