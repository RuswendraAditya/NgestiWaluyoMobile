package ngestiwaluyo.com.ngestiwaluyomobile.main.main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import java.io.IOException;
import java.util.ArrayList;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.AntrianFarmasiActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.AntrianKlinikActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.info.JamBesukActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.info.KlinikDescActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.login.LoginActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.main.adapter.MenuAdapter;
import ngestiwaluyo.com.ngestiwaluyomobile.main.main.model.Menu;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.PumOrderActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.RegistrationActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.RegistrationHistoryActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.room.EmptyRoomActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.schedule.ScheduleActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.ApkUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.AutoFitGridLayoutManager;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DateUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DialogAlert;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.NetworkStatus;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainMenuActivity extends AppCompatActivity implements MenuAdapter.ItemListener {

    @BindView(R.id.rvMenu)
    RecyclerView recyclerView;
    ArrayList<Menu> arrayList;
    private NetworkStatus networkStatus;
    @BindView(R.id.txtVersionMainMenu)
    TextView txtVersionMainMenu;
    ApkUtil apkUtil;
    Integer width;
    @BindView(R.id.txtLogout)
    TextView txtLogout;
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);
        width = 0;
        networkStatus = new NetworkStatus();
        arrayList = new ArrayList<>();
        apkUtil = new ApkUtil();
        txtVersionMainMenu.setText("Version: " + apkUtil.getAppVersion(MainMenuActivity.this));
        //txtVersionMainMenu.setText(String.valueOf(apkUtil.getAppVersionCode(MainMenuActivity.this)));
        initMenu(arrayList);
        MenuAdapter adapter = new MenuAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);
        width = getWidth();
        AutoFitGridLayoutManager layoutManager = null;
        if (width < 800) {
            layoutManager = new AutoFitGridLayoutManager(this, 300);
        }
        if (width >= 800) {
            layoutManager = new AutoFitGridLayoutManager(this, 400);
        }
        recyclerView.setLayoutManager(layoutManager);
        if (SharedData.getKey(MainMenuActivity.this, "currentDate") != null) {
            String date_now = SharedData.getKey(MainMenuActivity.this, "currentDate");
        } else {
            try {
                DateUtil.getCurrentDateFromServer(MainMenuActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (SharedData.getKey(MainMenuActivity.this, "noRM") == "")
        {
            txtLogout.setVisibility(View.INVISIBLE);
        }
        else
        {
            txtLogout.setVisibility(View.VISIBLE);
        }
        if (networkStatus.isOnline(MainMenuActivity.this)) {
            new CheckVersionTask().execute();
        }


    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainMenuActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



    private void initMenu(ArrayList<Menu> arrayList) {
        arrayList.add(new Menu(1, "PENDAFTARAN ONLINE", R.drawable.registration));
        arrayList.add(new Menu(2, "RIWAYAT PENDAFTARAN ONLINE", R.drawable.stetoskop));
        arrayList.add(new Menu(3, "JADWAL DOKTER", R.drawable.doctor));
        arrayList.add(new Menu(4, "INFORMASI KAMAR", R.drawable.bed));
        // arrayList.add(new Menu(5, "AMBULANCE ORDER", R.drawable.ambulance));
       // arrayList.add(new Menu(6, "INFO ANTRIAN DOKTER", R.drawable.antri));
       // arrayList.add(new Menu(7, "TRACKING RESEP FARMASI", R.drawable.farmasi));

        arrayList.add(new Menu(9, "INFO JAM BESUK", R.drawable.clock));
       // arrayList.add(new Menu(10, "DESKRIPSI KLINIK", R.drawable.info_klinik));
    }

    private Integer getWidth() {
        Integer lebar;
        try {
            Display disp = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            disp.getSize(size);
            lebar = size.x;
        } catch (Exception e) {
            lebar = 0;
        }
        return lebar;
    }

    private void launchPlayStore()
    {
        String playStoreMarketUrl = "market://details?id=";
        String playStoreWebUrl = "https://play.google.com/store/apps/details?id=";
        String packageName = getApplicationContext().getPackageName();
        try {
            Intent intent =  getApplicationContext()
                    .getPackageManager()
                    .getLaunchIntentForPackage("com.android.vending");
            if (intent != null) {
                ComponentName androidComponent = new ComponentName("com.android.vending",
                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity");
                intent.setComponent(androidComponent);
                intent.setData(Uri.parse(playStoreMarketUrl + packageName));
            } else {
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreMarketUrl + packageName));
            }
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(playStoreWebUrl + packageName));
            startActivity(intent);
        }
    }
    private  void checkVersion() throws IOException {
        ApkUtil apkUtil  = new ApkUtil();
        int versionCodeLokal = apkUtil.getAppVersionCode(MainMenuActivity.this);
        int versionCodePlayStore = ApkUtil.getVersionPlayStore(MainMenuActivity.this);
        if(versionCodePlayStore > versionCodeLokal)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new MaterialStyledDialog.Builder(MainMenuActivity.this)
                            .setTitle("Update")
                            .setCancelable(Boolean.FALSE)
                            .setDescription("Silahkan update aplikasi anda ke versi terbaru di Playstore")
                            .setNegativeText("Update")
                            //.setPositiveText("Lanjut")
                            .setIcon(R.drawable.logo_rsnw)
                            .withIconAnimation(false)
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    launchPlayStore();
                                    finishAffinity();
                                    System.exit(0);
                                }
                            })
                            /*.onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    new PrefetchData().execute();
                                }
                            })
*/                            .show();
                }
            });
        }


    }

    private class CheckVersionTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainMenuActivity.this,
                    "Please Wait",
                    "Check Version");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                checkVersion();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }



    @Override
    public void onItemClick(Menu item) {
        Intent intent;
        // menu registration
        if (item.id == 1) {
            if (SharedData.getKey(MainMenuActivity.this, "noRM") == "") {
                SharedData.setKey(MainMenuActivity.this, "source", "registration");
                intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(MainMenuActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }

        } else if (item.id == 2) {
            if (SharedData.getKey(MainMenuActivity.this, "noRM") == "") {
                SharedData.setKey(MainMenuActivity.this, "source", "history");
                intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                intent = new Intent(MainMenuActivity.this, RegistrationHistoryActivity.class);
                startActivity(intent);
            }


        } else if (item.id == 3) {
            if (networkStatus.isOnline(MainMenuActivity.this)) {
                intent = new Intent(MainMenuActivity.this, ScheduleActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainMenuActivity.this, "Koneksi Internet Tidak Ditemukan untuk mengakses menu ini", Toast.LENGTH_LONG).show();
                // finish();
            }

        } else if (item.id == 4) {
            if (networkStatus.isOnline(MainMenuActivity.this)) {
                intent = new Intent(MainMenuActivity.this, EmptyRoomActivity.class);
                //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
                startActivity(intent);
            } else {
                Toast.makeText(MainMenuActivity.this, "Koneksi Internet Tidak Ditemukan untuk mengakses menu ini", Toast.LENGTH_LONG).show();
                // finish();
            }

        } else if (item.id == 5) {
            intent = new Intent(MainMenuActivity.this, PumOrderActivity.class);
            //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
            startActivity(intent);


        }
        else if (item.id == 6) {
            intent = new Intent(MainMenuActivity.this, AntrianKlinikActivity.class);
            //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
            startActivity(intent);


        }
        else if (item.id == 7) {


            intent = new Intent(MainMenuActivity.this, AntrianFarmasiActivity.class);
            //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
            startActivity(intent);

        }
        else if (item.id == 9) {
            intent = new Intent(MainMenuActivity.this, JamBesukActivity.class);
            //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
            startActivity(intent);
        }
        else if (item.id == 10) {
            intent = new Intent(MainMenuActivity.this,KlinikDescActivity.class);
            //   intent = new Intent(MainMenuActivity.this, CalendarPicker.class);
            startActivity(intent);


        }
        else {
            DialogAlert alert = new DialogAlert();
            alert.alertValidation(MainMenuActivity.this, "Info", "Mohon Maaf,menu masih dalam tahap pengembangan");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // new GPVersionChecker.Builder(this).create();
        if (SharedData.getKey(MainMenuActivity.this, "noRM") == "")
        {
            txtLogout.setVisibility(View.INVISIBLE);
        }
        else
        {
            txtLogout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.txtLogout)
    public void logoutClick(View view)
    {
        SharedData.removeData(MainMenuActivity.this,"noRM");
        txtLogout.setVisibility(View.INVISIBLE);
    }
}
