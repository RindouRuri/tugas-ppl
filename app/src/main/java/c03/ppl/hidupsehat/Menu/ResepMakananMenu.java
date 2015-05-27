package c03.ppl.hidupsehat.Menu;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import c03.ppl.hidupsehat.Auth.Logout;
import c03.ppl.hidupsehat.Makanan.Request.RequestResep;
import c03.ppl.hidupsehat.Makanan.Resep.Index;
import c03.ppl.hidupsehat.R;

/**
 * Created by wahyuoi on 18/05/15.
 */
public class ResepMakananMenu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resep_makanan_sehat);

        ImageButton daftarResep = (ImageButton) findViewById(R.id.daftarresep);
        ImageButton pencarian = (ImageButton) findViewById(R.id.mencariresep);
        ImageButton favorit = (ImageButton) findViewById(R.id.resepfavorit);
        ImageButton request = (ImageButton) findViewById(R.id.requestresep);

        daftarResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(getApplicationContext(), Index.class);
                Log.e(Index.class.getName(), "Move To Daftar Resep");
                startActivity(daftar);

            }
        });

        pencarian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), c03.ppl.hidupsehat.Makanan.Search.Index.class);
                Log.e(c03.ppl.hidupsehat.Makanan.Search.Index.class.getName(), "Move to Pencarian Resep");
                startActivity(intent);

            }
        });

        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), c03.ppl.hidupsehat.Makanan.Favorit.Index.class);
                Log.e(c03.ppl.hidupsehat.Makanan.Favorit.Index.class.getName(), "Move to Daftar Resep Favorit");
                startActivity(intent);


            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RequestResep.class);
                Log.e(RequestResep.class.getName(), "Move to Form Request Resep");
                startActivity(intent);
                finish();
            }
        });
    }
}
