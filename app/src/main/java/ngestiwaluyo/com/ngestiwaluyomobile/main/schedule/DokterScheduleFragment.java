package ngestiwaluyo.com.ngestiwaluyomobile.main.schedule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.registration.DokterPickerActivity;
import ngestiwaluyo.com.ngestiwaluyomobile.main.schedule.adapter.DokterScheduleAdapter;
import ngestiwaluyo.com.ngestiwaluyomobile.main.schedule.model.DokterKlinikSchedule;
import ngestiwaluyo.com.ngestiwaluyomobile.main.schedule.service.DokterScheduleService;
import ngestiwaluyo.com.ngestiwaluyomobile.main.utility.DialogAlert;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;


public class DokterScheduleFragment extends Fragment {
    @BindView(R.id.editTextDokterSchedule)
    EditText editTextDokterSchedule;
    String kode_dokter;
    String nama_dokter;
    @BindView(R.id.btnCariJadwalDokter)
    Button btnCariJadwalDokter;
    List<DokterKlinikSchedule> listJadwal = new ArrayList<>();
    @BindView(R.id.rvJadwalDokter)
    RecyclerView rvJadwalDokter;

    @OnClick(R.id.editTextDokterSchedule)

    public void editTextDokterScheduleClick(View view) {
        editTextDokterSchedule.setText("");
        Intent intent = new Intent(getActivity(), DokterPickerActivity.class);
        intent.putExtra("kodeKlinik", "");
        intent.putExtra("kodeDokter", "dummy_dokter");
        this.startActivityForResult(intent, 2);

    }

    @OnClick(R.id.btnCariJadwalDokter)
    public void btnCariJadwalDokterClick(View view) {
        if (editTextDokterSchedule.getText().toString().compareToIgnoreCase("") != 0) {

            DokterSchduleTask dokterSchduleTask = new DokterSchduleTask();
            dokterSchduleTask.execute();

        } else {
            DialogAlert dialogAlert = new DialogAlert();
            dialogAlert.alertValidation(getActivity(), "Peringatan", "Anda Belum Memilih Dokter");
        }

    }


    public DokterScheduleFragment() {
        // Required empty public constructor
    }

    public static DokterScheduleFragment newInstance() {

        Bundle args = new Bundle();
        DokterScheduleFragment fragment = new DokterScheduleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                kode_dokter = data.getStringExtra("nidDokter");
                nama_dokter = data.getStringExtra("namaDokter");
                editTextDokterSchedule.setText(nama_dokter);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    private void getJadwalDokter() {
        try {
            listJadwal.clear();
            listJadwal = DokterScheduleService.getJadwalDokterByNid(kode_dokter);
            final DokterScheduleAdapter dokterScheduleAdapter = new DokterScheduleAdapter(getActivity(), listJadwal);
            dokterScheduleAdapter.notifyDataSetChanged();
            if (listJadwal.size() > 0) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rvJadwalDokter.setLayoutManager(mLayoutManager);
                        rvJadwalDokter.setItemAnimator(new DefaultItemAnimator());
                        rvJadwalDokter.setAdapter(dokterScheduleAdapter);

                    }
                });
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                        rvJadwalDokter.setLayoutManager(mLayoutManager);
                        rvJadwalDokter.setItemAnimator(new DefaultItemAnimator());
                        rvJadwalDokter.setAdapter(dokterScheduleAdapter);
                        DialogAlert dialogAlert = new DialogAlert();
                        dialogAlert.alertValidation(getActivity(), "Peringatan", "Jadwal Tidak Ditemukan");
                    }
                });

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dokter_schedule, container, false);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        if (bundle != null)
        {   kode_dokter= bundle.getString("nid");
            nama_dokter= bundle.getString("namaDokter");
            editTextDokterSchedule.setText(nama_dokter);
            if (editTextDokterSchedule.getText().toString().compareToIgnoreCase("") != 0) {
                DokterSchduleTask dokterSchduleTask = new DokterSchduleTask();
                dokterSchduleTask.execute();

            }
        }

        return view;

    }

    private class DokterSchduleTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(getActivity(),
                    "Please Wait",
                    "Mengambil Jadwal Dokter");
        }

        @Override
        protected Void doInBackground(Void... params) {
            getJadwalDokter();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressDialog.dismiss();
        }
    }


}