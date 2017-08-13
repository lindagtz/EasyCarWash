package lindagtz.co.easycarwash;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class RegistroCliente extends AppCompatActivity {

//declaramos todas las variables a usar
    EditText name, email, password, direccion, telefono;
    TextView auth_id;
    Button regCancelar, regGuardar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cli);

        //Edittext para registrar en la BD
        name = (EditText) findViewById(R.id.edNamec);
        email = (EditText) findViewById(R.id.edEmailc);
        password = (EditText) findViewById(R.id.edPasswordc);
        direccion = (EditText) findViewById(R.id.edDireccionc);
        telefono = (EditText) findViewById(R.id.edTelf);
        auth_id=(TextView) findViewById(R.id.txtperm);
        regGuardar = (Button) findViewById(R.id.regGuardar);

        regGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new RegistroCliente.CargarDatos().execute("http://easycarwash.hol.es/registrocli.php?name=" + name.getText().toString() + "&email=" + email.getText().toString()+ "&password=" + password.getText().toString()+ "&direccion=" + direccion.getText().toString()+ "&telefono=" + telefono.getText().toString()+ "&tipo=" + tipo.getText().toString()+ "&color=" + color.getText().toString()+ "&marca=" + marca.getText().toString() + "&auth_id=" + auth_id.getText().toString());
                new RegistroCliente.CargarDatos().execute("http://easycarwash.hol.es/registrocli.php?name=" +
                        name.getText().toString() + "&email=" + email.getText().toString()+ "&password=" +
                        password.getText().toString()+ "&direccion=" +
                        direccion.getText().toString()+ "&telefono=" +
                        telefono.getText().toString()+"&auth_id=" + auth_id.getText().toString());

            }
        });


        regCancelar = (Button) findViewById(R.id.regCancelar);

        regCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent regCancelar= new Intent(RegistroCliente.this, Login.class);
                startActivity(regCancelar);
            }
        });


    }

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
            Intent Login= new Intent(RegistroCliente.this, Login.class);
            startActivity(Login);
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















}
