package com.example.gpsik;

import java.util.Date;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

public class MainActivity extends Activity {
    private TextView tvProvider;
    private TextView tvLongitude;
    private TextView tvLatitude;

    private LocationManager locationManager;
    private Location savedLocation = null;
    private LocationListener locationListener = new LocationListener() {
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }

        public void onLocationChanged(Location location) {
            showLocation(location);
            if (savedLocation == null)
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }savedLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvProvider = (TextView) findViewById(R.id.tvProvider);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            tvProvider.setText("GPS");
        else
            tvProvider.setText("GPS Disabled. Please, turn it on");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000, 2, locationListener);
    }

    @Override
    protected void onStop() {
        locationManager.removeUpdates(locationListener);
        super.onStop();
    }

    private void showLocation(Location location) {
        String latitude = "Latitude: ";
        String longitude = "Longitude: ";
        if (location != null) {
            latitude += location.getLatitude();
            longitude += location.getLongitude();
            tvLatitude.setText(latitude);
            tvLongitude.setText(longitude);
        }
    }
}