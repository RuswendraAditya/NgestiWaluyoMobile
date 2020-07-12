package ngestiwaluyo.com.ngestiwaluyomobile.main.pum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;

import org.json.JSONException;

import java.io.IOException;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.main.MainMenuActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.model.Pum;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.model.PumCheck;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.model.PumOrderResult;
import ngestiwaluyo.com.ngestiwaluyomobile.main.pum.service.PumOrderServices;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DateUtil;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DialogAlert;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.NetworkStatus;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.SharedData;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PumOrderActivity extends AppCompatActivity {
    @BindView(R.id.txtNoTelpPUM)
    TextView txtNoTelp;
    @BindView(R.id.txtAlamatPUM)
    TextView txtAlamatPum;
    @BindView(R.id.txtLokasiJemput)
    TextView txtLokasiJemput;
    @BindView(R.id.txtNamaPUM)
    TextView txtNamaPUM;
    @BindView(R.id.spinnerTypePUM)
    Spinner spinnerTypePUM;
    @BindView(R.id.txtKasus)
    TextView txtKasus;
    @BindView(R.id.btnOrderPUM)
    Button btnOrderPUM;
    private NetworkStatus networkStatus;
    private DialogAlert dialogAlert;

    private String date_now ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pum_order);
        ButterKnife.bind(this);
        networkStatus = new NetworkStatus();
    }
    @OnClick(R.id.btnOrderPUM)
    public void btnOrderPUMClick(View view)
    {
        date_now = "";
        //ambil data current date
        if (SharedData.getKey(PumOrderActivity.this, "currentDate") != null) {
            date_now = SharedData.getKey(PumOrderActivity.this, "currentDate");
        } else {
            try {
               DateUtil.getCurrentDateFromServer(PumOrderActivity.this);
                date_now = SharedData.getKey(PumOrderActivity.this, "currentDate");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         date_now = DateUtil.changeFormatDate(date_now,"dd/MM/yyyy","M/dd/yyyy");
         if (networkStatus.isOnline(PumOrderActivity.this)) {
            if (txtNoTelp.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "Anda Belum Mengisi No Telp");
                    }
                });

            }
            else if (txtNoTelp.length() < 6)
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "No Telp minimal 6 Digit");
                    }
                });

            }

            else if (date_now == "")
            {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "Tidak bisa mendapatkan tanggal order");
                    }
                });

            }
            else if (txtNamaPUM.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "Anda Belum Mengisi Nama");
                    }
                });

            }
            else if (txtAlamatPum.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "Anda Belum Mengisi Alamat");
                    }
                });

            }
            else if (txtLokasiJemput.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "Anda Belum Mengisi Lokasi Jemput");
                    }
                });

            }
            else if (txtKasus.length() <= 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan", "Anda Belum Mengisi Kasus");
                    }
                });

            }
            else
            {
                new MaterialStyledDialog.Builder(PumOrderActivity.this)
                        .setTitle("Konfirmasi")
                        .setDescription("Apakah Anda Yakin Order Ambulance atas nama : "+txtNamaPUM.getText().toString().trim() +
                                ",Telp : "+txtNoTelp.getText().toString().trim())
                        .setIcon(R.drawable.logo_rsnw)
                        .withIconAnimation(false)
                        .setCancelable(Boolean.FALSE)
                        .setNegativeText("Cancel")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("Yes")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                PumOrderActivityTask pumOrderActivityTask = new PumOrderActivityTask();
                                pumOrderActivityTask.execute();
                            }
                        })
                        .show();

                //  RegistrationTask registrationTask = new RegistrationTask();
                // registrationTask.execute();
            }

        } else {
            Toast.makeText(PumOrderActivity.this, "Koneksi Internet Tidak Ditemukan,Mohon Coba Kembali", Toast.LENGTH_LONG).show();

        }




    }



    private void orderPUM(Pum pum)
    {   PumOrderResult pumOrderResult = new PumOrderResult();
        try {
            pumOrderResult = PumOrderServices.postOrder(pum);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String deskripsiresponse = pumOrderResult.getDeskrippsiResponse();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               /* dialogAlert = new DialogAlert();
                dialogAlert.alertValidation(PumOrderActivity.this, "Peringatan",deskripsiresponse);*/
                new MaterialStyledDialog.Builder(PumOrderActivity.this)
                        .setTitle("Konfirmasi")
                        .setDescription(deskripsiresponse)
                        .setIcon(R.drawable.logo_rsnw)
                        .withIconAnimation(false)
                        .setCancelable(Boolean.FALSE)
                        .setPositiveText("OK")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Intent intent = new Intent(PumOrderActivity.this,MainMenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .show();

            }
        });


    }


    private void RepeatOrderPUM()
    {
        final Pum pum = new Pum();
        String type = String.valueOf(spinnerTypePUM.getSelectedItem());
        String darurat ;
        if(type.equalsIgnoreCase("Darurat"))
        {
            darurat = "y";

        }
        else
        {
            darurat = "t";
        }

        pum.setNoTelp(txtNoTelp.getText().toString().trim());
        pum.setNama(txtNamaPUM.getText().toString().trim());
        pum.setAlamat(txtAlamatPum.getText().toString().trim());
        pum.setLokasi(txtLokasiJemput.getText().toString().trim());
        pum.setDarurat(darurat);
        pum.setKasus(txtKasus.getText().toString().trim());
        pum.setTglOrder(date_now);
        orderPUM(pum);


    }

    private void doPumOrder() {
       final Pum pum = new Pum();
        String type = String.valueOf(spinnerTypePUM.getSelectedItem());
        String darurat ;
        if(type.equalsIgnoreCase("Darurat"))
        {
            darurat = "y";

        }
        else
        {
            darurat = "t";
        }

        PumOrderResult pumOrderResult= new PumOrderResult();
        PumCheck pumCheck  = new PumCheck();
        pum.setNoTelp(txtNoTelp.getText().toString().trim());
        pum.setNama(txtNamaPUM.getText().toString().trim());
        pum.setAlamat(txtAlamatPum.getText().toString().trim());
        pum.setLokasi(txtLokasiJemput.getText().toString().trim());
        pum.setDarurat(darurat);
        pum.setKasus(txtKasus.getText().toString().trim());
        pum.setTglOrder(date_now);
        try {
            pumCheck = PumOrderServices.checkDuplicatePUMOrder(pum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String feedBackCheck = pumCheck.getDeskripsiResponse();
        if(pumCheck.getlSudah())
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    new MaterialStyledDialog.Builder(PumOrderActivity.this)
                            .setTitle("Konfirmasi")
                            .setDescription(feedBackCheck+" apakah anda yakin tetap order ambulance?")
                            .setIcon(R.drawable.logo_rsnw)
                            .withIconAnimation(false)
                            .setCancelable(Boolean.FALSE)
                            .setNegativeText("Cancel")
                            .onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            })
                            .setPositiveText("Yes")
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    PumRepeatOrderActivityTask pumRepeatOrderActivityTask = new PumRepeatOrderActivityTask();
                                    pumRepeatOrderActivityTask.execute();
                                }
                            })
                            .show();
                }
            });
        }
        else
        {
            orderPUM(pum);
        }


    }





    private class PumOrderActivityTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PumOrderActivity.this,
                    "Please Wait",
                    "Sedang Proses Order");
        }

        @Override
        protected Void doInBackground(Void... params) {
            doPumOrder();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();

        }

    
    }





    private class PumRepeatOrderActivityTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(PumOrderActivity.this,
                    "Please Wait",
                    "Sedang Proses Order");
        }

        @Override
        protected Void doInBackground(Void... params) {
            RepeatOrderPUM();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();

        }


    }


}
