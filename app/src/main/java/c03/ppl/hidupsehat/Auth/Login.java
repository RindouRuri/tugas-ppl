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
import android.widget.TextView;

import c03.ppl.hidupsehat.MainActivity;
import c03.ppl.hidupsehat.Profile.UpdateBerat;
import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 04/04/15.
 */
public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText inputName = (EditText) findViewById(R.id.username);
        final EditText inputPassword = (EditText) findViewById(R.id.password);
        Button inputLogin = (Button) findViewById(R.id.submit);
        Button inputSignUp = (Button) findViewById(R.id.signup);
        final TextView msgLabel = (TextView) findViewById(R.id.msg);

        // if there any msg
        Intent intent = getIntent();
        String msg = intent.getStringExtra("msg");
        if (msg != null && !msg.trim().isEmpty())
            msgLabel.setText(msg);

        inputSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), SignUp.class);
                signUpIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.e(Login.class.getName(), "Opening Sign Up Form!");
                startActivity(signUpIntent);
                finish();
            }
        });
        inputLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = inputName.getText().toString();
                String password = inputPassword.getText().toString();
                boolean login = doLogin(username, password);
                Log.e(Login.class.getName(), username + " try login and " + ((login) ? "Success" : "Failed"));

                if (!login){
                    msgLabel.setText("Username atau Password salah!");
                } else {
                    doRememberLogin(username);
                    Intent intent = new Intent(getApplicationContext(), UpdateBerat.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void doRememberLogin(String username) {
        ContentValues values = new ContentValues();
        values.put(DatabaseField.USER_COLUMN_IS_LOGIN, 1);
        DatabaseInfo dbInfo = new DatabaseInfo(this);
        dbInfo.update(DatabaseField.USER_TABLE, values, DatabaseField.USER_COLUMN_USERNAME+"=?", new String[] {username});
        dbInfo.close();
        return;
    }

    boolean doLogin(String username, String password){
        DatabaseInfo dbInfo = new DatabaseInfo(this);

        String query = "select * from " + DatabaseField.USER_TABLE + " where username = '" + username
                + "' and password = '" + password + "'";
        Cursor cur = dbInfo.getFromQuery(query);
        boolean ret = cur.isAfterLast() == false;
        cur.close();
        dbInfo.close();
        return ret;
    }
}
