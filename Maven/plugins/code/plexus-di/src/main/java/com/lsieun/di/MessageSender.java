package com.lsieun.di;

import org.codehaus.plexus.DefaultPlexusContainer;
import org.codehaus.plexus.PlexusContainer;

public class MessageSender {
    public static void main(String[] args) {
        PlexusContainer container = null;
        MessagingService msgService = null;

        try {
            container = new DefaultPlexusContainer();

            // send SMS
            msgService = (MessagingService) container.lookup(MessagingService.class, "sms");
            msgService.sendMessage("+9123456789", "Welcome to Plexus");

            // send Email
            msgService = (MessagingService) container.lookup(MessagingService.class, "email");
            msgService.sendMessage("abc@qq.com", "Welcome to Plexus");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (container != null) {
                container.dispose();
            }
        }
    }
}
