package com.lsieun.di;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceClientApplication {
    public static void main(String[] args) {
        Injector injector;
        GuiceMessageSender messageSender;

        injector = Guice.createInjector(new GuiceInjector());
        messageSender = injector.getInstance(GuiceMessageSender.class);
        messageSender.sendMessage("+9123456789", "Welcome to Guice");
    }
}
