package com.example.bookingapp.email;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailService  {
    private final JavaMailSender emailSender;
    private final Logger log = LoggerFactory.getLogger(EmailService.class);

    public void sendConfirmationOfReservation(String to, String subject, ReservationConfirmation reservationConfirmation) {
        String text = String.format("""
                        Dear %s,

                        Thank you for your reservation of %s.
                        You will be staying from %s to %s with %d guests.
                        The total price is %s$.

                        Best regards,
                        BookingApp""",
                reservationConfirmation.tenantName(),
                reservationConfirmation.listingName(),
                reservationConfirmation.checkIn(),
                reservationConfirmation.checkOut(),
                reservationConfirmation.numberOfGuests(),
                reservationConfirmation.totalPrice());
        log.info("Sending email confirmation of reservation of '{}' to {}", reservationConfirmation.listingName(), to);
        sendSimpleMessage(to, subject, text);
    }

    private void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@bookingapp.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
