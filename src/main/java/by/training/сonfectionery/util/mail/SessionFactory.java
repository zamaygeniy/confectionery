package by.training.сonfectionery.util.mail;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

class SessionFactory {

    private static final SessionFactory instance = new SessionFactory();
    private static final String MAIL_USER_NAME = "mail.user.name";
    private static final String MAIL_USER_PASSWORD = "mail.user.password";
    private SessionFactory() {
    }

    public static SessionFactory getInstance() {
        return instance;
    }

    public Session createSession(Properties properties) {
        String userName = properties.getProperty(MAIL_USER_NAME);
        String userPassword = properties.getProperty(MAIL_USER_PASSWORD);
        return Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, userPassword);
            }
        });

    }
}
