package ngestiwaluyo.com.ngestiwaluyomobile.main.login.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.WebServicesUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.login.model.Login;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Wendra on 10/9/2017.
 */

public class LoginServices {
    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static Login getLoginByNoRmServices(String noRM) throws IOException {
        Login login = new Login();
        Request request = new Request.Builder().url(url + "/Pasien/" + noRM).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("NoRM")) {
                login.setNoRM(jsonObject.getString("NoRM"));
                Log.d("NO RM", jsonObject.getString("NoRM"));
                login.setNamaPasien(jsonObject.getString("NamaPasien"));
                login.setResponse(jsonObject.getString("response"));
                login.setDeskripsiResponse(jsonObject.getString("deskripsiresponse"));
                login.setAlamat(jsonObject.getString("Alamat"));
                login.setDtglLahir(jsonObject.getString("dtgllahir"));
            }
        } catch (JSONException jEx) {
            Log.d("Login Error", jEx.getLocalizedMessage());
        }

        return login;
    }


}
