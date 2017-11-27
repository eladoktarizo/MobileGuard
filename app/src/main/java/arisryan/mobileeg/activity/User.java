package arisryan.mobileeg.activity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aris Riyanto on 7/26/2017.
 */

public class User {
    @SerializedName("ID")
    public String ID;

    @SerializedName("EmailAddress")
    public String EmailAddress;

    @SerializedName("EncryptedUserValidation")
    public String EncryptUserValid;

    @SerializedName("UserValidation")
    public String UserValid;

}
