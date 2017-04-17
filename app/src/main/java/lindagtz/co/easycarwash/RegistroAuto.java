package lindagtz.co.easycarwash;

import android.content.Intent;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.identity.intents.Address;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.Barcode.GeoPoint;
import com.google.android.gms.maps.Projection;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class RegistroAuto extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_auto);
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

        // Add a marker in Sydney and move the camera
        LatLng tecamac = new LatLng(19.709338, -98.966541);
        mMap.addMarker(new MarkerOptions().position(tecamac).title("Tecámac de Felipe Villanueva"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tecamac));
        goToLocationZoom(19.709338, -98.966541, 15);

    }
    private void goToLocation(double lat, double lng) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
        mMap.moveCamera(update);
    }

    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);



    }

    public void onSearch(View view) throws IOException{
        System.out.println("si click");

        EditText slocation= (EditText) findViewById(R.id.txtLocation);
        //Edittext para registrar en la BD
        final EditText usernamee= (EditText) findViewById(R.id.txtuserA);
        final EditText emaill= (EditText) findViewById(R.id.txtemailA);
        final EditText passwordd= (EditText) findViewById(R.id.txtpassA);
        final EditText direccionn= (EditText) findViewById(R.id.txtDirecA);
        final EditText longitudee=(EditText) findViewById(R.id.txtlong);
        final EditText latitude= (EditText) findViewById(R.id.txtLat);
        final EditText phonee=(EditText) findViewById(R.id.txtphoneA);
        final EditText firstnamee=(EditText) findViewById(R.id.txtfirstnameA);
        final EditText lastnamee=(EditText) findViewById(R.id.txtfirstnameA);
        Button btnRegisAuto=(Button) findViewById(R.id.btnRegisAuto);

        //para el registro de datos de autolavados
        btnRegisAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernamee.getText().toString();
                final String email = emaill.getText().toString();
                final String password = passwordd.getText().toString();
                final String direccion = direccionn.getText().toString();
                final double longitud = Double.parseDouble(longitudee.getText().toString());
                final double latitud = Double.parseDouble(latitude.getText().toString());
                final int telefono = Integer.parseInt(phonee.getText().toString());
                final String firstname = firstnamee.getText().toString();
                final String lastname = lastnamee.getText().toString();
                final String gender = lastnamee.getText().toString();
                final int auth_id = 3;

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String result=null;
                            Log.i("tagconvertstr", "["+result+"]");

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(RegistroAuto.this, Login.class);
                                RegistroAuto.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegistroAuto.this);
                                builder.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(username, email, password, direccion, longitud, latitud, telefono,
                        firstname, lastname, gender, auth_id, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegistroAuto.this);
                queue.add(registerRequest);
            }

        });



        String location=slocation.getText().toString();

        Geocoder gc = new Geocoder(this);
        List<android.location.Address> list = gc.getFromLocationName(location, 1);
        android.location.Address address = list.get(0);
        String locality = address.getLocality();
        LatLng latLng=new LatLng(address.getLatitude(), address.getLongitude());

        Toast.makeText(this, locality, Toast.LENGTH_LONG).show();
        double lat = address.getLatitude();
        double lng = address.getLongitude();
        String lati=String.valueOf(lat);
        String longi=String.valueOf(lng);


        goToLocationZoom(lat, lng, 15);
        mMap.addMarker(new MarkerOptions().position(latLng).title("Yeii"));
        System.out.print(lati+' '+longi);
        latitude.setText(lati);
        longitudee.setText(longi);

    }

    public boolean onTouchEvent(MotionEvent event, GoogleMap googleMap){
            if(event.getAction()==1){



            }
    //cuando toque el mapa

        return false;
    }
    }





