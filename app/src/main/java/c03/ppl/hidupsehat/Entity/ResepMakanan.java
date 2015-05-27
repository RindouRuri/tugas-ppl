package c03.ppl.hidupsehat.Entity;

/**
 * Created by wahyuoi on 06/05/15.
 */
public class ResepMakanan {
    int idUser;
    int id;
    String nama;
    int kalori;
    String caraMembuat;
    String saranPenyajian;
    // TODO tambah detail

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getKalori() {
        return kalori;
    }

    public void setKalori(int kalori) {
        this.kalori = kalori;
    }

    public String getCaraMembuat() {
        return caraMembuat;
    }

    public void setCaraMembuat(String caraMembuat) {
        this.caraMembuat = caraMembuat;
    }

    public String getSaranPenyajian() {
        return saranPenyajian;
    }

    public void setSaranPenyajian(String saranPenyajian) {
        this.saranPenyajian = saranPenyajian;
    }
}
