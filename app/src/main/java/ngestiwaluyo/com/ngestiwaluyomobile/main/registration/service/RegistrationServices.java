package ngestiwaluyo.com.ngestiwaluyomobile.main.registration.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.model.InfoDatangDokter;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.model.Registration;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.model.RegistrationResult;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DateUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.WebServicesUtil;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Wendra on 10/17/2017.
 */

public class RegistrationServices {
    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    /*public static RegistrationResult postRegistration(Registration registration) throws JSONException, IOException {
        // HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/Pendaftaran/").newBuilder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/pendaftarantgl/").newBuilder();
        String url = urlBuilder.build().toString();
        JSONObject newRegistration = new JSONObject();
        newRegistration.put("norm", registration.getNoRM());
        newRegistration.put("kodeklinik", registration.getKodeKlinik());
        newRegistration.put("kodedokter", registration.getKodeDokter());
        newRegistration.put("tglreg",registration.getTglReg());
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                newRegistration.toString());
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        RegistrationResult registrationResult = new RegistrationResult();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("Norm")) {
                Log.d("Regis", jsonObject.getString("Norm"));
                String tgl_reg = DateUtil.changeFormatDate(jsonObject.getString("tglreg"),"dd/MM/yyyy","dd/MM/yyyy");
                registrationResult.setTglReg(tgl_reg);
                registrationResult.setJamReg(jsonObject.getString("jamReg"));
                registrationResult.setNoRm(jsonObject.getString("Norm"));
                registrationResult.setNamaPasien(jsonObject.getString("namapasien"));
                registrationResult.setNoRegj(jsonObject.getString("noregj"));
                registrationResult.setKodeKlinik(jsonObject.getString("kodeklinik"));
                registrationResult.setNamaKlinik(jsonObject.getString("namaklinik"));
                registrationResult.setKodeDokter(jsonObject.getString("kodedokter"));
                registrationResult.setNamaDokter(jsonObject.getString("namadokter"));
                registrationResult.setNoUrutklinik(jsonObject.getString("noUrutklinik"));
                registrationResult.setNoUrutdokter(jsonObject.getString("noUrutdokter"));
                registrationResult.setDeskripsiResponse(jsonObject.getString("deskripsiresponse"));
                registrationResult.setResponse(jsonObject.getString("response"));
            }
        } catch (JSONException jEx) {
            Log.d("Regis Error", jEx.getLocalizedMessage());
        }
        return registrationResult;
    }*/


    public static RegistrationResult postRegistration(Registration registration) throws JSONException, IOException {
        // HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/Pendaftaran/").newBuilder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/pendaftarantgl/").newBuilder();
        String url = urlBuilder.build().toString();
        JSONObject newRegistration = new JSONObject();
        newRegistration.put("norm", registration.getNoRM());
        newRegistration.put("kodeklinik", registration.getKodeKlinik());
        newRegistration.put("kodedokter", registration.getKodeDokter());
        newRegistration.put("tglreg",registration.getTglReg());
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                newRegistration.toString());
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
        RegistrationResult registrationResult = new RegistrationResult();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("Norm")) {
                Log.d("Regis", jsonObject.getString("Norm"));
                String tgl_reg = DateUtil.changeFormatDate(jsonObject.getString("tglreg"),"dd/MM/yyyy","dd/MM/yyyy");
                registrationResult.setTglReg(tgl_reg);
                registrationResult.setJamReg(jsonObject.getString("jamReg"));
                registrationResult.setNoRm(jsonObject.getString("Norm"));
                registrationResult.setNamaPasien(jsonObject.getString("namapasien"));
                registrationResult.setNoRegj(jsonObject.getString("noregj"));
                registrationResult.setKodeKlinik(jsonObject.getString("kodeklinik"));
                registrationResult.setNamaKlinik(jsonObject.getString("namaklinik"));
                registrationResult.setKodeDokter(jsonObject.getString("kodedokter"));
                registrationResult.setNamaDokter(jsonObject.getString("namadokter"));
                registrationResult.setNoUrutklinik(jsonObject.getString("noUrutklinik"));
                registrationResult.setNoUrutdokter(jsonObject.getString("noUrutdokter"));
                registrationResult.setDeskripsiResponse(jsonObject.getString("deskripsiresponse"));
                registrationResult.setResponse(jsonObject.getString("response"));
            }
        } catch (JSONException jEx) {
            Log.d("Regis Error", jEx.getLocalizedMessage());
        }
        return registrationResult;
    }

    public static InfoDatangDokter getInfoJamDatang(String nid,String klinik,String tglPeriksa,String noUrut) throws JSONException, IOException {
        InfoDatangDokter infoDatangDokter = new InfoDatangDokter();
        Request request = new Request.Builder().url(url + "/GetInfoJamDatangPasien/?cNid=" +nid+"&cklinik="
                                                    +klinik+"&dTglPeriksa="+tglPeriksa+"&nNoUrut="+noUrut).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (!jsonObject.isNull("response")) {
                infoDatangDokter.setJamStart(jsonObject.getString("JamMulaipraktek"));
                infoDatangDokter.setJamDatang(jsonObject.getString("JamPasienDatang"));
                infoDatangDokter.setLamaPemerikasaan(jsonObject.getString("LamaPemeriksaanpasienDalamMenit"));
                infoDatangDokter.setNid(jsonObject.getString("NID"));
                infoDatangDokter.setNamaDokter(jsonObject.getString("NamaDokter"));
                infoDatangDokter.setDescResponse(jsonObject.getString("deskripsiresponse"));
                infoDatangDokter.setHari(jsonObject.getString("hari_ini"));
                infoDatangDokter.setKodeHari(jsonObject.getString("kodehari"));
                infoDatangDokter.setNoUrut(jsonObject.getString("nNoUrutPasien"));
                infoDatangDokter.setResponse(jsonObject.getString("response"));
                infoDatangDokter.setTanggal(jsonObject.getString("tanggal"));
                infoDatangDokter.setKodeKlinik(klinik);
            }
        } catch (JSONException jEx) {
            Log.d("InfO datang dokter", jEx.getLocalizedMessage());
        }

        return infoDatangDokter;
    }
}
