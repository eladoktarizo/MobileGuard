package arisryan.mobileeg.activity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aris Riyanto on 7/26/2017.
 */

public class EmailKey {
    @SerializedName("Id")
    public String Id;

    @SerializedName("EncryptedSenderSymetricKey")
    public String EncryptedSenderSymetricKey;

    @SerializedName("EncryptedRecipientSymetricKey")
    public String EncryptedRecipientSymetricKey;

    @SerializedName("SenderId")
    public String SenderId;

    @SerializedName("RecipientId")
    public String RecipientId;

    @SerializedName("SenderKeyId")
    public String SenderKeyId;

    @SerializedName("RecipientkeyId")
    public String RecipientkeyId;

    @SerializedName("EmailId")
    public String EmailId;

}
