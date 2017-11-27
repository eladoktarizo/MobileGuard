package arisryan.mobileeg.activity;

import android.os.AsyncTask;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;


public class EmailReceiver extends AsyncTask<Void,Void,Void> {
    private String saveDirectory;
    private String ID;
    private String host;
    private String userName;
    private String password;

    public void setSaveDirectory(String dir) {
        this.saveDirectory = dir;
    }

    public void downloadEmailAttachments(String ID, String host,String userName, String password) {
    this.ID=ID;
    this.host=host;
    this.userName= userName;
    this.password= password;

    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            //create properties field
            Properties properties = new Properties();

            properties.put("imap.gmail.com", host);
            properties.put("mail.imap.port", "993");
            properties.put("mail.imap.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");
            MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
            mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
            mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
            mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
            mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
            mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
            CommandMap.setDefaultCommandMap(mc);
            store.connect(host, userName, password);
            // connects to the message store
            //Store store = session.getStore("imap");
            //store.connect(userName, password);

            // opens the inbox folder
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            String number = ID;
            // fetches new messages from server
            Message[] arrayMessages = folderInbox.getMessages();

            for (int i = 0; i < arrayMessages.length; i++) {
                Message message = arrayMessages[i];
                int ID = message.getMessageNumber();
                if (number.equals(String.valueOf(ID))) {
                    Address[] fromAddress = message.getFrom();
                    String from = fromAddress[0].toString();
                    String subject = message.getSubject();
                    String sentDate = message.getSentDate().toString();

                    String contentType = message.getContentType();
                    String messageContent = "";

                    // store attachment file name, separated by comma
                    String attachFiles = "";

                    if (contentType.contains("multipart")) {
                        Multipart mp = (Multipart) message.getContent();
                        int numberOfParts = mp.getCount();
                        for (int partCount = 0; partCount < numberOfParts; partCount++) {
                            MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(partCount);
                            if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                // this part is attachment
                                String fileName = part.getFileName();
                                attachFiles += fileName + ", ";
                                part.saveFile(saveDirectory + File.separator + fileName);
                            } else {
                                // this part may be the message content
                                messageContent = part.getContent().toString();
                            }
                        }

                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                        }
                    } else if (contentType.contains("text/plain")
                            || contentType.contains("text/html")) {
                        Object content = message.getContent();
                        if (content != null) {
                            messageContent = content.toString();
                        }
                    }


                    // print out details of each message
                    System.out.println("Message #" + (i + 1) + ":");
                    System.out.println("\t From: " + from);
                    System.out.println("\t Subject: " + subject);
                    System.out.println("\t Sent Date: " + sentDate);
                    System.out.println("\t Message: " + messageContent);
                    System.out.println("\t Attachments: " + attachFiles);
                    message.writeTo(System.out);
                }
            }

            // disconnect
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for pop3.");
            ex.printStackTrace();
        } catch (MessagingException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Runs this program with Gmail POP3 server
     */
    public static void main(String[] args) {
        String ID = "2";
        String host = "imap.mail.yahoo.com";
        String port = "993";
        String userName = "ujicoba160@yahoo.com";
        String password = "aris1234";

        String saveDirectory = "G:/Buku";

        EmailReceiver receiver = new EmailReceiver();
        receiver.setSaveDirectory(saveDirectory);
        receiver.downloadEmailAttachments(ID, host, userName, password);

    }


}
