package arisryan.mobileeg.activity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aris Riyanto on 7/26/2017.
 */

public class PubKey
{
    @SerializedName("UserId")
    public String User;

    @SerializedName("KeyId")
    public String Key;

    @SerializedName("publicKey")
    public String pubKey;

}
