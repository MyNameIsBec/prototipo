package com.portfolioafam.gestionecondivisioneesterna;
import com.portfolioafam.service.EmailService;
import jakarta.mail.MessagingException;
public class CondividiLinkCTRL {
    private final EmailService emailService;
    public CondividiLinkCTRL(EmailService es) { this.emailService = es; }
    public void inviaLink(String destinatario, String link, String messaggio) throws MessagingException {
        emailService.inviaLinkCondivisione(destinatario, link, messaggio);
    }
}
