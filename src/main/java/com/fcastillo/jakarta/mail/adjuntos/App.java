package com.fcastillo.jakarta.mail.adjuntos;

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

        String remitente = "remitente@gmail.com";
        String destinatario = "destinatario@gmail.com";
        String asunto = "Archivos adjuntos";

        StringBuilder mensaje = new StringBuilder();

        mensaje.append("Hola\n\n")
                .append("este es un mensaje de prueba en el que se envian archivos adjuntos, enviado el: ")
                .append(LocalDate.now());

        String[] files = new String[2];

        files[0] = "/home/imagenes/imagen1.jpg";
        files[1] = "/home/imagenes/imagen2.jpg";

        boolean enviado = EmailManager.enviarAdjuntos(files, asunto, remitente, destinatario, mensaje.toString(), properties);

        if (enviado) {
            LOG.info("Mensaje enviado exitosamente");
        } else {
            LOG.info("Fallo envio de mail");
        }
    }
}
