package com.lsieun.di;

import com.google.inject.AbstractModule;

public class GuiceInjector extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessagingService.class).to(SMSMessagingService.class);
    }
}
