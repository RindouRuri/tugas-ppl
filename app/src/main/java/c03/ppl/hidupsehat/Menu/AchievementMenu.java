package c03.ppl.hidupsehat.Menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import c03.ppl.hidupsehat.Achievement.Kalori;
import c03.ppl.hidupsehat.Achievement.Olahraga;
import c03.ppl.hidupsehat.R;

/**
 * Created by wahyuoi on 20/05/15.
 */
public class AchievementMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement);

        ImageButton olahraga = (ImageButton) findViewById(R.id.olahraga);
        ImageButton kalori = (ImageButton) findViewById(R.id.kalori);

        olahraga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Olahraga.class);
                Log.e(Olahraga.class.getName(), "Ke Menu Achievement Olahraga");
                startActivity(intent);
                finish();
            }
        });

        kalori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Kalori.class);
                Log.e(Kalori.class.getName(), "Ke Menu Achievement Kalori");
                startActivity(intent);
                finish();
            }
        });

    }
}
