package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model;

public class AntrianFarmasi {

    private String kodeResep;
    private String noAntrian;

    public String getDokter() {
        return dokter;
    }

    public void setDokter(String dokter) {
        this.dokter = dokter;
    }

    public String getKlinik() {
        return klinik;
    }

    public void setKlinik(String klinik) {
        this.klinik = klinik;
    }

    private String dokter;
    private String klinik;
    public String getJamKontrol() {
        return jamKontrol;
    }

    public void setJamKontrol(String jamKontrol) {
        this.jamKontrol = jamKontrol;
    }

    private  String jamKontrol;

    public String getKodeResep() {
        return kodeResep;
    }

    public void setKodeResep(String kodeResep) {
        this.kodeResep = kodeResep;
    }

    public String getNoAntrian() {
        return noAntrian;
    }

    public void setNoAntrian(String noAntrian) {
        this.noAntrian = noAntrian;
    }

    public String getNoEpres() {
        return noEpres;
    }

    public void setNoEpres(String noEpres) {
        this.noEpres = noEpres;
    }

    public String getJamEntri() {
        return jamEntri;
    }

    public void setJamEntri(String jamEntri) {
        this.jamEntri = jamEntri;
    }

    public String getJamFilling() {
        return jamFilling;
    }

    public void setJamFilling(String jamFilling) {
        this.jamFilling = jamFilling;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getNoReg() {
        return noReg;
    }

    public void setNoReg(String noReg) {
        this.noReg = noReg;
    }

    public String getPanggil() {
        return panggil;
    }

    public void setPanggil(String panggil) {
        this.panggil = panggil;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getSerah() {
        return serah;
    }

    public void setSerah(String serah) {
        this.serah = serah;
    }

    public String getTglResep() {
        return tglResep;
    }

    public void setTglResep(String tglResep) {
        this.tglResep = tglResep;
    }

    private String noEpres;
    private String jamEntri;
    private String jamFilling;
    private String hari;
    private String noReg;
    private String panggil;

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    private String namaPasien;


    private String review;
    private String serah;
    private String tglResep;
}
