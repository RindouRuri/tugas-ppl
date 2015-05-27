package c03.ppl.hidupsehat.Menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import c03.ppl.hidupsehat.R;
import c03.ppl.hidupsehat.olahraga.HitungJarak;

/**
 * Created by Ruri_2 on 2015/05/27.
 */
public class OlahragaMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.olahraga);

        ImageButton sepeda = (ImageButton) findViewById(R.id.bersepeda);
        ImageButton lari = (ImageButton) findViewById(R.id.berlari);

        sepeda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sepeda = new Intent(getApplicationContext(), HitungJarak.class);
                Log.e(HitungJarak.class.getName(), "Start GPS Tracking");
                startActivity(sepeda);
            }
        });

        lari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lari = new Intent(getApplicationContext(), HitungJarak.class);
                Log.e(HitungJarak.class.getName(), "Start GPS Tracking");
                startActivity(lari);
            }
        });
    }
}