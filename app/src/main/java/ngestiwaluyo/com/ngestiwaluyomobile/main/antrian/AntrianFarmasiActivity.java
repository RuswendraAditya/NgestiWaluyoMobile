package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.adapter.AntrianFarmasiAdapter;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.AntrianFarmasi;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.service.AntrianFarmasiServices;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DateUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DialogAlert;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.NetworkStatus;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AntrianFarmasiActivity extends AppCompatActivity {
    private List<AntrianFarmasi> antrianFarmasiList = new ArrayList<>();
    @BindView(R.id.rv_antrian_farmasi)
    RecyclerView rv_antrian_farmasi;
    AntrianFarmasiAdapter adapter;
    @BindView(R.id.btnSearchAntrianFarmasi)
    Button btnSearchAntrianFarmasi;
    @BindView(R.id.txtInputNoRMFarmasi)
    EditText txtInputNoRMFarmasi;
    private NetworkStatus networkStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antrian_farmasi);
        ButterKnife.bind(this);
        networkStatus= new NetworkStatus();
       // antrianFarmasiList = db.getRegisFromDBByNoRM(SharedData.getKey(RegistrationHistoryActivity.this, "noRM"));

    }
    private void  getAntrianFarmasi() {
        String date = SharedData.getKey(AntrianFarmasiActivity.this, "currentDate");
        date  = DateUtil.changeFormatDate(date,"dd/MM/yyyy","MM/dd/yyyy");
          try {
            antrianFarmasiList = AntrianFarmasiServices.getTrackingFarmasiRajal(txtInputNoRMFarmasi.getText().toString(),date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnSearchAntrianFarmasi)
    public void antrianClick(View view) {
        if(isValiadateSearh())
        {   if (antrianFarmasiList.size()>0)
              {
            antrianFarmasiList.clear();
            adapter.notifyDataSetChanged();
                  }
            if (networkStatus.isOnline(AntrianFarmasiActivity.this)) {
                runningServicesGetAntrian();
            } else {
                Toast.makeText(AntrianFarmasiActivity.this, "Koneksi Internet Tidak Ditemukan saat login, mohon cek kembali", Toast.LENGTH_LONG).show();
            }
        }
        }

    private boolean isValiadateSearh() {
        if (txtInputNoRMFarmasi.getText().toString().length() == 0) {
            txtInputNoRMFarmasi.requestFocus();
            txtInputNoRMFarmasi.setError("Silahkan Masukkan no RM");
            return false;
        }

        return true;
    }



    private void  runningServicesGetAntrian() {

       AntrianFarmasiTask antrianFarmasiTask = new AntrianFarmasiTask();
       antrianFarmasiTask.execute();
    }


    private class AntrianFarmasiTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(AntrianFarmasiActivity.this,
                    "Please Wait",
                    "Sedang Mengambil Data Antrian Farmasi");
        }

        @Override
        protected Void doInBackground(Void... params) {
            getAntrianFarmasi();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
            adapter = new AntrianFarmasiAdapter(AntrianFarmasiActivity.this, antrianFarmasiList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rv_antrian_farmasi.setLayoutManager(mLayoutManager);
            rv_antrian_farmasi.setItemAnimator(new DefaultItemAnimator());
            rv_antrian_farmasi.setAdapter(adapter);
            if(antrianFarmasiList.size()==0)
            {
                DialogAlert dialogAlert = new DialogAlert();
                dialogAlert.alertValidation(AntrianFarmasiActivity.this, "Peringatan", "Resep Tidak Ditemukan");
            }
        }

    }
}


