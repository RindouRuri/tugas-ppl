package c03.ppl.hidupsehat.Makanan.Resep;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;

import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 18/05/15.
 */
public class Index extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseInfo dbInfo = new DatabaseInfo(this);
        Cursor cursor = dbInfo.getFromQuery("select * from " + DatabaseField.RESEP_MAKANAN_TABLE);

        ArrayList arrayList = new ArrayList();

        while(cursor.isAfterLast() == false){
            arrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_NAMA)));
            cursor.moveToNext();
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        setContentView(R.layout.daftar_resep);
        ListView obj = (ListView) findViewById(R.id.listview_resep);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                // TODO id
                System.out.println(id);
                bundle.putInt("id", position+1);
                Intent intent = new Intent(getApplicationContext(), DisplayResep.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
