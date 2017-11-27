package arisryan.mobileeg.API;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import arisryan.mobileeg.activity.UserKey;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Aris Riyanto on 7/26/2017.
 */

public class UserKeyPairs {
    public String Url= "http://api.emailguard.id/api/UserKeyPairs/";
    //public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static UserKey EPK;

    public static void EncryptedPK(String Url, String ID) throws IOException {
        //Gson gson = new Gson();
        //String json = gson.toJson(postBody);
        OkHttpClient client = new OkHttpClient();

        //RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(Url+ID)
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
                UserKey Kampret = gson.fromJson(response.body().string(),UserKey.class);
                EPK = Kampret;
                //Token token1 = gson.fromJson(response.body().string(), Token.class);
//                String token = token1.token;
//                tokenfix = token;
            }
        });
    }

}
