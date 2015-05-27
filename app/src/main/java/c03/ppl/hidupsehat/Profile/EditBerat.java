package c03.ppl.hidupsehat.Profile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import c03.ppl.hidupsehat.MainActivity;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.Tools.Sync;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 23/05/15.
 */
public class EditBerat extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simpan_berat_badan);

        final DatabaseInfo dbInfo = new DatabaseInfo(this);
        final int id = dbInfo.getIdLogin(DatabaseField.USER_TABLE, DatabaseField.USER_COLUMN_IS_LOGIN);
        System.out.println(id+" login");
        Cursor cursor = dbInfo.getFromQuery("select " + DatabaseField.USER_COLUMN_BERAT + " from " + DatabaseField.USER_TABLE + " where id = '" + id + "'");
        int berat = cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_BERAT));

        EditText field = (EditText) findViewById(R.id.berat);
        field.setText(String.valueOf(berat));

        ImageButton submit = (ImageButton) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // simpan ke db local
                EditText field = (EditText) findViewById(R.id.berat);
                int berat = Integer.parseInt(field.getText().toString());
                Cursor cursor = dbInfo.getFromQuery("Select * from "+DatabaseField.USER_TABLE+" where id = '"+id+"'");
                ContentValues values = new ContentValues();
                values.put(DatabaseField.USER_COLUMN_ID, cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_ID)));
                values.put(DatabaseField.USER_COLUMN_USERNAME, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_USERNAME)));
                values.put(DatabaseField.USER_COLUMN_PASSWORD, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_PASSWORD)));
                values.put(DatabaseField.USER_COLUMN_NAMA, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_NAMA)));
                values.put(DatabaseField.USER_COLUMN_TINGGI, cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_TINGGI)));
                values.put(DatabaseField.USER_COLUMN_BERAT, berat);
                values.put(DatabaseField.USER_COLUMN_KELAMIN, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_KELAMIN)));
                values.put(DatabaseField.USER_COLUMN_IS_LOGIN, cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_IS_LOGIN)));
                dbInfo.update(DatabaseField.USER_TABLE, values, "id=?", new String[] {String.valueOf(id)});
                // sync ke atas
                (new Sync()).registerUser(values);
                // redirect
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Log.e(EditBerat.class.getName(), "Berat tersimpan, lanjur ke menu");
                startActivity(intent);
                finish();
            }
        });
    }
}
