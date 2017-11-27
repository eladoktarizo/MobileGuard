package arisryan.mobileeg.activity;

/**
 * Created by Aris Riyanto on 5/12/2017.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import java.io.IOException;

import arisryan.mobileeg.API.KeyExchange;
import arisryan.mobileeg.BaseApp;
import arisryan.mobileeg.Helper.Helper;
import arisryan.mobileeg.R;


public class LoginActivity extends BaseApp {
    SessionManager sessionManager;
    private EditText logtxtEmail, logtxtPassword, provider;
    private TextView loglblRegister, txt_forgot;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupView();

        sessionManager = new SessionManager(getApplicationContext());
        //spinner1 = (Spinner) findViewById(R.id.spinner);
        //spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                LoginUser();

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
   private void LoginUser() {
       logtxtEmail.setError(null);
       logtxtPassword.setError(null);
        /*check kebaradan teks*/
       if (Helper.isEmpty(logtxtEmail)) {
           logtxtEmail.setError("Email masih kosong");
           logtxtEmail.requestFocus();
       } else if (Helper.isEmpty(logtxtPassword)) {
           logtxtPassword.setError("Password masih kosong");
           logtxtPassword.requestFocus();
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
           //try {
               /*
               aQuery.progress(pd).ajax(URL, param, String.class, new AjaxCallback<String>() {
                   @Override
                   public void callback(String url, String object, AjaxStatus status) {
                       if (object != null) {
                           try {
                               Log.i("tagconvertstr", "["+result+"]");
                               JSONObject JsonObjek = new JSONObject(object);
                               String result = JsonObjek.getString("result");
                               Log.d("hasil",JsonObjek.getString("result"));
                               String msg = JsonObjek.getString("msg");
                               Log.d("message",JsonObjek.getString("msg"));
                               if (result.equalsIgnoreCase("true")) {
                                   sessionManager.createSession(logtxtEmail.getText().toString(),
                                           logtxtPassword.getText().toString(),provider.getText().toString());
                                   startActivity(new Intent(context, MainActivity.class));
                                   Helper.pesan(context, msg);
                                   finish();
                               } else {
                                   Helper.pesan(context, msg);
                               }
                           } catch (JSONException e) {
                               Helper.pesan(context, "Error convert data json");
                           }
                       }
                   }
               });
               */

               KeyExchange login = new KeyExchange();
               LoginUser datalogin = new LoginUser();
               datalogin.username = logtxtEmail.getText().toString();
               datalogin.password = logtxtPassword.getText().toString();
               String token = login.tokenfix;

           try {
               //KeyExchange.postRequest(postUrl, datalogin);
               login.Login(login.postUrl, datalogin);
               //Log.d("Toing",postUrl);
           } catch (IOException e) {
               //Log.d("Toingerror",postUrl);
               e.printStackTrace();
           }

               if(token==null)

               {
                   Helper.pesan(context, "Check UserName or Password");
                   pd.dismiss();
               }
               else
               {
                   Log.d("Cek Token",token);
                   sessionManager.createSession(logtxtEmail.getText().toString(),logtxtPassword.getText().toString(),
                           "Isi",token);
                   startActivity(new Intent(context, ConfigMailActivity.class));
                   pd.dismiss();
               }
           /*} catch (Exception e) {
               Helper.pesan(context, e.toString());
               pd.dismiss();
           }*/
       }
   }

    private void setupView() {
        logtxtEmail = (EditText) findViewById(R.id.logtxtEmail);
        logtxtPassword = (EditText) findViewById(R.id.logtxtPassword);
        txt_forgot = (TextView) findViewById(R.id.txt_forgot);
       // provider = (MaterialEditText) findViewById(R.id.provider);
        btnLogin = (Button) findViewById(R.id.logbtnLogin);
        //loglblRegister = (TextView) findViewById(R.id.loglblRegister);
    }
}