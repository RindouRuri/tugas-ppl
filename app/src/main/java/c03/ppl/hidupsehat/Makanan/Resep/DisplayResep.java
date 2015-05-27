package c03.ppl.hidupsehat.Makanan.Resep;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import c03.ppl.hidupsehat.Entity.KeteranganBahan;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 20/05/15.
 */
public class DisplayResep extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detil_resep);
        final DatabaseInfo dbInfo = new DatabaseInfo(this);
        final int id = (getIntent().getExtras().getInt("id"));
        final int idUser = dbInfo.getIdLogin(DatabaseField.USER_TABLE, DatabaseField.USER_COLUMN_IS_LOGIN);

        Cursor cursor = dbInfo.getFromQuery("select * from " + DatabaseField.RESEP_MAKANAN_TABLE + " JOIN "
                + DatabaseField.RESEP_DAN_BAHAN_TABLE +" ON " + DatabaseField.RESEP_MAKANAN_TABLE + "."
                + DatabaseField.RESEP_MAKANAN_ID + " = " + DatabaseField.RESEP_DAN_BAHAN_TABLE + "."
                + DatabaseField.RESEP_DAN_BAHAN_ID_RESEP +" JOIN " + DatabaseField.BAHAN_MAKANAN_TABLE
                + " ON " + DatabaseField.BAHAN_MAKANAN_TABLE + "." + DatabaseField.BAHAN_MAKANAN_ID
                + " = " + DatabaseField.RESEP_DAN_BAHAN_TABLE + "." + DatabaseField.RESEP_DAN_BAHAN_ID_BAHAN
                + " where " + DatabaseField.RESEP_MAKANAN_TABLE + "." + DatabaseField.RESEP_MAKANAN_ID
                + " = '" + id + "'");

        ArrayList<KeteranganBahan> array = new ArrayList();
        String bahan = "";
        while(cursor.isAfterLast() == false){
            array.add(new KeteranganBahan((cursor.getString(cursor.getColumnIndex(DatabaseField.BAHAN_MAKANAN_NAMA))),
                            (cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_DAN_BAHAN_KETERANGAN)))));
            bahan += cursor.getString(cursor.getColumnIndex(DatabaseField.BAHAN_MAKANAN_NAMA)) + "\n";
            bahan += cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_DAN_BAHAN_KETERANGAN)) + "\n";
            cursor.moveToNext();
        }

        TextView listView = (TextView) findViewById(R.id.bahan_bahan);
//        CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.atasbawah, array);
//        listView.setAdapter(adapter);
        listView.setText(bahan);
        // cara membuat
        cursor = dbInfo.getFromQuery("select * from " + DatabaseField.RESEP_MAKANAN_TABLE + " where id = '" + id + "'");
        String namaMakanan = cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_NAMA));
        String kaloriTotal = cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_KALORI));
        String caraMembuat = cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_CARA_MEMBUAT));
        String saranPenyajian = cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_SARAN_PENYAJIAN));

        TextView nama = (TextView) findViewById(R.id.nama_resep_makanan);
        TextView cara = (TextView) findViewById(R.id.cara_membuat);
        TextView saran = (TextView) findViewById(R.id.saran_penyajian);
        TextView kalori = (TextView) findViewById(R.id.total_kalori);

        nama.setText(removeTag(namaMakanan));
        cara.setText(removeTag(caraMembuat));
        saran.setText(removeTag(saranPenyajian));
        kalori.setText(removeTag(kaloriTotal));

        // favorit
        boolean isFav = dbInfo.isFavorit(id, idUser);
        CheckBox favorit = (CheckBox) findViewById(R.id.favorit);
        favorit.setChecked(isFav);
        favorit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ContentValues values = new ContentValues();
                    values.put(DatabaseField.FAVORIT_RESEP, id);
                    values.put(DatabaseField.FAVORIT_USER, idUser);
                    dbInfo.insert(DatabaseField.FAVORIT_TABLE, values);
                } else {
                    String where = DatabaseField.FAVORIT_RESEP + "=? AND "
                            + DatabaseField.FAVORIT_USER + "=?";
                    dbInfo.deleteFrom(DatabaseField.FAVORIT_TABLE, where,
                            new String[] {String.valueOf(id), String.valueOf(idUser)});
                }
            }
        });
    }

    public String removeTag(String text){
        return text.replaceAll("<[^>]*>","").replaceAll("(\r\n)+", "\r\n").trim();
    }
    private class ViewHolder {
        TextView nama;
        TextView keterangan;
    }
    private class CustomListViewAdapter extends ArrayAdapter<KeteranganBahan> {
        Context context;
        public CustomListViewAdapter(Context context, int resourceId, ArrayList<KeteranganBahan> array) {
            super(context, resourceId, array);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            KeteranganBahan ketBahan = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.atasbawah, null);
                holder = new ViewHolder();
                holder.nama = (TextView) convertView.findViewById(R.id.item);
                holder.keterangan = (TextView) convertView.findViewById(R.id.sub);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.keterangan.setText(ketBahan.getKeterangan());
            holder.nama.setText(ketBahan.getNama());

            return convertView;
        }
    }
}
