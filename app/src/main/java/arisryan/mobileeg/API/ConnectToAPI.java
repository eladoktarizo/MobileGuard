package arisryan.mobileeg.API;

import android.renderscript.ScriptGroup;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Aris Riyanto on 7/12/2017.
 */

public class ConnectToAPI {
    private static final String BASE_URL = "http://api.emailguard.id/";
    /*
    public static HttpURLConnection ConnectionGet(String root, String child)
    {

        try {
            URL url = new URL(root + child);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            return conn;

        }catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    public static HttpURLConnection ConnectionGet(String root, String child, String token)
    {

        try {
            URL url = new URL(root + child);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("authenticationToken", token);
            return conn;

        }catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    public static HttpURLConnection ConnectionPost (String root, String child, String Input){
        try {
            URL url = new URL(root + child);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            //return conn;

            OutputStream outputPost = new BufferedOutputStream(conn.getOutputStream());
            outputPost.write(Input.getBytes());
            //writeStream(outputPost);
            outputPost.flush();
            outputPost.close();

        }
        catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }

    public static HttpURLConnection ConnectionPost (String root, String child, String token, String Input){
        try {
            URL url = new URL(root + child);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("authenticationToken", token);
            //return conn;
            OutputStream os = conn.getOutputStream();
            os.write(Input.getBytes());
            os.flush();
            return conn;
        }
        catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
        return null;
    }
    */

}
