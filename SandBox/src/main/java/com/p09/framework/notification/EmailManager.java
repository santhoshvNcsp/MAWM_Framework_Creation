package com.p09.framework.notification;

import com.p09.framework.config.ConfigManager;
import com.p09.framework.reporting.ReportEngine;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

import java.io.File;
import java.util.Properties;

public final class EmailManager {

    private EmailManager() {
    }

    private static boolean validateConfiguration() {

        if (!ConfigManager.getBoolean("email.enabled")) {

            System.out.println("Email notification is disabled.");

            return false;

        }

        String[] required = {
                ConfigManager.get("mail.host"),
                ConfigManager.get("mail.port"),
                ConfigManager.get("mail.username"),
                ConfigManager.get("mail.password"),
                ConfigManager.get("mail.recipients")
        };

        for (String value : required) {

            if (value == null || value.trim().isEmpty()) {

                System.out.println("Email configuration is incomplete.");

                return false;

            }

        }

        return true;

    }

    private static boolean validateReport(File reportFile) {

        if (!reportFile.exists()) {

            System.out.println("Report file not found.");

            return false;

        }

        return true;

    }

    public static void sendReport() {

        if (!validateConfiguration()) {
            return;
        }

        String mailHost = ConfigManager.get("mail.host");
        String mailPort = ConfigManager.get("mail.port");
        String mailUsername = ConfigManager.get("mail.username");
        String mailPassword = ConfigManager.get("mail.password");
        String mailRecipients = ConfigManager.get("mail.recipients");
        String mailSubject = ConfigManager.get("mail.subject");

        Properties properties = new Properties();

        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.smtp.port", mailPort);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(
                                mailUsername,
                                mailPassword);

                    }
                });

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(mailUsername));

            String[] recipients = mailRecipients.split(",");

            for (String recipient : recipients) {

                message.addRecipient(
                        Message.RecipientType.TO,
                        new InternetAddress(recipient.trim()));

            }

            message.setSubject(mailSubject);

            MimeBodyPart body = new MimeBodyPart();

            body.setText(
                    "Hi Team,\n\n" +
                            "Please find the attached Automation Execution Report.\n\n" +
                            "Regards,\nMAP Automation Framework");
            MimeBodyPart attachment = new MimeBodyPart();
            System.out.println("Report Path : " + ReportEngine.getReportPath());

            File reportFile = new File(ReportEngine.getReportPath());

            System.out.println("Report Exists : " + reportFile.exists());
            System.out.println("Absolute Path : " + reportFile.getAbsolutePath());

            if (!validateReport(reportFile)) {
                return;
            }

            attachment.attachFile(reportFile);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(body);
            multipart.addBodyPart(attachment);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Report Email Sent Successfully.");

        } catch (Exception e) {

            System.out.println("----------------------------------");
            System.out.println("EMAIL NOTIFICATION");
            System.out.println("----------------------------------");
            System.out.println("Status : FAILED");
            System.out.println("Reason : " + e.getMessage());
            e.printStackTrace();
            System.out.println("----------------------------------");

        }

    }

}