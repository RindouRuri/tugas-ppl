package c03.ppl.hidupsehat.Entity;

/**
 * Created by wahyuoi on 06/05/15.
 */
public class User {
    int id;
    String kelamin;
    int berat_Badan;
    int tinggi_Badan;
    String nama;
    String username;
    String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public void setBerat_Badan(int berat_Badan) {
        this.berat_Badan = berat_Badan;
    }

    public void setTinggi_Badan(int tinggi_Badan) {
        this.tinggi_Badan = tinggi_Badan;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getKelamin() {
        return kelamin;
    }

    public int getBerat_Badan() {
        return berat_Badan;
    }

    public int getTinggi_Badan() {
        return tinggi_Badan;
    }

    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
