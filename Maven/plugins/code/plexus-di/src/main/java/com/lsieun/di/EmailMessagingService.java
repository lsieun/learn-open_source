package com.lsieun.di;

public class EmailMessagingService implements MessagingService {

    @Override
    public void sendMessage(String recipient, String message) {
        System.out.println("Email sent to: " + recipient);
    }
}
