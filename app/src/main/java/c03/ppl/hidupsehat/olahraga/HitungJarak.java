package c03.ppl.hidupsehat.olahraga;

/**
 * Created by Karel on 2015/05/22.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import c03.ppl.hidupsehat.R;

public class HitungJarak extends FragmentActivity implements LocationListener
{
    GoogleMap googleMap;
    double lati;
    double longi;
    int distance;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mulai_olahraga);

        addButtonClickListener();

        // Getting Google Play availability status
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        // Showing status
        // Google Play Services are not available
        if(status!= ConnectionResult.SUCCESS)
        {

            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();

        }
        // Google Play Services are available
        else
        {

            // Getting reference to the SupportMapFragment of activity_main.xml
            SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

            // Getting GoogleMap object from the fragment
            googleMap = fm.getMap();

            // Enabling MyLocation Layer of Google Map
            googleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if(location!=null) onLocationChanged(location);

            locationManager.requestLocationUpdates(provider, 1000, 1, this);
        }
    }

    public void addButtonClickListener()
    {
        Button button = (Button) findViewById(R.id.start);
        Button button2 = (Button) findViewById(R.id.stop);

        button.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                Toast.makeText(HitungJarak.this, "Start app",	Toast.LENGTH_SHORT).show();
                //check = true;
            }

        });

        button2.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View view)
            {
                int kalori = (int) Math.floor((3.3 * distance * 50) / 4.8);

                SharedPreferences sp = getSharedPreferences("kalori", Activity.MODE_PRIVATE);
                int kaloriTersimpan = sp.getInt("kalori-tersimpan", -1);
                int temp = Math.max(kalori, kaloriTersimpan);

                Toast.makeText(HitungJarak.this, "Selesai olahraga, total kalori : " + temp, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("kalori-tersimpan", temp);
                editor.commit();

                //check = false;
            }

        });
    }

    @Override
    public void onLocationChanged(Location location)
    {

        TextView tvLocation = (TextView) findViewById(R.id.tv_location);

        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        if (check)
        {
            Location temp = new Location("Previous spot");
            temp.setLongitude(longi);
            temp.setLatitude(lati);
            distance += (int) Math.round(temp.distanceTo(location));
            //Toast.makeText(HitungJarak.this, "Tambah jarak: " + distance, Toast.LENGTH_SHORT).show();
        }
        else
        {
            distance = 0;
            //Toast.makeText(HitungJarak.this, "Pengecekan berhenti",	Toast.LENGTH_SHORT).show();
        }

        lati = latitude;
        longi = longitude;

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

        // Setting latitude and longitude in the TextView tv_location
       tvLocation.setText("Lat: " +  latitude  + " | Long: "+ longitude + " | Dist: " + distance);
        //tvLocation.setText("Uji Coba GPS");
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        // TODO Auto-generated method stub
        Toast.makeText(HitungJarak.this, "GPS mati" ,	Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        // TODO Auto-generated method stub
        Toast.makeText(HitungJarak.this, "GPS menyala" ,	Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
}