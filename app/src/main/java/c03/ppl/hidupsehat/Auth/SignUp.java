package c03.ppl.hidupsehat.Auth;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.Tools.Misc;
import c03.ppl.hidupsehat.Tools.Sync;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 08/04/15.
 */
public class SignUp extends Activity {
    TextView statusLabel;

    public SignUp() {
        statusLabel = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        final EditText inputUsername = (EditText) findViewById(R.id.username);
        final EditText inputPassword = (EditText) findViewById(R.id.password);
        final EditText inputNama = (EditText) findViewById(R.id.nama);
        final EditText inputTinggi = (EditText) findViewById(R.id.tinggi);
        final EditText inputBerat = (EditText) findViewById(R.id.berat);
        final RadioGroup inputSex = (RadioGroup) findViewById(R.id.radioGroup);
        ImageButton buttonSignUp = (ImageButton) findViewById(R.id.signup);
        ImageButton buttonCancel = (ImageButton) findViewById(R.id.cancel);

        statusLabel = (TextView) findViewById(R.id.status);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputUsername.getText().toString();
                if (username == null || username.trim().isEmpty())
                    inputUsername.setError("Required");
                String password = inputPassword.getText().toString();
                if (password == null || password.trim().isEmpty())
                    inputPassword.setError("Required");
                String nama = inputNama.getText().toString();
                if (nama == null || nama.trim().isEmpty())
                    inputNama.setError("Required");
                String tinggi = inputTinggi.getText().toString();
                if (tinggi== null || tinggi.trim().isEmpty())
                    inputTinggi.setError("Required");
                String berat = inputBerat.getText().toString();
                if (berat == null || berat.trim().isEmpty())
                    inputBerat.setError("Required");
                String kelamin = null;
                int kelaminId = inputSex.getCheckedRadioButtonId();
                if (kelaminId != -1) {
                    RadioButton kelaminButton = (RadioButton) findViewById(kelaminId);
                    kelamin = kelaminButton.getText().toString();
                    if (kelamin.equalsIgnoreCase("M"))
                        kelamin = "L";
                    else
                        kelamin = "P";
                }

                if (validInput(username, password, nama, tinggi, berat, kelamin))
                    if (!isRegistered(username)) {
                        doRegister(username, password, nama, tinggi, berat, kelamin);
                        Intent login = new Intent(getApplicationContext(), Login.class);
                        login.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        login.putExtra("msg", "Pendaftaran berhasil!");
                        Log.e(SignUp.class.getName(), "Register success");
                        startActivity(login);
                        finish();
                    } else {
                        statusLabel.setText("username sudah terdaftar!");
                    }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), Login.class);
                Log.e(SignUp.class.getName(), "Move to login screen");
                startActivity(login);
                finish();
            }
        });

    }

    private boolean validInput(String username, String password, String nama, String tinggi, String berat, String kelamin) {
        if (username == null || username.trim().isEmpty() || (username.split(" ").length > 1))
            statusLabel.setText("Username tidak boleh kosong");
        else if (password == null || password.trim().isEmpty())
            statusLabel.setText("Password tidak boleh kosong");
        else if (nama == null || nama.trim().isEmpty())
            statusLabel.setText("Nama tidak boleh kosong");
        else if (berat == null || berat.trim().isEmpty())
            statusLabel.setText("Berat badan tidak boleh kosong");
        else if (kelamin == null || kelamin.trim().isEmpty())
            statusLabel.setText("Kelamin harus dipilih");
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

    private boolean isRegistered(String username) {
        DatabaseInfo dbInfo = new DatabaseInfo(this);
        String query = "select * from " + DatabaseField.USER_TABLE + " where " + DatabaseField.USER_COLUMN_USERNAME + " = '" + username + "'";
        Cursor cur = dbInfo.getFromQuery(query);
        boolean ret = cur.isAfterLast() == false;
        cur.close();
        dbInfo.close();
        return ret;
    }

    private boolean doRegister(String username, String password, String nama, String tinggi, String berat, String kelamin) {
        DatabaseInfo dbInfo = new DatabaseInfo(this);
        ContentValues values = new ContentValues();
        values.put(DatabaseField.USER_COLUMN_ID, System.currentTimeMillis() % 1000007);
        values.put(DatabaseField.USER_COLUMN_USERNAME, username);
        values.put(DatabaseField.USER_COLUMN_PASSWORD, password);
        values.put(DatabaseField.USER_COLUMN_NAMA, nama);
        values.put(DatabaseField.USER_COLUMN_TINGGI, tinggi);
        values.put(DatabaseField.USER_COLUMN_BERAT, berat);
        values.put(DatabaseField.USER_COLUMN_KELAMIN, kelamin);
        values.put(DatabaseField.USER_COLUMN_IS_LOGIN, 0);
        boolean ret = dbInfo.insert(DatabaseField.USER_TABLE, values);
        (new Sync()).registerUser(values);
        dbInfo.close();
        return ret;
    }
}
