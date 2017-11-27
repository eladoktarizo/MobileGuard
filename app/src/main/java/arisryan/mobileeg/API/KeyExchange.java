package arisryan.mobileeg.API;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import arisryan.mobileeg.activity.LoginUser;
import arisryan.mobileeg.activity.PubKey;
import arisryan.mobileeg.activity.Token;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aris Riyanto on 7/12/2017.
 */

public class KeyExchange  {
    public static String tokenfix;
    public static int respons;
    public static PubKey pubKey;
    public String postUrl= "https://api.emailguard.id/api/Login";
    public String logouturl = "https://api.emailguard.id/api/Logout";
    public String publicKeyUrl = "https://api.emailguard.id/api/PublicKey?email=";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void Login(String postUrl, LoginUser postBody) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(postBody);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(postUrl)
                //.addHeader("authenticationToken", tokenfix)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("rusak", e.toString());
                tokenfix = null;
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                Token token1 = gson.fromJson(response.body().string(), Token.class);
                String token = token1.token;
                tokenfix = token;
            }
        });
    }
    public static void Logout(String logouturl) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(logouturl)
                .addHeader("authenticationToken", tokenfix)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("rusak", e.toString());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                respons = response.code();

            }
        });

    }
    public static void PublicKey(String publicKeyUrl, String email) throws IOException {
        Gson gson = new Gson();
        //String json = gson.toJson(postBody);
        OkHttpClient client = new OkHttpClient();

        //RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(publicKeyUrl+email)
                .addHeader("authenticationToken", tokenfix)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("rusak", e.toString());
               // tokenfix = null;
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                PubKey publicKey = gson.fromJson(response.body().string(),PubKey.class);
                pubKey = publicKey;
                //Token token1 = gson.fromJson(response.body().string(), Token.class);
//                String token = token1.token;
//                tokenfix = token;
            }
        });
    }
}
