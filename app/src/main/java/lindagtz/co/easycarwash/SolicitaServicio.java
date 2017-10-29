package lindagtz.co.easycarwash;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SolicitaServicio extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManager;
    String telef;
    TextView telefono, coorden, direccion, txt1;
    Button btnEnviar, btnCanc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicita_servicio);
        Bundle bundle = getIntent().getExtras();
        telef = bundle.getString("telefono");
        Log.i("telefonoo", telef);

        telefono = (TextView) findViewById(R.id.txtTelefAutolavado);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent in = new Intent(SolicitaServicio.this, Maps.class);
                startActivity(in);
            }
        });



        telefono.setText(telef);




        btnEnviar=(Button)findViewById(R.id.btnEnv);
        btnCanc=(Button)findViewById(R.id.btncanc);
        telefono=(TextView)findViewById(R.id.txtTelefAutolavado);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1000, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1000, this);

        }
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    TextView co = (TextView) findViewById(R.id.txtcoordenadas);
                    TextView di = (TextView) findViewById(R.id.txtdireccion);
                    String coord = co.getText().toString();
                    String direcc = di.getText().toString();

                    //Comenzamos con los SMS
                    if (ContextCompat.checkSelfPermission(SolicitaServicio.this,
                            Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {


                        if (ActivityCompat.shouldShowRequestPermissionRationale(SolicitaServicio.this,
                                Manifest.permission.SEND_SMS)) {
                            ActivityCompat.requestPermissions(SolicitaServicio.this,
                                    new String[]{Manifest.permission.SEND_SMS}, 1);

                        } else {
                            ActivityCompat.requestPermissions(SolicitaServicio.this,
                                    new String[]{Manifest.permission.SEND_SMS}, 1);

                        }

                    } else {
                        //nada

                        String numer = telefono.getText().toString();

                        String texto = ":\n"+coord + "\n" + direcc;
                       SmsManager.getDefault().sendTextMessage(numer, null, direcc + "\n" +
                                coord, null, null);
                        Log.i("texto: ", texto);
                    }
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Mensaje enviado, Por favor espere su confirmación (10 min aprox.)!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
             /*   telefono.setText("");
                coorden.setText("");
                    direccion.setText("");*/
                } catch (Exception e) {
                    Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Oops, falló el envío!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    coorden.setText("");
                    direccion.setText("");
                    e.printStackTrace();


                }


            }
        });

        btnCanc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SolicitaServicio.this, Maps.class);
                startActivity(i);
            }
        });


    }
      /*  public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
            switch (requestCode){
                case 1:{
                    if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                        if(ContextCompat.checkSelfPermission(SolicitaServicio.this,
                                Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "sii", Toast.LENGTH_SHORT).show();
                            Log.i("si","si");
                        }

                    }else{
                        Toast.makeText(this, "NOO", Toast.LENGTH_SHORT).show();
                        Log.i("no","no");

                    }
                    return;
                }
            }
        }
*/

    @Override
    public void onLocationChanged(Location location) {
        coorden=(TextView) findViewById(R.id.txtcoordenadas);
        direccion=(TextView) findViewById(R.id.txtdireccion);
        coorden.setText("http://maps.google.com/?q="+ location.getLatitude() + "," + location.getLongitude());
        this.setLocation(location);
    }

    private void setLocation(Location location) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> list = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);
                if (!list.isEmpty()) {
                    Address DirCalle = list.get(0);
                    direccion.setText("Solicitud de autolavado a domicilio en: \n"
                            + DirCalle.getAddressLine(0));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String provider) {

        Log.d("Latitude","disable");
    }
}




