package ngestiwaluyo.com.ngestiwaluyomobile.main.utility;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import androidx.core.content.pm.PackageInfoCompat;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Rs Bethesda on 11/2/2017.
 */

public class ApkUtil {

    static OkHttpClient client = WebServicesUtil.connect();
    static String url = WebServicesUtil.getServiceUrl();

    public String getAppVersion(Context context) {

        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        String myVersionName = "-"; // initialize String

        try {
            myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return myVersionName;
    }

    public Integer getAppVersionCode(Context context) {

        int versionCode=0;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            versionCode = (int) PackageInfoCompat.getLongVersionCode(info);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
    return  versionCode;
    }



    public static Integer getVersionPlayStore(Context context ) throws IOException {
        int versionPlayStore = 0;
        try{
            Request request = new Request.Builder().url(url + "/GetVersi/").build();
            Response response = client.newCall(request).execute();
            String results = response.body().string();
            versionPlayStore  = Integer.valueOf(results.substring(1,3));

        }
        catch (Exception e){
            return 0;
        }

        return versionPlayStore;
    }
}
