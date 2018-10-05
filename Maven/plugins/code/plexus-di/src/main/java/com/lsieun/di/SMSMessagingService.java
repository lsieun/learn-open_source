package com.lsieun.di;

public class SMSMessagingService implements MessagingService {

    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("SMS sent to: " + recipient);
    }
}
