package c03.ppl.hidupsehat;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import c03.ppl.hidupsehat.Auth.EditProfile;
import c03.ppl.hidupsehat.Auth.Login;
import c03.ppl.hidupsehat.Auth.Logout;
import c03.ppl.hidupsehat.Menu.AchievementMenu;
import c03.ppl.hidupsehat.Menu.OlahragaMenu;
import c03.ppl.hidupsehat.Menu.ResepMakananMenu;
import c03.ppl.hidupsehat.Tools.Sync;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;


public class MainActivity extends ActionBarActivity {

    private ListView obj;
    private DatabaseInfo dbInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);
        ImageButton buttonOlahraga = (ImageButton) findViewById(R.id.olahraga);
        ImageButton buttonLogout = (ImageButton) findViewById(R.id.logout);
        ImageButton buttonEditProfile = (ImageButton) findViewById(R.id.profil);
        ImageButton buttonResepMakanan = (ImageButton) findViewById(R.id.resepMakananSehat);
        ImageButton buttonAchievement = (ImageButton) findViewById(R.id.achievement);

        buttonLogout.setOnClickListener(new Logout(this));
        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfile = new Intent(getApplicationContext(), EditProfile.class);
                Log.e(MainActivity.class.getName(), "Move to Edit Profile");
                startActivity(editProfile);
            }
        });
        buttonOlahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuOlahraga = new Intent(getApplicationContext(), OlahragaMenu.class);
                Log.e(OlahragaMenu.class.getName(), "Start GPS Tracking");
                startActivity(menuOlahraga);
            }
        });buttonResepMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resepMakanan = new Intent(getApplicationContext(), ResepMakananMenu.class);
                Log.e(ResepMakananMenu.class.getName(), "Move to Resep Makanan Sehat");
                startActivity(resepMakanan);
            }
        });
        buttonAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AchievementMenu.class);
                Log.e(AchievementMenu.class.getName(), "Move to Achievement");
                startActivity(intent);
            }
        });
        dbInfo = new DatabaseInfo(this);

        if (!dbInfo.isLogin(DatabaseField.USER_TABLE, DatabaseField.USER_COLUMN_IS_LOGIN)){
            // Sycn user
            Sync sync = new Sync();
            sync.fetch(this);

            Intent loginScreen = new Intent(getApplicationContext(), Login.class);
            loginScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.e(MainActivity.class.getName(), "redirect to login screen!");
            startActivity(loginScreen);
            dbInfo.close();
            finish();
        }

        // user udah login

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}
