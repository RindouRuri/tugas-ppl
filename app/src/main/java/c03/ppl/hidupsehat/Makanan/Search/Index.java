package c03.ppl.hidupsehat.Makanan.Search;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import c03.ppl.hidupsehat.Makanan.Resep.DisplayResep;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.Tools.Sync;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

import static android.R.layout.simple_list_item_1;

/**
 * Created by wahyuoi on 20/05/15.
 */
public class Index extends Activity {
    String query;
    DatabaseInfo dbInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mencari_resep);
        final EditText text = (EditText) findViewById(R.id.search_bar);
        ImageButton search = (ImageButton) findViewById(R.id.search_button);
        dbInfo = new DatabaseInfo(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = text.getText().toString().trim();
                query = "Select * from " + DatabaseField.RESEP_MAKANAN_TABLE + " where " +
                        DatabaseField.RESEP_MAKANAN_NAMA + " like '%" + key + "%'";
                Cursor cursor = dbInfo.getFromQuery(query);

                System.err.println(query);

                ArrayList arrayList = new ArrayList();
                while(cursor.isAfterLast() == false){
                    arrayList.add(cursor.getString(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_NAMA)));
                    cursor.moveToNext();
                }
                setResult(arrayList);
            }
        });
    }
    public void setResult(ArrayList arrayList){
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        ListView obj = (ListView) findViewById(R.id.listview_resep);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                // TODO ID
                System.out.println(id);
                Cursor cursor = dbInfo.getFromQuery(query);
                for (int ii=0;ii<position;++ii) cursor.moveToNext();
                bundle.putInt("id", cursor.getInt(cursor.getColumnIndex(DatabaseField.RESEP_MAKANAN_ID)));
                Intent intent = new Intent(getApplicationContext(), DisplayResep.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
