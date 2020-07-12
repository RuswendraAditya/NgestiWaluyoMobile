package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.kofigyan.stateprogressbar.StateProgressBar;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AllStatesCompletedActivity extends AppCompatActivity {
    String[] descriptionData = {"Obat Sedang Disiapkan", "Obat Siap Diambil", "Obat Telah Diambil"};
    @BindView(R.id.farmasiProgess)
    StateProgressBar farmasiProgess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_states_completed);
        ButterKnife.bind(this);
        farmasiProgess.setStateDescriptionData(descriptionData);

    }
}
