package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.Antrian;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.AntrianResult;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.service.AntrianKlinikServices;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DatabaseHandler;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DateUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DialogAlert;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.NetworkStatus;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AntrianKlinikActivity extends AppCompatActivity {
    private DialogAlert dialogAlert;
    public static final int REQUEST_CODE_KLINIK_ANTRIAN= 21;
    public static final int REQUEST_CODE_DOKTER_ANTRIAN = 22;
    NetworkStatus networkStatus;
    @BindView(R.id.editklinikpickerAntrian)
    EditText editKlinikPickerAntrian;
    @BindView(R.id.editDokPickerAntrian)
    EditText editDokPickerAntrian;
    @BindView(R.id.btnAntrian)
    Button btnAntrian;
    private String kodeKlinik = "";
    private String namaKlinik = null;
    private String tgl_regis =  null;
    private String nidDokter ="";
    private String namaDokter = null;
    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_klinik);
        ButterKnife.bind(this);
        networkStatus = new NetworkStatus();
        db = new DatabaseHandler(AntrianKlinikActivity.this);

        tgl_regis= SharedData.getKey(AntrianKlinikActivity.this,"currentDate");
        tgl_regis =  DateUtil.changeFormatDate(tgl_regis,"dd/MM/yyyy","MM/dd/yyyy");
        Log.d("antrian",tgl_regis);
    }

    @OnClick(R.id.editklinikpickerAntrian)
    public void editklinikpickerClick(View view) {
        if (networkStatus.isOnline(AntrianKlinikActivity.this)) {
            editKlinikPickerAntrian.setText("");
            kodeKlinik ="";
            //  editDokPicker.setText("");


                Intent intent = new Intent(AntrianKlinikActivity.this, KlinikAntrianPickerActivity.class);
                intent.putExtra("tgl_regis",tgl_regis);
                intent.putExtra("kodeKlinik", "");
                intent.putExtra("kodeDokter", nidDokter);
                intent.putExtra("namaDokter",namaDokter);
                startActivityForResult(intent, REQUEST_CODE_KLINIK_ANTRIAN);



        } else {
            Toast.makeText(AntrianKlinikActivity.this, "Koneksi Internet Tidak Ditemukan Saat mengambil data Klinik,Mohon Coba Kembali", Toast.LENGTH_LONG).show();

        }

    }

    @OnClick(R.id.editDokPickerAntrian)
    public void editDokPickerClick(View view) {
        editDokPickerAntrian.setText("");
        nidDokter  = "";
        if (networkStatus.isOnline(AntrianKlinikActivity.this)) {
                Intent intent = new Intent(AntrianKlinikActivity.this, DokterAntrianPickerActivity.class);
                intent.putExtra("tgl_regis", tgl_regis);
                intent.putExtra("kodeKlinik", kodeKlinik);
                intent.putExtra("kodeDokter", "");
                startActivityForResult(intent, REQUEST_CODE_DOKTER_ANTRIAN);


        }
        else
        {
            Toast.makeText(AntrianKlinikActivity.this, "Koneksi Internet Tidak Ditemukan saat mengambil data dokter,Mohon Coba Kembali", Toast.LENGTH_LONG).show();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_KLINIK_ANTRIAN) {
            if (resultCode == RESULT_OK) {
                kodeKlinik = data.getStringExtra("kodeKlinik");
                namaKlinik = data.getStringExtra("namaKlinik");
                editKlinikPickerAntrian.setText(namaKlinik);
            }
        }
        if (requestCode == REQUEST_CODE_DOKTER_ANTRIAN) {
            if (resultCode == RESULT_OK) {
                //Log.d("dokter",data.getStringExtra("nidDokter"));
                nidDokter = data.getStringExtra("nidDokter");
                namaDokter = data.getStringExtra("namaDokter");
                editDokPickerAntrian.setText(namaDokter);
            }
        }
    }


    private void getAntrian() {
        Antrian antrian = new Antrian();
        AntrianResult antrianResult = new AntrianResult();
        antrian.setTgl(tgl_regis);
        antrian.setKodeKlinik(kodeKlinik);
        antrian.setNid(nidDokter);
        try {
           antrianResult = AntrianKlinikServices.getAntrianKlinik(antrian);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String totalDilayani = antrianResult.getTotalDilayani();
        final AntrianResult finalAntrianResult = antrianResult;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final String infoAntrian = "Total Pasien dilayani adalah : " + finalAntrianResult.getTotalDilayani()+", Hubungi petugas Klinik jika nomer antrian anda sudah terlewat";
                dialogAlert = new DialogAlert();
                dialogAlert.alertValidation(AntrianKlinikActivity.this, "Info Antrian", infoAntrian);

            }
        });

    }


    @OnClick(R.id.btnAntrian)
    public void editBtnAntrianClick(View view) {
        if (networkStatus.isOnline(AntrianKlinikActivity.this)) {
           if (editKlinikPickerAntrian.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(AntrianKlinikActivity.this, "Peringatan", "Anda Belum Memilih Klinik");
                    }
                });

            }
            else if (editDokPickerAntrian.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(AntrianKlinikActivity.this, "Peringatan", "Anda Belum Memilih Dokter Klinik");
                    }
                });

            }
            else
            {
               AntrianTask antrianTask = new AntrianTask();
                antrianTask.execute();
                //  RegistrationTask registrationTask = new RegistrationTask();
                // registrationTask.execute();
            }

        } else {
            Toast.makeText(AntrianKlinikActivity.this, "Koneksi Internet Tidak Ditemukan,Mohon Coba Kembali", Toast.LENGTH_LONG).show();

        }


    }


    private class AntrianTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AntrianKlinikActivity.this,
                    "Please Wait",
                    "Sedang Mencari Data Antrian Dokter");
        }

        @Override
        protected Void doInBackground(Void... params) {
            getAntrian();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }


}
