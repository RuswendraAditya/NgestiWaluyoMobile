package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

import ngestiwaluyo.com.ngestiwaluyomobile.R;
import ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model.AntrianFarmasi;

import static com.kofigyan.stateprogressbar.StateProgressBar.StateNumber.FOUR;
import static com.kofigyan.stateprogressbar.StateProgressBar.StateNumber.THREE;
import static com.kofigyan.stateprogressbar.StateProgressBar.StateNumber.TWO;

public class AntrianFarmasiAdapter  extends RecyclerView.Adapter<AntrianFarmasiAdapter.CustomViewHolder> {
   // String[] descriptionData = {"Obat Sedang Disiapkan", "Obat Siap Diambil","Pasien Dipanggil", "Obat Telah Diambil"};

    private List<AntrianFarmasi> antrianFarmasiList;
    private Context mContext;

    public AntrianFarmasiAdapter(Context context, List<AntrianFarmasi> feedItemList) {
        this.antrianFarmasiList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.antrian_farmasi_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        AntrianFarmasi antrianFarmasiData = antrianFarmasiList.get(i);
        String obatDisiapkan = "";
        if(antrianFarmasiData.getReview().length() == 0 && antrianFarmasiData.getJamEntri().length() > 0 )
        {
            obatDisiapkan = "Obat Disiapkan"+"\n"+"("+antrianFarmasiData.getJamEntri()+")";


        }
        else
        {
            obatDisiapkan = "Obat Disiapkan"+"\n"+"("+antrianFarmasiData.getReview()+")";

        }
        //String obatDisiapkan = "Obat Sedang Disiapkan"+"\n"+"("+antrianFarmasiData.getReview()+")";
        String obatSiapDiambil = "Obat Siap Diambil"+"\n"+"("+antrianFarmasiData.getJamKontrol()+")";
        String pasienDipanggil = "Pasien Dipanggil"+"\n"+"("+antrianFarmasiData.getPanggil()+")";
        String pasieSerah = "Obat Telah Diambil"+"\n"+"("+antrianFarmasiData.getSerah()+")";

        String[] descriptionData = {obatDisiapkan , "Obat Siap Diambil","Pasien Dipanggil", "Obat Telah Diambil"};
        customViewHolder.txtNamaAntrianFarmasi.setText("Nama : "+antrianFarmasiData.getNamaPasien());
        customViewHolder.txtKodeResep.setText("Kode Resep : "+ antrianFarmasiData.getKodeResep());
        customViewHolder.txtKlinikFarmasi.setText("Klinik :"+antrianFarmasiData.getKlinik());
        customViewHolder.txtDokterFarmasi.setText("Dokter :"+antrianFarmasiData.getDokter());
        customViewHolder.antrianProgress.setStateDescriptionData(descriptionData);
        customViewHolder.txtNoAntrianFarmasi.setText("No Antrian :" + antrianFarmasiData.getNoAntrian());
        customViewHolder.txtStatusAntrian.setText("Status : Obat Disiapkan");
      //  customViewHolder.txtStatusAntrian.setTextColor(Color.parseColor("#42f548"));

        if(antrianFarmasiData.getJamKontrol().length() >0)
        {
            String[] descriptionData_2 = {obatDisiapkan , obatSiapDiambil,"Pasien Dipanggil", "Obat Telah Diambil"};

            customViewHolder.txtStatusAntrian.setText("Status : Obat Siap Diambil");
            customViewHolder.antrianProgress.setCurrentStateNumber(TWO);
            customViewHolder.antrianProgress.setStateDescriptionData(descriptionData_2);
        }

        if(antrianFarmasiData.getPanggil().length() >0)
        {
            String[] descriptionData_3 = {obatDisiapkan , obatSiapDiambil,pasienDipanggil, "Obat Telah Diambil"};

            customViewHolder.txtStatusAntrian.setText("Status : Pasien Dipanggl");
            customViewHolder.antrianProgress.setCurrentStateNumber(THREE);
            customViewHolder.antrianProgress.setStateDescriptionData(descriptionData_3);
        }


        if(antrianFarmasiData.getSerah().length() >0)
        {
            String[] descriptionData_4 = {obatDisiapkan , obatSiapDiambil,pasienDipanggil, pasieSerah};

            customViewHolder.txtStatusAntrian.setText("Status : Obat Telah Diambil");
            customViewHolder.antrianProgress.setCurrentStateNumber(FOUR);
            customViewHolder.antrianProgress.setAllStatesCompleted(true);
            customViewHolder.antrianProgress.setStateDescriptionData(descriptionData_4);
        }
    }

    @Override
    public int getItemCount() {
        return (null != antrianFarmasiList ? antrianFarmasiList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtKodeResep;
        protected TextView txtNamaAntrianFarmasi;
        protected  TextView txtNoAntrianFarmasi;
        protected TextView txtStatusAntrian;
        protected StateProgressBar antrianProgress;
        protected  TextView txtDokterFarmasi;
        protected TextView txtKlinikFarmasi;
        public CustomViewHolder(View view) {
            super(view);
            this.txtKodeResep =  (TextView) view.findViewById(R.id.txtKodeResep);
              this.txtNoAntrianFarmasi = (TextView) view.findViewById(R.id.txtNoAntrianFarmasi);
            this.txtNamaAntrianFarmasi =  (TextView) view.findViewById(R.id.txtNamaAntrianFarmasi);
            this.txtStatusAntrian = (TextView) view.findViewById(R.id.txtStatusAntrian);
            this.antrianProgress = (StateProgressBar) view.findViewById(R.id.farmasiProgessAntrian);
            this.txtDokterFarmasi = (TextView) view.findViewById(R.id.txtDokterFarmasi);
            this.txtKlinikFarmasi = (TextView) view.findViewById(R.id.txtKlinikFarmasi);

        }
    }
}
