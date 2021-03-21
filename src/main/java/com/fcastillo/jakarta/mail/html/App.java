package com.fcastillo.jakarta.mail.html;

import com.fcastillo.jakarta.mail.general.EmailManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
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

        String rutaPlantilla = "/home/facsoft/plantillas/prestamo-aprobado";

        Map<String, String> parametros = new HashMap<>();

        parametros.put("{mensajebienvenida}", "Felicidades estimado cliente");

        boolean enviado = EmailManager.enviarHTML(asunto, remitente, destinatario, parametros, rutaPlantilla, properties);

        if (enviado) {
            LOG.info("Mensaje enviado exitosamente");
        } else {
            LOG.info("Fallo envio de mail");
        }
    }
}
