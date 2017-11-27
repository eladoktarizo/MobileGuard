package arisryan.mobileeg.API;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import arisryan.mobileeg.activity.EmailKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Aris Riyanto on 7/26/2017.
 */

public class EmailKeys {
    public String GetUrls= "http://api.emailguard.id/api/EmailKeys?mailId=";
    public String PostUrls= "http://api.emailguard.id/api/EmailKeys";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static EmailKey mailKey;

    public static void GetEmailKeys(String GetUrl, final String mailID) throws IOException {
        //Gson gson = new Gson();
        //String json = gson.toJson(postBody);
        OkHttpClient client = new OkHttpClient();

        //RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(GetUrl+mailID)
                .addHeader("authenticationToken", KeyExchange.tokenfix)
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
                EmailKey mailkey = gson.fromJson(response.body().string(),EmailKey.class);
                mailKey = mailkey;
                //Token token1 = gson.fromJson(response.body().string(), Token.class);
//                String token = token1.token;
//                tokenfix = token;
            }
        });
    }

    public static void PostEmailKeys(String PostUrl, List<EmailKey> emailkeys) throws IOException {
        Gson gson = new Gson();
        String json = gson.toJson(emailkeys);
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(PostUrl)
                .addHeader("authenticationToken", KeyExchange.tokenfix)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("rusak", e.toString());
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                KeyExchange key = new KeyExchange();
                key.respons = response.code();
            }
        });
    }
}
