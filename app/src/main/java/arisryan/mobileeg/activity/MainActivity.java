package arisryan.mobileeg.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;

import arisryan.mobileeg.R;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String GMAIL = "smtp.gmail.com";
    public static final String YAHOO = "smtp.mail.yahoo.com";
    public static final String OUTLOOK = "smtp-mail.outlook.com";
    public static final String IMAPGMAIL = "imap.gmail.com";
    public static final String IMAPYAHOO = "imap.mail.yahoo.com";
    public static final String IMAPOUTLOOK = "imap-mail.outlook.com";
    public static String email;
    public static String pass;
    public static String host;
    public static String imaphost;
    SessionManager sessionManager;
    private TextView txtprofil;
    private Button btnlogout;
    private List<Mail> mailList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MailAdapter mAdapter;

    int intMessagesLength = 5;
    ListView lvwMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FolderList fl = new FolderList();
//        ArrayList<DaftarFolder> df = fl.check(host,email,pass);
//        for (DaftarFolder daf : df){
//            String fullname = daf.fullName;
//            String foldName = daf.folderName;
//        }

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        final String name = user.get(SessionManager.kunci_email);
        email = name;
        final String password = user.get(SessionManager.kunci_password);
        final String provider = user.get(SessionManager.kunci_provider);

        if (provider.equals("Gmail") || provider.equals("gmail")) {
            host = GMAIL;
            imaphost = IMAPGMAIL;
        } else if (provider.equals("Yahoo!") || provider.equals("Yahoo") || provider.equals("yahoo") || provider.equals("yahoo!")) {
            host = YAHOO;
            imaphost = IMAPYAHOO;
        } else if (provider.equals("Outlook") || provider.equals("outlook")) {
            host = OUTLOOK;
            imaphost = IMAPOUTLOOK;
        }

        pass = password;
        //txtprofil.setText(Html.fromHtml("<b>" + name +  "<br>" +password +"</b>"));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Compose.class);
                Bundle bun = new Bundle();
                bun.putString("strFrom", email);
                intent.putExtras(bun);
                startActivity(intent);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //      .setAction("Action", null).show();
            }
        });
        txtprofil = (TextView) findViewById(R.id.label);

        // Log.d("ToingLo",KeyExchange.tokenfix);

        Log.d ("Email", String.valueOf(email));
        Log.d ("Password", String.valueOf(pass));
        Log.d ("PROVIDER", String.valueOf(provider));
        Log.d ("HOST", String.valueOf(host));
       // Log.d("array", String.valueOf(CheckingMails.mailList));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //lvwMail = (ListView) findViewById(R.id.mailBoxList);

        final ListView lvwMail = (ListView) findViewById(R.id.mailBoxList);
        Log.d("sunda", email);
        Log.d("sunda2", pass);
        Log.d("sunda3", imaphost);
        try {
            String[][] result = new LoadMail().execute().get();
            lvwMail.setAdapter(new MailArrayAdapter(this, result[0], result[1], result[2], result[3], result[4], result[5]));
            lvwMail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String strID = ((TextView) view.findViewById(R.id.id)).getText().toString();
                    String strFrom = ((TextView) view.findViewById(R.id.from)).getText().toString();
                    String strSubject = ((TextView) view.findViewById(R.id.subject)).getText().toString();
                    String strContent = ((TextView) view.findViewById(R.id.conten)).getText().toString();
                    String strAttachment = ((TextView) view.findViewById(R.id.attachment)).getText().toString();
                    String strDate = ((TextView) view.findViewById(R.id.date)).getText().toString();
                    Log.d("tes", strDate);
                    Intent myIntent = new Intent(MainActivity.this, BacaEmail.class);
                    Bundle bun = new Bundle();
                   // bun.putString("strID", strID);
                    bun.putString("strFrom", strFrom);
                    bun.putString("strSubject", strSubject);
                    bun.putString("strDate", strDate);
                    bun.putString("strContent", strContent);
                    bun.putString("strAttachment", strAttachment);
                    myIntent.putExtras(bun);
                    MainActivity.this.startActivity(myIntent);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private class LoadMail extends AsyncTask<String, Void, String[][]> {

        // private String saveDir;
        @Override
        protected String[][] doInBackground(String[] params) {
            String mail_host = imaphost;// change accordingly
            String mail_storeType = "pop3";
            final String mail_username = email;// change accordingly
            final String mail_password = pass;// change accordingly

            String result[][] = new String[6][9999];

            // do above Server call here
            try {
                //create properties field
                Properties properties = new Properties();

                properties.put("mail.imap.host", mail_host);
                properties.put("mail.imap.port", "995");
                properties.put("mail.imap.starttls.enable", "true");
                properties.put("mail.imap.starttls.enable", "true");

                Session emailSession = Session.getInstance(properties,
                        new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(mail_username, mail_password);
                            }
                        });
                emailSession.setDebug(true);

                Store store = emailSession.getStore("imaps");

                store.connect(mail_host, mail_username, mail_password);
                MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
                mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
                mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
                mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
                mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
                mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
                CommandMap.setDefaultCommandMap(mc);

                //create the folder object and open it
                Folder emailFolder = store.getFolder("[Gmail]/Sent Mail");
                emailFolder.open(Folder.READ_ONLY);

                // retrieve the messages from the folder in an array and print it
                Message[] messages = emailFolder.getMessages();

                String[] ID = new String[messages.length];
                String[] from = new String[messages.length];
                String[] subject = new String[messages.length];
                String[] content = new String[messages.length];
                String[] attachment = new String[messages.length];
                String[] date = new String[messages.length];
                result = new String [6][messages.length];

                System.out.println("messages.length---" + messages.length);

                /*for (int i = 0, n = messages.length; i < n; i++) {
                    Message message = messages[i];
                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("Text: " + message.getContent());
                    Object messagecontentObject = message.getContent();

                    if (messagecontentObject instanceof Multipart) {
                        Multipart parts = (Multipart) message.getContent();

                        Part part = null;

                        part = parts.getBodyPart(1);
                        System.out.println("Text: " + part.getContent());
                        content [i] = part.getContent().toString();
                    }else{
                        content[i] = message.getContent().toString();
                    }*/
                for (int i = 0; i < 15; i++) {
                    Message message = messages[i];
                    /*int ID = message.getMessageNumber();
                    Address[] fromAddress = message.getFrom();
                    String from = fromAddress[0].toString();
                    String subject = message.getSubject();
                    String sentDate = message.getSentDate().toString();*/

                    String contentType = message.getContentType();

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

                                //part.saveFile(saveDir + File.separator + fileName);
                            } else {
                                // this part may be the message content

                                content[i] = part.getContent().toString();
                                Log.d("BBBBB",contentType);
                            }
                        }

                        if (attachFiles.length() > 1) {
                            attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                        }
                    } else if (contentType.contains("text/plain")
                            || contentType.contains("text/html")) {
                        Object conten = message.getContent();
                        if (conten != null) {
                            content[i] = conten.toString();
                            Log.d("sunda",contentType);
                        }
                    }


                    // print out details of each message
                   /* System.out.println("Message #" + (i + 1) + ":");
                    System.out.println("\t From: " + from);
                    System.out.println("\t Subject: " + subject);
                    System.out.println("\t Sent Date: " + sentDate);
                    System.out.println("\t Message: " + messageContent);
                    System.out.println("\t Attachments: " + attachFiles);*/


                    ID[i] = String.valueOf(message.getMessageNumber());
                    Log.d("ID", ID.toString());
                    from[i] = message.getFrom()[0].toString();
                    Log.d("ID", from.toString());
                    subject[i] = message.getSubject();
                    Log.d("ID", subject.toString());
                    date[i] = message.getSentDate().toString().substring(4,10);
                    Log.d("ID", date.toString());
                    attachment[i] = attachFiles;
                }

                result[0] = ID;
                result[1] = from;
                result[2] = subject;
                result[3] = content;
                result[4] = attachment;
                result[5] = date;

                //close the store and folder objects
                emailFolder.close(false);
                store.close();

            }catch (NoSuchProviderException e) {
                Log.d("AAAAAA", "ERR1");
                e.printStackTrace();
            } catch (MessagingException e) {
                Log.d("AAAAAA", "ERR2");
                e.printStackTrace();
            }catch (Exception e) {
                Log.d("AAAAAA", "ERR3");
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String[][] result) {
            super.onPostExecute(result);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.logout) {
//            KeyExchange logout = new KeyExchange();
//            int res = logout.respons;
//
//            Log.d("Cek", String.valueOf(res));
//            //Token token = new Token();
//            //token.token= KeyExchange.tokenfix;
//            try {
//                //KeyExchange.postRequest(postUrl, datalogin);
//                logout.Logout(logout.logouturl);
//
//                //Log.d("Toing",postUrl);
//            } catch (IOException e) {
//                //Log.d("Toingerror",postUrl);
//                e.printStackTrace();
//            }
//            if (res==200){
//                sessionManager.logout();
//            }
//            //sessionManager.logout();
//        }
        if (id == R.id.action_search) {
            try {
                recreateActivityCompat(this);
            }
            catch (Exception e){
                Log.d("Error", "error");
            }
        }


        return super.onOptionsItemSelected(item);
    }
    @SuppressLint("NewApi")
    public static final void recreateActivityCompat(final Activity a) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            a.recreate();
        } else {
            final Intent intent = a.getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.finish();
            a.overridePendingTransition(0, 0);
            a.startActivity(intent);
            a.overridePendingTransition(0, 0);
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_inbox) {
        } else if (id == R.id.nav_sent) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_drafts) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_outbox) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_trash) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_profile) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_address_book) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_settings) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_help_feedback) {
            Toast.makeText(getApplicationContext(), "Menu Belum Berfungsi", Toast.LENGTH_LONG).show();

        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to logout?");
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
            });
            builder.create().show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
