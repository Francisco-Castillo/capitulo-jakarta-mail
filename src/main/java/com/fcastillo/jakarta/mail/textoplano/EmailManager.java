package com.fcastillo.jakarta.mail.textoplano;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author fcastillo
 */
public class EmailManager {

    private static final Logger LOG = LogManager.getLogger(EmailManager.class);

    /**
     *
     * @param asunto asunto del mensaje
     * @param remitente dirección de correo de quien envia el mensaje
     * @param destinatario dirección de correo del destinatario
     * @param mensaje cuerpo del mensaje
     * @param properties configuración de credenciales
     * @return true si el mensaje fue enviado, false en caso contrario
     */
    public static boolean enviarTextoPlano(String asunto, String remitente, String destinatario, String mensaje, Properties properties) {
        
        boolean enviado = false;

        try {

            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            properties.getProperty("mail.smtp.user"),
                            properties.getProperty("mail.smtp.password")
                    );
                }
            });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);

            enviado = true;
            
        } catch (MessagingException e) {
            
            LOG.error("Ocurrió un error y no pudo enviarse el mail." + e);
        }

        return enviado;
    }
}
