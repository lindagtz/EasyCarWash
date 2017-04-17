package lindagtz.co.easycarwash;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;

public class Maps extends ActionBarActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker marcador;
    double lat= 0.0;
    double lng=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Marker marker;

        if(mMap != null){
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {



                        View v= getLayoutInflater().inflate(R.layout.activity_infowindow, null);
                        TextView tv1=(TextView) v.findViewById(R.id.tv_locality);
                        TextView tv2=(TextView) v.findViewById(R.id.tv_lat);
                        TextView tv3=(TextView) v.findViewById(R.id.tv_lng);
                        TextView tv4=(TextView) v.findViewById(R.id.tv_snippet);

                        tv1.setText(marker.getTitle());
                        tv4.setText(marker.getSnippet());


                    return v;


                }
            });
        }

        // Add a marker in a place and move the camera
        LatLng car = new LatLng(19.673098, -99.015353);
        LatLng car2 = new LatLng(19.630964, -99.031613);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        goToLocationZoom(19.709338, -98.966541, 10);



        MarkerOptions options= new MarkerOptions()
                .position(car)
                .title("hii")
                .snippet("El carwash feliz\nhello\nhola\nyouju")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(car));
                marker= mMap.addMarker(options);



        //segundo autolavado
        mMap.addMarker(new MarkerOptions()
                .position(car2)
                .title("Carwash 2")
                .snippet("El carwash feliz 2")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(car2));


        // Assume thisActivity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(Maps.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==0){
            mMap.setMyLocationEnabled(true);

        }




    }
    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);



    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapTypeNone:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case R.id.mapTypeNormal:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case R.id.mapTypeSatellite:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.mapTypeTerrain:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case R.id.mapTypeHybrid:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(this, InfoCarWash.class);
        startActivity(i);


    }
}
