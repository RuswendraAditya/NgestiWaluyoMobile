package ngestiwaluyo.com.ngestiwaluyomobile.main.antrian.model;

public class AntrianResult {
    private String totalDilayani;

    public String getTotalDilayani() {
        return totalDilayani;
    }

    public void setTotalDilayani(String totalDilayani) {
        this.totalDilayani = totalDilayani;
    }

    public String getTotalPasien() {
        return totalPasien;
    }

    public void setTotalPasien(String totalPasien) {
        this.totalPasien = totalPasien;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String totalPasien;
    private String response;
}
