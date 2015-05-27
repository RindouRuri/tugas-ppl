package c03.ppl.hidupsehat.Auth;

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
public class EditProfile extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);



        final DatabaseInfo dbInfo = new DatabaseInfo(this);
        final int id = dbInfo.getIdLogin(DatabaseField.USER_TABLE, DatabaseField.USER_COLUMN_IS_LOGIN);
        Cursor cursor = dbInfo.getFromQuery("select * from " + DatabaseField.USER_TABLE + " where id = '" + id + "'");

        int berat = cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_BERAT));
        int tinggi = cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_TINGGI));
        String username = cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_USERNAME));
        String password = cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_PASSWORD));


        EditText field = (EditText) findViewById(R.id.weight);
        field.setText(String.valueOf(berat));

        TextView field2 = (TextView) findViewById(R.id.username);
        field2.setText(String.valueOf(username));

        EditText field3 = (EditText) findViewById(R.id.password);
        field3.setText(String.valueOf(password));

        EditText field4 = (EditText) findViewById(R.id.height);
        field4.setText(String.valueOf(tinggi));

        ImageButton submit = (ImageButton) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // simpan ke db local
                EditText field = (EditText) findViewById(R.id.weight);
                int berat = Integer.parseInt(field.getText().toString());



                EditText field3 = (EditText) findViewById(R.id.password);
                String password = field3.getText().toString();

                EditText field4 = (EditText) findViewById(R.id.height);
                int tinggi = Integer.parseInt(field4.getText().toString());


                Cursor cursor = dbInfo.getFromQuery("Select * from "+DatabaseField.USER_TABLE+" where id = '"+id+"'");
                ContentValues values = new ContentValues();
                values.put(DatabaseField.USER_COLUMN_ID, cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_ID)));
                values.put(DatabaseField.USER_COLUMN_USERNAME, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_USERNAME)));
                values.put(DatabaseField.USER_COLUMN_PASSWORD, password);
                values.put(DatabaseField.USER_COLUMN_NAMA, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_NAMA)));
                values.put(DatabaseField.USER_COLUMN_TINGGI, tinggi);
                values.put(DatabaseField.USER_COLUMN_BERAT, berat);
                values.put(DatabaseField.USER_COLUMN_KELAMIN, cursor.getString(cursor.getColumnIndex(DatabaseField.USER_COLUMN_KELAMIN)));
                values.put(DatabaseField.USER_COLUMN_IS_LOGIN, cursor.getInt(cursor.getColumnIndex(DatabaseField.USER_COLUMN_IS_LOGIN)));
                dbInfo.update(DatabaseField.USER_TABLE, values, "id=?", new String[]{String.valueOf(id)});
                // sync ke atas
                (new Sync()).registerUser(values);
                // redirect

                // TODO direct kemana nih harusnnya?

               /* Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                Log.e(EditProfile.class.getName(), "Profile Tersimpan");
                startActivity(intent);
                dbInfo.close();
                finish();
              */

                //Revisi v2.0
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Log.e(MainActivity.class.getName(), "Profile Tersimpan");
                startActivity(intent);
                dbInfo.close();
                finish();

                //Revisi v2.0
            }
        });
        ImageButton cancel = (ImageButton) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Log.e(MainActivity.class.getName(), "Profile Batal Tersimpan");
                startActivity(intent);
                finish();
            }
        });

    }
}