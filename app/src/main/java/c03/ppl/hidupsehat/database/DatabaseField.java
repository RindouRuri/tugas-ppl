package c03.ppl.hidupsehat.database;

/**
 * Created by wahyuoi on 04/04/15.
 */
public class DatabaseField {
    public static final String DATABASE_NAME = "Sehat.db";
    // USER
    public static final String USER_TABLE = "user";
    public static final String USER_COLUMN_USERNAME = "username";
    public static final String USER_COLUMN_PASSWORD = "password";
    public static final String USER_COLUMN_IS_LOGIN = "isLogin";
    public static final String USER_COLUMN_NAMA = "nama";
    public static final String USER_COLUMN_TINGGI = "tinggi" ;
    public static final String USER_COLUMN_BERAT = "berat" ;
    public static final String USER_COLUMN_UMUR = "umur";
    public static final String USER_COLUMN_ID = "id";
    public static final String USER_COLUMN_KELAMIN = "kelamin";

    //RESEP MAKANAN
    public static final String RESEP_MAKANAN_TABLE = "resep_makanan";
    public static final String RESEP_MAKANAN_ID_USER = "idUser";
    public static final String RESEP_MAKANAN_ID = "id";
    public static final String RESEP_MAKANAN_NAMA = "nama";
    public static final String RESEP_MAKANAN_KALORI = "kalori";
    public static final String RESEP_MAKANAN_CARA_MEMBUAT = "caraMembuat";
    public static final String RESEP_MAKANAN_SARAN_PENYAJIAN = "saranPenyajian";


    //BAHAN MAKANAN
    public static  final  String BAHAN_MAKANAN_TABLE = "bahan_makanan";
    public static  final  String BAHAN_MAKANAN_ID = "id";
    public static  final  String BAHAN_MAKANAN_NAMA = "nama";
    public static  final  String BAHAN_MAKANAN_KALORI = "kalori";


    //RESEP DAN BAHAN
    public static final String RESEP_DAN_BAHAN_TABLE = "resep_dan_bahan";
    public static final String RESEP_DAN_BAHAN_ID_RESEP = "idResep";
    public static final String RESEP_DAN_BAHAN_ID_BAHAN = "idBahan";
    public static final String RESEP_DAN_BAHAN_KETERANGAN = "keterangan";

    // FAVORIT
    public static final String FAVORIT_TABLE = "favorit";
    public static final String FAVORIT_RESEP = "idResep";
    public static final String FAVORIT_USER = "idUser";

}
