package arisryan.mobileeg.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

import arisryan.mobileeg.BaseApp;
import arisryan.mobileeg.Helper.Helper;
import arisryan.mobileeg.R;

/**
 * Created by Aris Riyanto on 7/27/2017.
 */

public class ConfigMailActivity extends BaseApp {

    SessionManager sessionManager;
    private EditText logEmail;
    private EditText logPassword; //provider;
    private Spinner provider;
    private TextView loglblRegister;
    private Button btnSet;
    public String emailLogin;
    public String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setView();

        Spinner spinner = (Spinner) findViewById(R.id.logprovider);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.provider,
                android.R.layout.simple_spinner_item);
        spinner.setPrompt("Gmail");

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //sessionManager = new SessionManager(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String, String> user = sessionManager.getUserDetails();
        final String name = user.get(SessionManager.kunci_email);
        final String token = user.get(SessionManager.kunci_token);
        emailLogin = name;
        logEmail.setText(emailLogin);
        logEmail.setFocusable(false);
        //final String password = user.get(SessionManager.kunci_password);
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                SettingEmail();

            }
        });
    }
    /*
  public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

       String firstItem = String.valueOf(spinner1.getSelectedItem());

       public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
           if (firstItem.equals(String.valueOf(spinner1.getSelectedItem()))) {
               // ToDo when first item is selected
           } else {
               Toast.makeText(parent.getContext(),
                       "Kamu memilih : " + parent.getItemAtPosition(pos).toString(),
                       Toast.LENGTH_LONG).show();
           }
       }

       @Override
       public void onNothingSelected(AdapterView<?> arg) {

       }

   }*/
    private void SettingEmail() {
        logEmail.setError(null);
        logPassword.setError(null);
        /*check kebaradan teks*/
        if (Helper.isEmpty(logEmail)) {
            logEmail.setError("Email masih kosong");
            logEmail.requestFocus();
        } else if (Helper.isEmpty(logPassword)) {
            logPassword.setError("Password masih kosong");
            logPassword.requestFocus();
        } else {
            /*kirim data ke server*/
            /*
           String URL = Helper.BASE_URL + "login.php";
           Log.d("URL",URL);
           Map<String, String> param = new HashMap<>();
           param.put("email", logtxtEmail.getText().toString());
           param.put("password", logtxtPassword.getText().toString());
           */
            /*menampilkan progressbar saat mengirim data*/
            ProgressDialog pd = new ProgressDialog(context);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.setInverseBackgroundForced(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setTitle("Info");
            pd.setMessage("Login");
            pd.show();
            String mailSetting = logEmail.getText().toString();
            Log.d("Email", mailSetting);
            Log.d("Email2", emailLogin);
            Log.d("password", logPassword.getText().toString());
            if(mailSetting!=null)
            {
                sessionManager.createSession(logEmail.getText().toString(),logPassword.getText().toString(),
                        provider.getSelectedItem().toString(),token);
                startActivity(new Intent(context, MainActivity.class));
                pd.dismiss();
            }
            else
            {
                Helper.pesan(context, "Check UserName or Password");
                //pd.dismiss();
            }
           /*} catch (Exception e) {
               Helper.pesan(context, e.toString());
               pd.dismiss();
           }*/
        }
    }

    private void setView() {
        logEmail = (EditText) findViewById(R.id.logEmail);
        logPassword = (EditText) findViewById(R.id.logPassword);
        provider = (Spinner) findViewById(R.id.logprovider);
        btnSet = (Button) findViewById(R.id.logbtnSet);
        //loglblRegister = (TextView) findViewById(R.id.loglblRegister);
    }

}
