package c03.ppl.hidupsehat.Tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;

import java.util.List;

import c03.ppl.hidupsehat.Client.HidupSehatClient;
import c03.ppl.hidupsehat.Entity.BahanMakanan;
import c03.ppl.hidupsehat.Entity.ResepDanBahan;
import c03.ppl.hidupsehat.Entity.ResepMakanan;
import c03.ppl.hidupsehat.Entity.User;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wahyuoi on 06/05/15.
 */
public class Sync {
    final String API_URL = "http://ppl-c03.cs.ui.ac.id/index.php/service/";

    public void fetch(final Context context) {

        // Create a very simple REST adapter which points the HidupSehat API endpoint.
        HidupSehatClient client = ServiceGenerator.createService(HidupSehatClient.class, API_URL);

        // Fetch and print a list of the contributors to this library.
        client.getAllUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                DatabaseInfo dbInfo = new DatabaseInfo(context);
//                dbInfo.deleteTableContent(DatabaseField.USER_TABLE);
                for (User user : users) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseField.USER_COLUMN_ID, user.getId());
                    values.put(DatabaseField.USER_COLUMN_NAMA, user.getNama());
                    values.put(DatabaseField.USER_COLUMN_KELAMIN, user.getKelamin());
                    values.put(DatabaseField.USER_COLUMN_PASSWORD, user.getPassword());
                    values.put(DatabaseField.USER_COLUMN_USERNAME, user.getUsername());
                    values.put(DatabaseField.USER_COLUMN_BERAT, user.getBerat_Badan());
                    values.put(DatabaseField.USER_COLUMN_TINGGI, user.getTinggi_Badan());
                    try{
                        if (!dbInfo.isExists(DatabaseField.USER_TABLE, user.getUsername()))
                            dbInfo.insert(DatabaseField.USER_TABLE,values);
                    } catch (SQLiteConstraintException e){}
                }
                System.err.println("Sync users down ok");
            }

            @Override
            public void failure(RetrofitError error) {
                System.err.println("Sync users down fail");
            }
        });

        client.getAllResep(new Callback<List<ResepMakanan>>() {
            @Override
            public void success(List<ResepMakanan> resepMakanans, Response response) {
                DatabaseInfo dbInfo = new DatabaseInfo(context);
                dbInfo.deleteTableContent(DatabaseField.RESEP_MAKANAN_TABLE);
                for (ResepMakanan resepMakanan : resepMakanans) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseField.RESEP_MAKANAN_ID, resepMakanan.getId());
                    values.put(DatabaseField.RESEP_MAKANAN_CARA_MEMBUAT, resepMakanan.getCaraMembuat());
                    values.put(DatabaseField.RESEP_MAKANAN_KALORI, resepMakanan.getKalori());
                    values.put(DatabaseField.RESEP_MAKANAN_NAMA, resepMakanan.getNama());
                    values.put(DatabaseField.RESEP_MAKANAN_SARAN_PENYAJIAN, resepMakanan.getSaranPenyajian());

                    dbInfo.insert(DatabaseField.RESEP_MAKANAN_TABLE,values);
                }
                System.err.println("Sync resep makanan down ok");
            }

            @Override
            public void failure(RetrofitError error) {
                System.err.println("Sync resep makanan down fail");
            }
        });

        client.getAllBahan(new Callback<List<BahanMakanan>>() {
            @Override
            public void success(List<BahanMakanan> bahanMakanans, Response response) {
                DatabaseInfo dbInfo = new DatabaseInfo(context);
                dbInfo.deleteTableContent(DatabaseField.BAHAN_MAKANAN_TABLE);
                for (BahanMakanan bahanMakanan : bahanMakanans) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseField.BAHAN_MAKANAN_ID, bahanMakanan.getId());
                    values.put(DatabaseField.BAHAN_MAKANAN_NAMA, bahanMakanan.getNama());
                    values.put(DatabaseField.BAHAN_MAKANAN_KALORI, bahanMakanan.getKalori());
                    dbInfo.insert(DatabaseField.BAHAN_MAKANAN_TABLE, values);
                }
                System.err.println("Sync bahan makanan down ok");
            }

            @Override
            public void failure(RetrofitError error) {
                System.err.println("Sync bahana makanan down fail");
            }
        });

        client.getAllResepDanBahan(new Callback<List<ResepDanBahan>>() {
            @Override
            public void success(List<ResepDanBahan> resepDanBahans, Response response) {
                DatabaseInfo dbInfo = new DatabaseInfo(context);
                dbInfo.deleteTableContent(DatabaseField.RESEP_DAN_BAHAN_TABLE);
                for(ResepDanBahan resep : resepDanBahans) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseField.RESEP_DAN_BAHAN_ID_BAHAN, resep.getIdBahan());
                    values.put(DatabaseField.RESEP_DAN_BAHAN_ID_RESEP, resep.getIdResep());
                    values.put(DatabaseField.RESEP_DAN_BAHAN_KETERANGAN, resep.getKeterangan());

                    dbInfo.insert(DatabaseField.RESEP_DAN_BAHAN_TABLE, values);
                }
                System.err.println("sync resep dan bahan up ok");
            }

            @Override
            public void failure(RetrofitError error) {
                System.err.println("sync resep dan bahan down fail");
            }
        });

//        // TODO DUMMY FAVORIT
//        DatabaseInfo dbInfo = new DatabaseInfo(context);
//        ContentValues values = new ContentValues();
//        values.put(DatabaseField.FAVORIT_RESEP, 1);
//        values.put(DatabaseField.FAVORIT_USER, 1);
//        dbInfo.insert(DatabaseField.FAVORIT_TABLE, values);
//        System.err.println("Insert dummy fav done");
    }

    public void registerUser(ContentValues values) {
        HidupSehatClient client = ServiceGenerator.createService(HidupSehatClient.class, API_URL);
        User user = new User();
        user.setId(values.getAsInteger(DatabaseField.USER_COLUMN_ID));
        user.setNama(values.getAsString(DatabaseField.USER_COLUMN_NAMA));
        user.setBerat_Badan(values.getAsInteger(DatabaseField.USER_COLUMN_BERAT));
        user.setTinggi_Badan(values.getAsInteger(DatabaseField.USER_COLUMN_TINGGI));
        user.setKelamin(values.getAsString(DatabaseField.USER_COLUMN_KELAMIN));
        user.setPassword(values.getAsString(DatabaseField.USER_COLUMN_PASSWORD));
        user.setUsername(values.getAsString(DatabaseField.USER_COLUMN_USERNAME));
        client.postUser(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                System.err.println("Sync up ok");
            }

            @Override
            public void failure(RetrofitError error) {
                System.err.println("Sync up fail : " + error.getResponse().getUrl());
            }
        });
    }
}
