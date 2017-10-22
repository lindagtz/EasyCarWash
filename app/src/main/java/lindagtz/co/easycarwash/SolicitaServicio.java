package lindagtz.co.easycarwash;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import java.util.Map;

public class SolicitaServicio extends AppCompatActivity {
String telef;
    TextView telefono;
    Button btnEnviar, btnCanc;
    EditText texto;

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

        }

        btnEnviar=(Button)findViewById(R.id.btnEnv);
        btnCanc=(Button)findViewById(R.id.btncanc);
        telefono=(TextView)findViewById(R.id.txtTelefAutolavado);
        texto=(EditText)findViewById(R.id.textoMsg);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numer=telefono.getText().toString();
                String text=texto.getText().toString();
            try {
                SmsManager smsManager= SmsManager.getDefault();
                smsManager.sendTextMessage(numer, null, text, null, null);
                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Mensaje enviado!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                telefono.setText("");
                texto.setText("");
            }catch (Exception e) {
                Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Oops, falló!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

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
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
            switch (requestCode){
                case 1:{
                    if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                        if(ContextCompat.checkSelfPermission(SolicitaServicio.this,
                                Manifest.permission.SEND_SMS)== PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "sii", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(this, "NOO", Toast.LENGTH_SHORT).show();

                    }
                    return;
                }
            }
        }









    }




