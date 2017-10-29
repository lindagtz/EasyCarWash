package lindagtz.co.easycarwash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

public class InfoCarWash extends AppCompatActivity {
TextView direccion, telefono, horario, servicio, nombre;
String id_auto,id_user, tel;
    public String telef=null;

    Button SolicitarSer;
    protected static TextView text_v;
    protected static RatingBar rating_b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_infocarwash);
        Bundle bundle = getIntent().getExtras();
        id_auto=bundle.getString("id_autolavado");
        direccion=(TextView) findViewById(R.id.txtAddress);
        telefono=(TextView) findViewById(R.id.txtTel);
        horario=(TextView)findViewById(R.id.txtHorario);
        servicio=(TextView)findViewById(R.id.txtServic);
        nombre=(TextView)findViewById(R.id.txtNombreAu);
        SolicitarSer=(Button)findViewById(R.id.btnSolicitar);
        //rating_b = (RatingBar) findViewById(R.id.ratingBar);
        text_v = (TextView)findViewById(R.id.txtEve);




        //listenerForRatingBar();
        SolicitarSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarTelef();
            }
        });




    }



   /* private void listenerForRatingBar() {


        rating_b.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text_v.setText(String.valueOf(rating));
                    }
                }
        );
    }
*/
    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
        new ConsultarDatos().execute("http://easycarwash.hol.es/consultaUserAuto.php?id_autolavado="+id_auto);
        SharedPreferences sharedPreferences= getSharedPreferences("MyId", Context.MODE_PRIVATE);
        //  SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(HomeActivity.globalPreference_user, Context.MODE_PRIVATE);
        String id_userr= sharedPreferences.getString("id_user", "Default id");

        //new ConsultarDatos().execute("http://easycarwash.hol.es/consultaAutoId.php?id_autolavado="+id_auto);


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

        protected void onPostExecute(String result) {
            try {
                JSONArray ja = new JSONArray(result);
//para mostrar los datos del autolavado esta en un arreglo json
                //te.setText(ja.getString(2));
                nombre.setText("*Autolavado "+ja.getString(0)+"*");
                direccion.setText(ja.getString(1));
                telef=ja.getString(2);
                telefono.setText(ja.getString(2));
                horario.setText(ja.getString(3));


            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.i("telef",telef);


        }


    }

    private void enviarTelef() {
        Intent SolicitaServic=new Intent(this, SolicitaServicio.class);
        SolicitaServic.putExtra("telefono", telef);
        startActivity(SolicitaServic);

    }


    private String downloadUrl(String myurl) throws IOException {
        Log.i("URL",""+myurl);
        myurl = myurl.replace(" ","%20");
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 1500;

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





}
