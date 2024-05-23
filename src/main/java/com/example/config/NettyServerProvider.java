package com.example.config;

import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.MalformedURLException;
import java.net.URI;

public class NettyServerProvider {

    private static final String BASE_URL = "http://localhost/";
    private static final int PORT = 8080;

    public static String startHttpServer(final Class<?>... classes)
            throws MalformedURLException {
        final ResourceConfig rc = new ResourceConfig(classes);
        URI baseUri = UriBuilder.fromUri(BASE_URL).port(PORT).build();
        NettyHttpContainerProvider.createServer(baseUri, rc, false);
        return String.format("App running on %s%n",
                baseUri.toURL().toExternalForm());
    }

}
