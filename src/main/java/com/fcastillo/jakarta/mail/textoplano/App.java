package com.fcastillo.jakarta.mail.textoplano;

import com.fcastillo.jakarta.mail.general.EmailManager;
import java.time.LocalDate;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        
        Properties properties = new Properties();

        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", "remitente@gmail.com");
        properties.put("mail.smtp.password", "password");

        StringBuilder mensaje = new StringBuilder();

        mensaje.append("Hola\n\n")
                .append("este es un mensaje de prueba, enviado el: ")
                .append(LocalDate.now());

        boolean enviado = EmailManager.enviarTextoPlano(
                "Mensaje de prueba",
                "remitente@gmail.com",
                "destinatario@gmail.com",
                mensaje.toString(),
                properties
        );

        if (enviado) {
            LOG.info("Mensaje enviado exitosamente");
        } else {
            LOG.info("Fallo envio de mail");
        }
    }
}
