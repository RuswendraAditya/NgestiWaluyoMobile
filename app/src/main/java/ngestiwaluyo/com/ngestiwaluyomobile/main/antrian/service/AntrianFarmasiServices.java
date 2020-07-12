package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.service;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.AntrianFarmasi;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.WebServicesUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AntrianFarmasiServices {
    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static List<AntrianFarmasi> getTrackingFarmasiRajal(String noRM,String date) throws IOException {
        List<AntrianFarmasi> antrianFarmasiList = new ArrayList<>();
        Request request = new Request.Builder().url(url + "/getResepRajal/?cNoRM="+noRM+"&dTanggal="+date).build();
        Response response = client.newCall(request).execute();
        String results = response.body().string();
        try {
            JSONArray jsonArray = new JSONArray(results);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                AntrianFarmasi antrianFarmasi = new AntrianFarmasi();
                antrianFarmasi.setKodeResep(jsonObject.getString("KodeResep"));
                antrianFarmasi.setNoAntrian(jsonObject.getString("NoAntrian"));
                antrianFarmasi.setNoEpres(jsonObject.getString("NoEpres"));
                antrianFarmasi.setNoReg(jsonObject.getString("noregj"));
                antrianFarmasi.setJamEntri(jsonObject.getString("entri"));
                antrianFarmasi.setJamFilling(jsonObject.getString("filling"));
                antrianFarmasi.setJamKontrol(jsonObject.getString("kontrol"));
                antrianFarmasi.setSerah(jsonObject.getString("serah"));
                antrianFarmasi.setHari(jsonObject.getString("kontrol"));
                antrianFarmasi.setPanggil(jsonObject.getString("panggil"));
                antrianFarmasi.setReview(jsonObject.getString("review"));
                antrianFarmasi.setDokter(jsonObject.getString("Dokter"));
                antrianFarmasi.setKlinik(jsonObject.getString("Klinik"));
                antrianFarmasi.setNamaPasien(jsonObject.getString("Namapasien"));
                antrianFarmasiList.add(antrianFarmasi);
            }
        } catch (JSONException jEx) {
            Log.d("Tracking Obat Error", jEx.getLocalizedMessage());
        }
        return antrianFarmasiList;

    }
}
