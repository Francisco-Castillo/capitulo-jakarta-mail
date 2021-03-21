package com.fcastillo.jakarta.mail.copias;

import com.fcastillo.jakarta.mail.textoplano.*;
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

        String asunto = "Mensaje de prueba";
        String remitente = "remitente@gmail.com";

        StringBuilder mensaje = new StringBuilder();

        mensaje.append("Hola\n\n")
                .append("este es un mensaje de prueba, enviado el: ")
                .append(LocalDate.now());

        // Destinatarios principales
        String[] addressTO = new String[1];

        addressTO[0] = "direccion@gmail.com";

        // Copias publicas - CC
        String[] addressCC = new String[1];

        addressCC[0] = "direccion@hotmail.com";

        // Copias ocultas - BCC
        String[] addressBCC = new String[2];

        addressBCC[0] = "direccion1@gmail.com";
        addressBCC[1] = "direccion2@yahoo.com";

        boolean enviado = EmailManager.enviarConCopia(addressTO, addressCC, addressBCC, asunto, remitente, mensaje.toString(), properties);

        if (enviado) {
            LOG.info("Mensaje enviado exitosamente");
        } else {
            LOG.info("Fallo envio de mail");
        }
    }
}
