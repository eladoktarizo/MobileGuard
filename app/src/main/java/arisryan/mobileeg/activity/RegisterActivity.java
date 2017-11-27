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

import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import arisryan.mobileeg.BaseApp;
import arisryan.mobileeg.Helper.Helper;
import arisryan.mobileeg.R;

public class RegisterActivity extends BaseApp {

    private MaterialEditText regtxtEmail, regtxtPassword1, regtxtPassword2;
    private TextView reglblLogin;
    private Button regbtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupView();
        reglblLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(BtnAnimasi);
                startActivity(new Intent(context, LoginActivity.class));
            }
        });
        regbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        regtxtEmail.setError(null);
        regtxtPassword1.setError(null);
        regtxtPassword2.setError(null);
        /*check keberadaan teks*/
        if (Helper.isEmpty(regtxtEmail)) {
            regtxtEmail.setError("Email masih kosong");
            regtxtEmail.requestFocus();
        } else if (!Helper.isEmailValid(regtxtEmail)) {
            regtxtEmail.setError("Format email salah");
            regtxtEmail.requestFocus();
        } else if (Helper.isEmpty(regtxtPassword1)) {
            regtxtPassword1.setError("Password masih kosong");
            regtxtPassword1.requestFocus();
        } else if (Helper.isEmpty(regtxtPassword2)) {
            regtxtPassword2.setError("Konfirmasi password masih kosong");
            regtxtPassword2.requestFocus();
            /*check kesamaan password*/
        } else if (Helper.isCompare(regtxtPassword1, regtxtPassword2)) {
            regtxtPassword2.setError("Password tidak cocok");
            regtxtPassword2.requestFocus();
        } else {
            /*kirim data ke server*/

            /*alamat url http://192.168.154.2/app_pesantren/register.php*/
            String URL = Helper.BASE_URL + "register.php";

            /*menampung nilai*/
            Map<String, String> param = new HashMap<>();
            param.put("email", regtxtEmail.getText().toString());
            param.put("password", regtxtPassword1.getText().toString());

            /*menampilkan progressbar saat mengirim data*/
            ProgressDialog pd = new ProgressDialog(context);
            pd.setIndeterminate(true);
            pd.setCancelable(false);
            pd.setInverseBackgroundForced(false);
            pd.setCanceledOnTouchOutside(false);
            pd.setTitle("Info");
            pd.setMessage("Sedang menambah data");
            pd.show();

            try {
                /*format ambil data*/
                aQuery.progress(pd).ajax(URL, param, String.class, new AjaxCallback<String>() {
                    @Override
                    public void callback(String url, String object, AjaxStatus status) {
                        if (object != null)
                            Log.e("Hasil",result);
                        Log.e("Obj",String.valueOf(object));{
                            try {
                                Log.i("tagconvertstr", "["+result+"]");
                                JSONObject jsonObject = new JSONObject(object);
                                String result = jsonObject.get("result").toString();
                                String msg = jsonObject.get("msg").toString();
                                Log.d("result",jsonObject.get("result").toString());
                                if (result.equalsIgnoreCase("true")) {
                                    startActivity(new Intent(context, LoginActivity.class));
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
            } catch (Exception e) {
                Helper.pesan(context, "Gagal mengambil data");
            }
        }
    }

    /*pengenalan objek*/
    private void setupView() {
        regtxtEmail = (MaterialEditText) findViewById(R.id.regtxtEmail);
        regtxtPassword1 = (MaterialEditText) findViewById(R.id.regtxtPassword1);
        regtxtPassword2 = (MaterialEditText) findViewById(R.id.regtxtPassword2);
        regbtnRegister = (Button) findViewById(R.id.regbtnRegister);
        reglblLogin = (TextView) findViewById(R.id.reglblLogin);
    }

}