//without attachment

package arisryan.mobileeg.activity;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import arisryan.mobileeg.R;

public class Compose extends AppCompatActivity implements View.OnClickListener {


    String email, subject, message, attachmentFile;
    Uri URI = null;
    private static final int PICK_FROM_GALLERY = 101;
    int columnIndex;

    //Declaring EditText
    private EditText editTextFrom;
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private String strFrom;
    // TextView txtp;
    //private String strEK;

    //Send button
    //private Button buttonSend, btnAttachment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_compose);
        setSupportActionBar(toolbar);
        if (getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Tulis Email");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Bundle b = getIntent().getExtras();
        strFrom = b.getString("strFrom");
        //Initializing the views
        editTextFrom = (EditText) findViewById(R.id.txt_compose_from);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);
        //txtp = (TextView) findViewById(R.id.tes);
        editTextFrom.setText(strFrom);

        //btnAttachment = (Button) findViewById(R.id.btnAttachment);
        //buttonSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener

        //buttonSend.setOnClickListener(this);
        //btnAttachment.setOnClickListener(this);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
                /**
                 * Get Path
                 */
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                attachmentFile = cursor.getString(columnIndex);
                //Log.e("Attachment Path:", attachmentFile);
                URI = Uri.parse(attachmentFile);
                //Log.d("Path:",String.valueOf(URI));
                cursor.close();
            }
        }catch (Exception e){

        }
    }

    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();

        String message1 = editTextMessage.getText().toString();
        String attachment = String.valueOf(URI);

        Symmetric sym = new Symmetric();
        String key = sym.GenerateKeyString();
        //byte[] nonce = sym.GenerateNonce();

        String mes = sym.Encrypt(key, message1);
        String message = mes;
        Log.d("key", key);
        Log.d("Pesan", mes);

        /*
        Asymmetric asym = new Asymmetric();
        KeyPair key = asym.GenerateKeyPair();
        KeyPair key1 = asym.GenerateKeyPair();
        //byte[] nonce = sym.GenerateNonce();

        String mes = asym.Encrypts(message1, key.publickey, key1.privatekey);
        String message = asym.Decrypt(mes, key1.publickey, key.privatekey);;
        Log.d("key1public", Base64.encodeToString(key1.publickey,Base64.DEFAULT));
        Log.d("keyprivate", Base64.encodeToString(key.privatekey,Base64.DEFAULT));
        Log.d("Pesan", mes);
          */


        //Creating SendMail object

            SendMail sm = new SendMail(this, email, subject, message, attachment);

        //Executing sendmail to send email
        sm.execute();
    }
 /*   private void kampret(){
        KeyPair key = new KeyPair();
        String publickey = key.getPublicKey().toString();
        // Log.d("A", publickey);
        String privatekey = key.getPrivateKey().toString();
        // Log.d("B", privatekey);
        Asymmetric asym = new Asymmetric();
        String chipertext = asym.Encrypts(publickey, privatekey, "coba cumi");
    }*/


    @Override
    public void onClick(View v) {

//        if (v == btnAttachment) {
//            UserKeyPairs ek = new UserKeyPairs();
//            UserKey en = ek.EPK;
//            try {
//                //KeyExchange.postRequest(postUrl, datalogin);
//                ek.EncryptedPK(ek.Url,strEK);
//                Log.d("Cek Key",strEK);
//                if(en != null){
//                    txtp.setText(en.EncryptedPK);
//                }
//                //Log.d("Toing",postUrl);
//            } catch (IOException e) {
//                //Log.d("Toingerror",postUrl);
//                e.printStackTrace();
//            }
//            openGallery();

//        }
//        if (v == buttonSend) {
//            sendEmail();
            //kampret();
//        }

    }
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                PICK_FROM_GALLERY);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // TODO: method untuk item menu toolbar compose, lalu ganti nilai return -> true
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.Attachment:
                openGallery();
                return true;
            case R.id.Send:
                sendEmail();
                return true;
            case R.id.compose_menu:
//                openComposeSettings();
                return false;
            default:
                return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.compose, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
