package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.Antrian;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.AntrianResult;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.WebServicesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AntrianKlinikServices {
    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static AntrianResult getAntrianKlinik(Antrian antrian) throws IOException {
        AntrianResult antrianResult = new AntrianResult();
        String tgl =  antrian.getTgl();
        String klinik  = antrian.getKodeKlinik();
        String nid  = antrian.getNid();
        Request request = new Request.Builder().url(url + "/getAntrianKlinik/?dtanggal=" + tgl+"&cKlinik="+klinik+"&cNid="+nid).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
          try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("response")) {
                antrianResult.setTotalDilayani(jsonObject.getString("dilayani"));
                antrianResult.setTotalPasien(jsonObject.getString("jmlpasien"));
                antrianResult.setResponse(jsonObject.getString("response"));
            }
        } catch (JSONException jEx) {
            Log.d("Get antrian error", jEx.getLocalizedMessage());
        }

        return antrianResult;
    }
}
