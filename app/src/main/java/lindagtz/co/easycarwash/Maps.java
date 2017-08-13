package lindagtz.co.easycarwash;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Maps extends ActionBarActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker marcador;
    double lat= 0.0;
    double lng=0.0;
    Button consulta;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        consulta=(Button)findViewById(R.id.btnMaps);
        info=(TextView)findViewById(R.id.txtInfo);


     consulta.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             new ConsultarDatos().execute("http://easycarwash.hol.es/consulta.php");
         }
     });

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

//Ventana de informacion de autolavados

                        View v= getLayoutInflater().inflate(R.layout.activity_infowindow, null);
                        TextView tv1=(TextView) v.findViewById(R.id.tv_locality);
                    //informacion a mostrar en la ventana de informacion
                        TextView tv2=(TextView) v.findViewById(R.id.tv_lat);
                        TextView tv3=(TextView) v.findViewById(R.id.tv_lng);
                        TextView tv4=(TextView) v.findViewById(R.id.tv_snippet);

                        tv1.setText(marker.getTitle());
                        tv4.setText(marker.getSnippet());
                    //descripcion cualquiera


                    return v;


                }
            });
        }




















        //otros markers
        // Add a marker in a place and move the camera
       // LatLng car = new LatLng(19.673098, -99.015353);
       // LatLng car2 = new LatLng(19.630964, -99.031613);
       /* MarkerOptions options= new MarkerOptions()
                .position(car)
                .title("hi")
                .snippet("El carwash feliz\nhello\nhola\nyouju")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro));
        //icono que se muestra
        mMap.moveCamera(CameraUpdateFactory.newLatLng(car));
        marker= mMap.addMarker(options);



        //segundo autolavado
        mMap.addMarker(new MarkerOptions()
                .position(car2)
                .title("Carwash 2")
                .snippet("El carwash feliz 2")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(car2));
        //ubicar el marcador en la direccion declarada arriba

*/

        //para hacer zoom y verlo en el lugar deseado(tecamac)
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnInfoWindowClickListener(this);
        goToLocationZoom(19.709338, -98.966541, 10);
//ir a tecamac
String hola="hh";
//informacion del primer marker, posicion, titulo...


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

//aqui iria la consulta



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

        protected void onPostExecute(String result) {


            try {

               JSONObject ja= new JSONObject(result);
                //JSONArray autolavados= ja.getJSONArray("autolavados");
                JSONArray jsonArray= ja.getJSONArray("autolavados");;
//HERE I ADD THE MARKERSSS OF THE DATABASE
                //ArrayList<MiObjeto> listaObj =new ArrayList<MiObjeto> ();

                for (int i = 0; i < jsonArray.length(); i++) {
                    // Create a marker for each carwashhh in the JSON data.
                    JSONObject jsonObj = jsonArray.getJSONObject(i);
                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.carro))
                            .title(jsonObj.getString("nombre"))
                            .snippet(jsonObj.getString("nombre"))
                            .position(new LatLng(
                                    jsonObj.getDouble("latitud"),
                                    jsonObj.getDouble("longitud")
                            ))
                    );
                }
                /*for(MiObjeto obj: listaObj){
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(new LatLng(obj.getLat(), obj.getLng()))
                            .title(obj.getNombre());

                    mMap.addMarker(markerOptions);
                }

*/

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }


    }

    public class MiObjeto {
        String nombre;
        double latitud;
        double longitud;
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getNombre() {
            return nombre;
        }


        public void setLat(double latitud) {
            this.latitud = latitud;
        }

        public double getLat() {
            return latitud;
        }

        public void setLng(double longitud) {
            this.longitud = longitud;
        }

        public double getLng() {
            return longitud;
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

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }




    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(this, InfoCarWash.class);
        startActivity(i);


    }
}
