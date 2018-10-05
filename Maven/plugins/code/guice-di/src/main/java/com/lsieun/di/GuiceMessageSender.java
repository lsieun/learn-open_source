package com.lsieun.di;

import javax.inject.Inject;

public class GuiceMessageSender {
    private MessagingService messagingService;

    @Inject
    public void setService(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public void sendMessage(String recipient, String message) {
        messagingService.sendMessage(recipient, message);
    }
}
