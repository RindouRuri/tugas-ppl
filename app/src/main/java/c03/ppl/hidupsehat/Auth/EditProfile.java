/*package c03.ppl.hidupsehat.Auth;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import c03.ppl.hidupsehat.MainActivity;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.Tools.Misc;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;


 // Created by wahyuoi on 09/04/15.
 
public class EditProfile extends Activity {
    private TextView inputUsername;
    private EditText inputPassword;
    private EditText inputNama;
    private EditText inputUmur;
    private EditText inputTinggi;
    private ImageButton buttonSave;
    private EditText inputBerat;
    private ImageButton buttonCancel;
    private TextView statusLabel;
    private String oldPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        inputUsername = (TextView) findViewById(R.id.username);
        inputPassword = (EditText) findViewById(R.id.password);
        inputNama = (EditText) findViewById(R.id.nama);
        inputTinggi = (EditText) findViewById(R.id.height);
        inputBerat = (EditText) findViewById(R.id.weight);
        buttonSave = (ImageButton) findViewById(R.id.submit);
        buttonCancel = (ImageButton) findViewById(R.id.cancel);

        setFieldValue();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                String nama = inputNama.getText().toString();
                String tinggi = inputTinggi.getText().toString();
                String berat = inputBerat.getText().toString();
                String umur = inputUmur.getText().toString();

                if (validInput(username, password, nama, tinggi, berat, umur)) {
                    doUpdate(username, password, nama, tinggi, berat, umur);
                    Intent editProfile = new Intent(getApplicationContext(), MainActivity.class);
                    editProfile.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    editProfile.putExtra("msg", "Update berhasil!");
                    Log.e(SignUp.class.getName(), "Update success");
                    startActivity(editProfile);
                    finish();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setFieldValue() {
        DatabaseInfo dbInfo = new DatabaseInfo(this);
        String query = "Select * from " + DatabaseField.USER_TABLE + " where " +
                DatabaseField.USER_COLUMN_IS_LOGIN + " = '1'";
        Cursor cur = dbInfo.getFromQuery(query);
        inputUsername.setText(cur.getString(cur.getColumnIndex(DatabaseField.USER_COLUMN_USERNAME)));
        oldPassword = cur.getString(cur.getColumnIndex(DatabaseField.USER_COLUMN_PASSWORD));
        inputPassword.setHint("Change if you want!");
        inputUmur.setText(cur.getString(cur.getColumnIndex(DatabaseField.USER_COLUMN_UMUR)));
        inputNama.setText(cur.getString(cur.getColumnIndex(DatabaseField.USER_COLUMN_NAMA)));
        inputTinggi.setText(cur.getString(cur.getColumnIndex(DatabaseField.USER_COLUMN_TINGGI)));
        inputBerat.setText(cur.getString(cur.getColumnIndex(DatabaseField.USER_COLUMN_BERAT)));

        cur.close();
        dbInfo.close();
    }

    private boolean validInput(String username, String password, String nama, String tinggi, String berat, String umur) {
        if (username == null || username.trim().isEmpty() || (username.split(" ").length > 1))
            statusLabel.setText("Username tidak boleh kosong");
        else if (nama == null || nama.trim().isEmpty())
            statusLabel.setText("Nama tidak boleh kosong");
        else if (umur == null || umur.trim().isEmpty())
            statusLabel.setText("Umur tidak boleh kosong");
        else if (berat == null || berat.trim().isEmpty())
            statusLabel.setText("Berat badan tidak boleh kosong");
        else if (tinggi == null || tinggi.trim().isEmpty())
            statusLabel.setText("Tinggi badan tidak boleh kosong");
        else if (!Misc.isPositiveNumeric(berat))
            statusLabel.setText("Berat badan harus bulat positif");
        else if (!Misc.isPositiveNumeric(tinggi))
            statusLabel.setText("Tinggi badan harus bulat positif");
        else
            return true;
        return false;
    }

    private boolean doUpdate(String username, String password, String nama, String tinggi, String berat, String umur) {
        DatabaseInfo dbInfo = new DatabaseInfo(this);
        ContentValues values = new ContentValues();
        values.put(DatabaseField.USER_COLUMN_USERNAME, username);
        if (password == null || password.trim().isEmpty())
                password = oldPassword;
        values.put(DatabaseField.USER_COLUMN_PASSWORD, password);
        values.put(DatabaseField.USER_COLUMN_NAMA, nama);
        values.put(DatabaseField.USER_COLUMN_TINGGI, tinggi);
        values.put(DatabaseField.USER_COLUMN_BERAT, berat);
        values.put(DatabaseField.USER_COLUMN_IS_LOGIN, 1);
        values.put(DatabaseField.USER_COLUMN_UMUR, umur);
        boolean ret = dbInfo.update(DatabaseField.USER_TABLE, values,
                DatabaseField.USER_COLUMN_USERNAME+"=?", new String[] {username});
        dbInfo.close();
        return ret;
    }
}
*/




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

                //EditText field2 = (EditText) findViewById(R.id.username);
                //String username = field2.getText().toString();

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

                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                Log.e(EditProfile.class.getName(), "Profile Tersimpan");
                startActivity(intent);
                dbInfo.close();
                finish();

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
