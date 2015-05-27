package c03.ppl.hidupsehat.Makanan.Request;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import c03.ppl.hidupsehat.MainActivity;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.Tools.Sync;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 23/05/15.
 */
public class RequestResep extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_resep);

        ImageButton submit = (ImageButton) findViewById(R.id.submit);
        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Log.e(MainActivity.class.getName(), "Berhasil Mensubmit Request Resep Makanan");
                startActivity(intent);
                finish();
            }
        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Log.e(MainActivity.class.getName(), "Gagal Mensubmit Request Resep Makanan");
                startActivity(intent);
                finish();
            }
        });

    }
}