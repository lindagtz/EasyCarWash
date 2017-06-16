package lindagtz.co.easycarwash;

import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class RegistroAuto extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    EditText username, email, password, direccion, longitud, latitud, phone, lastname, firstname;
    Button btnRegisAuto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_auto);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Edittext para registrar en la BD
        username = (EditText) findViewById(R.id.txtuserA);
        email = (EditText) findViewById(R.id.txtemailA);
        password = (EditText) findViewById(R.id.txtpassA);
        longitud = (EditText) findViewById(R.id.txtlong);
        latitud = (EditText) findViewById(R.id.txtLat);
        direccion = (EditText) findViewById(R.id.txtDirecA);
        phone = (EditText) findViewById(R.id.txtphoneA);
        firstname = (EditText) findViewById(R.id.txtfirstnameA);
        lastname = (EditText) findViewById(R.id.txtfirstnameA);
        btnRegisAuto = (Button) findViewById(R.id.btnRegisAuto);

        btnRegisAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new CargarDatos().execute("http://easycarwash.hol.es/registro.php?nombre=" + username.getText().toString() + "&apellido=" + direccion.getText().toString());

            }
        });
    }



   /* btnRegisAuto.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            new ConsultarDatos().execute("http://10.0.3.2/CursoAndroid/consulta.php?id="+etId.getText().toString());

        }
    });

*/






private class CargarDatos extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        Toast.makeText(getApplicationContext(), "Se almacenaron los datos correctamente", Toast.LENGTH_LONG).show();

    }
}


private class ConsultarDatos extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urls) {

        // params comes from the execute() call: params[0] is the url.
        try {
            return downloadUrl(urls[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }
    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        JSONArray ja = null;
        try {
            ja = new JSONArray(result);
           // username.setText(ja.getString(1));
            //etTelefono.setText(ja.getString(2));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 500;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("respuesta", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
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


        EditText slocation= (EditText) findViewById(R.id.txtLocation);


                String location = slocation.getText().toString();

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
        latitud.setText(lati);
        longitud.setText(longi);

    }

    public boolean onTouchEvent(MotionEvent event, GoogleMap googleMap){
            if(event.getAction()==1){



            }
    //cuando toque el mapa

        return false;
    }
    }





