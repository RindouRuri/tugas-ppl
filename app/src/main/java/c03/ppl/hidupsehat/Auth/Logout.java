package c03.ppl.hidupsehat.Auth;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import c03.ppl.hidupsehat.MainActivity;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 09/04/15.
 */
public class Logout implements View.OnClickListener {
    private final Activity activity;

    public Logout(Activity mainActivity) {
        this.activity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        Intent login = new Intent(activity.getApplicationContext(), Login.class);
        doForgetLogin();
        Log.e(Logout.class.getName(), "User logout");
        activity.startActivity(login);
        activity.finish();
    }

    private void doForgetLogin() {
        ContentValues values = new ContentValues();
        values.put(DatabaseField.USER_COLUMN_IS_LOGIN, 0);
        DatabaseInfo dbInfo = new DatabaseInfo(activity);
        dbInfo.update(DatabaseField.USER_TABLE, values, null, null);
        dbInfo.close();
        return;
    }
}
