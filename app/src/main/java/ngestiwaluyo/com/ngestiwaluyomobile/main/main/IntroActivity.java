package ngestiwaluyo.com.ngestiwaluyomobile.main.main;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.service.DokterServices;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.service.KlinikServices;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DatabaseHandler;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DateUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.NetworkStatus;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private DatabaseHandler db;
    private NetworkStatus networkStatus;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        progressBar.setProgress(0);
        db = new DatabaseHandler(IntroActivity.this);
        networkStatus = new NetworkStatus();
        SharedData.clearData(IntroActivity.this);
        Log.d("Online", String.valueOf(networkStatus.isOnline(IntroActivity.this)));
        if (networkStatus.isOnline(IntroActivity.this)) {
          // new CheckVersionTask().execute();
           new PrefetchData().execute();
        } else {
            // Toast.makeText(IntroActivity.this, "Koneksi Internet Tidak Ditemukan", Toast.LENGTH_LONG).show();
            //finish();
            new MaterialStyledDialog.Builder(IntroActivity.this)
                    .setTitle("Konfirmasi")
                    .setDescription("Koneksi Internet Tidak Ditemukan, Apakah Ingin Mencoba Kembali? ")
                    .setIcon(R.drawable.logo_rsnw)
                    .withIconAnimation(false)
                    .setNegativeText("No")
                    .setCancelable(Boolean.FALSE)
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    })
                    .setPositiveText("Yes")
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    })
                    .show();
        }



    /*    new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(IntroActivity.this, MainMenuActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);*/
    }


    private class PrefetchData extends AsyncTask<Void, Integer, Void> {
        private Boolean is_success = false;

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPreExecute() {
            //   setColorProgressBar();
            super.onPreExecute();
            progressBar.setProgress(0);
        }

        @Override
        protected Void doInBackground(Void... params) {
            is_success = false;
            publishProgress(10);
            try {
               /* publishProgress(40);
                if (
                        db.deleteAllKlinik()
                        && KlinikServices.getAllKlinikServices(db)
                        && db.deleteAllDokter()
                        && DateUtil.getCurrentDateFromServer(IntroActivity.this)
                        && DokterServices.insertDokterToTable(db)) {
                    publishProgress(60);
                    is_success = true;
                }*/

                if (db.deleteAllKlinik()) {
                    publishProgress(10);
                    if (KlinikServices.getAllKlinikServices(db)) {

                        publishProgress(30);
                        if (db.deleteAllDokter()) {

                            publishProgress(50);

                            if (DateUtil.getCurrentDateFromServer(IntroActivity.this)) {

                                publishProgress(55);
                                if (DokterServices.insertDokterToTable(db)) {

                                    publishProgress(70);
                                    if(DateUtil.getMaxDate(IntroActivity.this))
                                    {
                                        publishProgress(95);
                                        is_success = true;

                                    }



                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (is_success) {
                progressBar.setProgress(100);
                Intent intent = new Intent(IntroActivity.this, MainMenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                finish();
            }

        }


    }




}


