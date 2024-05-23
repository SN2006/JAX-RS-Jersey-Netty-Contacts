package com.example.config;

import com.example.domain.Contact;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class HibernateUtil {

    private static final Logger LOGGER = Logger.getLogger(HibernateUtil.class.getName());

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try{
                Configuration configuration = getConfiguration();
                configuration.addAnnotatedClass(Contact.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }catch (Exception e){
                LOGGER.warning(e.getMessage());
            }
        }
        return sessionFactory;
    }

    private static Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        Properties props = new Properties();
        try {
            props.load(HibernateUtil.class.getResourceAsStream("/db/db.properties"));
            props.put(Environment.JAKARTA_JDBC_DRIVER, props.getProperty("dbDriver"));
            props.put(Environment.JAKARTA_JDBC_URL, props.getProperty("dbUrl"));
            props.put(Environment.JAKARTA_JDBC_USER, props.getProperty("dbUser"));
            props.put(Environment.JAKARTA_JDBC_PASSWORD, props.getProperty("dbPass"));
            props.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.setProperties(props);
            return configuration;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
