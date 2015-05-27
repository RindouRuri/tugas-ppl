package c03.ppl.hidupsehat.Makanan.Favorit;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import c03.ppl.hidupsehat.Makanan.Resep.DisplayResep;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 20/05/15.
 */
public class Index extends Activity {
    DatabaseInfo dbInfo;
    String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbInfo = new DatabaseInfo(this);
        int idUser = dbInfo.getIdLogin(DatabaseField.USER_TABLE, DatabaseField.USER_COLUMN_IS_LOGIN);
        query = "Select * from "+DatabaseField.RESEP_MAKANAN_TABLE+
                " INNER JOIN "+DatabaseField.FAVORIT_TABLE+
                " ON "+DatabaseField.RESEP_MAKANAN_TABLE+"."+DatabaseField.RESEP_MAKANAN_ID+" = "+
                DatabaseField.FAVORIT_TABLE+"."+DatabaseField.FAVORIT_RESEP+" where "+
                DatabaseField.FAVORIT_TABLE+"."+DatabaseField.FAVORIT_USER+" = "+
                idUser;
        Cursor cursor = dbInfo.getFromQuery(query);
        ArrayList arrayList = new ArrayList();

        while(cursor.isAfterLast() == false){
            arrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_NAMA)));
            cursor.moveToNext();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        setContentView(R.layout.daftar_resep_favorit);
        ListView obj = (ListView) findViewById(R.id.listview_resep);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                // TODO Id
                Cursor cursor = dbInfo.getFromQuery(query);
                for (int ii=0;ii<position;++ii) cursor.moveToNext();
                bundle.putInt("id", cursor.getInt(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_ID)));
                Intent intent = new Intent(getApplicationContext(), DisplayResep.class);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
