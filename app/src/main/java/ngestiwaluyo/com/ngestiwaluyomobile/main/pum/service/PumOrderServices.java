package ngestiwaluyo.com.ngestiwaluyomobile.main.pum.service;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.model.Pum;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.model.PumCheck;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.model.PumOrderResult;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.WebServicesUtil;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PumOrderServices {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public static  PumOrderResult postOrder(Pum pum) throws JSONException, IOException {
        // HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/Pendaftaran/").newBuilder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url + "/pump/").newBuilder();
        String url = urlBuilder.build().toString();
        JSONObject newPumOrder = new JSONObject();

        newPumOrder.put("noTelepon", pum.getNoTelp());
        newPumOrder.put("Nama", pum.getNama());
        newPumOrder.put("Alamat", pum.getAlamat());
        newPumOrder.put("Lokasi_jemput",pum.getLokasi());
        newPumOrder.put("Kasus",pum.getKasus());
        newPumOrder.put("darurat",pum.getDarurat());
        newPumOrder.put("Tgl_Order",pum.getTglOrder());
        RequestBody body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"),
                newPumOrder.toString());
        Request request = new Request.Builder()
                .url(url).post(body).build();

        Response response = client.newCall(request).execute();
        String result = response.body().string();
       PumOrderResult pumOrderResult = new PumOrderResult();
        try {
            JSONObject jsonObject = new JSONObject(result);
            Log.d("pum order","masuk");
            Log.d("pum order",jsonObject.getString("noOrder"));
            if (!jsonObject.isNull("noOrder")) {

                pumOrderResult.setNoOrder(jsonObject.getString("noOrder"));
                pumOrderResult.setNoOrderLama(jsonObject.getString("noOrderlama"));
                pumOrderResult.setNoTelp(jsonObject.getString("noTelepon"));
                pumOrderResult.setNama(jsonObject.getString("nama"));
                pumOrderResult.setAlamat(jsonObject.getString("alamat"));
                pumOrderResult.setLokasi(jsonObject.getString("lokasi_jemput"));
                pumOrderResult.setKasus(jsonObject.getString("kasus"));
                pumOrderResult.setKasusLama(jsonObject.getString("kasuslama"));
                pumOrderResult.setTglOrder(jsonObject.getString("tgl_Order"));
                pumOrderResult.setResponse(jsonObject.getString("response"));
                pumOrderResult.setDeskrippsiResponse(jsonObject.getString("deskripsiresponse"));
                pumOrderResult.setSudah(jsonObject.getString("lsudah"));
                pumOrderResult.setDarurat(jsonObject.getString("darurat"));
            }
        } catch (JSONException jEx) {
            Log.d("Gagal Order", jEx.getLocalizedMessage());
        }
        return pumOrderResult;
    }


    public static PumCheck checkDuplicatePUMOrder(Pum pum) throws IOException {
        PumCheck pumCheck = new PumCheck();
        Request request = new Request.Builder().url(url + "/RegPumTgl/?cNoTelepon=" + pum.getNoTelp()+"&dTanggal="+pum.getTglOrder()).build();
        Response response = client.newCall(request).execute();
        String result = response.body().string();
        try {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getBoolean("lsudah"))
                {
                    pumCheck.setNoOrder(jsonObject.getString("noOrder"));
                    pumCheck.setNama(jsonObject.getString("nama"));
                    pumCheck.setDeskripsiResponse(jsonObject.getString("deskripsiresponse"));
                    pumCheck.setlSudah(jsonObject.getBoolean("lsudah"));
                }
                else
                {
                    pumCheck.setNoOrder("");
                    pumCheck.setNama("");
                    pumCheck.setDeskripsiResponse("");
                    pumCheck.setlSudah(jsonObject.getBoolean("lsudah"));
                }



        } catch (JSONException jEx) {
            Log.d("Check duplicate Error", jEx.getLocalizedMessage());
        }

        return pumCheck;
    }

}
