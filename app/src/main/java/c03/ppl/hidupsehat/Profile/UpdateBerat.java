package c03.ppl.hidupsehat.Profile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.database.DatabaseField;
import c03.ppl.hidupsehat.database.DatabaseInfo;

/**
 * Created by wahyuoi on 23/05/15.
 */
public class UpdateBerat extends Activity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_berat_badan);

        ImageButton next = (ImageButton) findViewById(R.id.update);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditBerat.class);
                Log.e(UpdateBerat.class.getName(), "Munculkan form edit berat badan");
                startActivity(intent);
                finish();
            }
        });

    }
}
