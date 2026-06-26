package com.portfolioafam.service;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailService {

    private final String smtpHost;
    private final int smtpPort;
    private final String username;
    private final String password;
    private final String fromAddress;

    public EmailService(String smtpHost, int smtpPort, String username, String password, String fromAddress) {
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
        this.username = username;
        this.password = password;
        this.fromAddress = fromAddress;
    }

    public void inviaOTP(String destinatario, String otp) throws MessagingException {
        String oggetto = "Portfolio AFAM - Codice di verifica OTP";
        String corpo = "Il tuo codice OTP è: " + otp + "\n\n"
                     + "Questo codice è valido per 60 secondi. Non condividerlo con nessuno.";

        inviaEmail(destinatario, oggetto, corpo);
    }

    public void inviaLinkCondivisione(String destinatario, String link, String messaggio) throws MessagingException {
        String oggetto = "Portfolio AFAM - Link di condivisione";
        String corpo = "Un portfolio è stato condiviso con te.\n\n"
                     + "Link: " + link + "\n\n"
                     + (messaggio != null && !messaggio.isEmpty() ? "Messaggio: " + messaggio + "\n\n" : "")
                     + "Clicca sul link per visualizzare i contenuti condivisi.";

        inviaEmail(destinatario, oggetto, corpo);
    }

    public void inviaLinkRecuperoAccount(String destinatario, String link) throws MessagingException {
        String oggetto = "Portfolio AFAM - Recupero account";
        String corpo = "Hai richiesto il recupero del tuo account Portfolio AFAM.\n\n"
                     + "Link di recupero: " + link + "\n\n"
                     + "Hai 14 giorni di tempo per recuperare il tuo account prima che venga eliminato definitivamente.";

        inviaEmail(destinatario, oggetto, corpo);
    }

    private void inviaEmail(String destinatario, String oggetto, String corpo) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", String.valueOf(smtpPort));

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromAddress));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(oggetto);
        message.setText(corpo);

        Transport.send(message);
    }
}
