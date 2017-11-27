package arisryan.mobileeg.activity;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.URLName;

/**
 * Created by Aris Riyanto on 8/5/2017.
 */

public class FolderList {

    public static ArrayList<DaftarFolder> check(String host, String user, String password) {
        try {

            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            URLName url = store.getURLName();

            //System.out.println("toing" + url);
            //Log.d("cumi", String.valueOf(url));
            store.connect(host, user, password);
            //System.out.println(store);

            Folder[] f = store.getDefaultFolder().list("*");
            ArrayList<DaftarFolder> folds = new ArrayList<DaftarFolder>();
            DaftarFolder fold = new DaftarFolder();

            for (Folder fd : f) {
                System.out.println(">> " + fd.getFullName());
                fold.folderName = fd.getName().toString();
                fold.fullName = fd.getFullName().toString();
                folds.add(fold);
            }

            /*for (DaftarFolder ff : folds)
            {
                System.out.println("> " + ff.folderName);
                    System.out.println("> " + ff.fullName);
            }*/

            return folds;
        } catch (NoSuchProviderException e1) {
            e1.printStackTrace();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
        return null;
    }
}