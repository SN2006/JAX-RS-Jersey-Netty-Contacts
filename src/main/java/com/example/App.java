package com.example;

import com.example.config.NettyServerProvider;
import com.example.controller.ContactController;

import java.net.MalformedURLException;
import java.util.logging.Logger;

public class App
{

    private final static Logger LOGGER
            = Logger.getLogger(App.class.getName());

    public static void main( String[] args ) throws MalformedURLException {
        final String httpServer = NettyServerProvider.startHttpServer(
                ContactController.class
        );

        LOGGER.info(httpServer);
    }
}
