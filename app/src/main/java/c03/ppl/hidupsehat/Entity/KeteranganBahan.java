package c03.ppl.hidupsehat.Entity;

/**
 * Created by wahyuoi on 25/05/15.
 */
public class KeteranganBahan {
    String nama;
    String keterangan;

    public KeteranganBahan(String nama, String keterangan) {
        this.nama = nama;
        this.keterangan = keterangan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
