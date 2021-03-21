package com.fcastillo.jakarta.mail.textoplano;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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

    /**
     *
     * @param to dirección de correo de los destintarios principales
     * @param cc direcciones de correo publicas
     * @param bcc direcciones de correo privadas
     * @param asunto asunto del mensaje
     * @param remitente dirección de correo de quien envia el mensaje
     * @param mensaje cuerpo del mensaje
     * @param properties configuración de credenciales
     * @return true si el mensaje fue enviado, caso contrario false
     */
    public static boolean enviarConCopia(String[] to, String[] cc, String[] bcc, String asunto, String remitente, String mensaje, Properties properties) {

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

            // Destinatarios principales
            if (to != null) {

                InternetAddress[] addressTO = new InternetAddress[to.length];

                for (int i = 0; i < to.length; i++) {
                    addressTO[i] = new InternetAddress(to[i]);
                    message.addRecipient(Message.RecipientType.TO, addressTO[i]);
                }
            }

            // Copias publicas
            if (cc != null) {

                InternetAddress[] addressCC = new InternetAddress[cc.length];

                for (int i = 0; i < cc.length; i++) {
                    addressCC[i] = new InternetAddress(cc[i]);
                    message.addRecipient(Message.RecipientType.CC, addressCC[i]);
                }
            }

            // Copias privadas
            if (bcc != null) {

                InternetAddress[] addressBCC = new InternetAddress[bcc.length];

                for (int i = 0; i < bcc.length; i++) {
                    addressBCC[i] = new InternetAddress(bcc[i]);
                    message.addRecipient(Message.RecipientType.BCC, addressBCC[i]);
                }
            }

            message.setFrom(new InternetAddress(remitente));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);

            enviado = true;

        } catch (MessagingException e) {

            LOG.error("Ocurrió un error y no se pudo enviar el mail " + e);

        }
        return enviado;
    }

    /**
     *
     * @param archivos archivos a ser enviados como adjuntos
     * @param asunto asunto del mensaje
     * @param remitente direccion de correo del remitente
     * @param destinatario direccion de correo del destinatario
     * @param mensaje cuerpo del mensaje
     * @param properties configuraciones
     * @return true si el mensaje fue enviado, false en caso contrario
     */
    public static boolean enviarAdjuntos(String[] archivos, String asunto, String remitente, String destinatario, String mensaje, Properties properties) {

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

            // Compones la parte de texto
            MimeBodyPart bodyPartTexto = new MimeBodyPart();

            bodyPartTexto.setText(mensaje);

            Multipart multipart = new MimeMultipart();

            if (archivos != null) {

                for (String archivo : archivos) {
                    
                    // Componemos la parte de archivos
                    MimeBodyPart bodyPartArchivos = new MimeBodyPart();

                    bodyPartArchivos.attachFile(archivo);

                    multipart.addBodyPart(bodyPartArchivos);
                }

            }

            multipart.addBodyPart(bodyPartTexto);

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setContent(multipart);

            Transport.send(message);

            enviado = true;

        } catch (Exception e) {

            LOG.error("Ocurrió un error y no pudo enviarse el mensaje " + e);

        }

        return enviado;

    }

}
