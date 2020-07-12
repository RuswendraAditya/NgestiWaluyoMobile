package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model;

public class Antrian {
    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getKodeKlinik() {
        return kodeKlinik;
    }

    public void setKodeKlinik(String kodeKlinik) {
        this.kodeKlinik = kodeKlinik;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    private String tgl;
    private String kodeKlinik;
    private String nid;
}
