package lindagtz.co.easycarwash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class Perfi extends Fragment {
EditText usnombre, usdire, usemail,telef;
    Button id;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_perfi, container, false);
        usnombre=(EditText) v.findViewById(R.id.usnombre);
        usdire=(EditText) v.findViewById(R.id.usdirec);
        usemail=(EditText) v.findViewById(R.id.usemail);
        telef=(EditText)v.findViewById(R.id.ustelef);
        id=(Button)v.findViewById(R.id.btnIdm);
id.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        update();
    }
});
      //  id_user = getActivity().getIntent().getExtras().getString("id_user");
        return v;


    }



    @Override
    public void onStart() {
        super.onStart();
        Snackbar.make(getActivity().getWindow().findViewById(android.R.id.content), "Actualiza tu información!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyId", Context.MODE_PRIVATE);
        //  SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(HomeActivity.globalPreference_user, Context.MODE_PRIVATE);
        String id_user= preferences.getString("id_user", "Default id");
            Log.i("id perfi",id_user);
        new ConsultarDatos().execute("http://easycarwash.hol.es/consultaDatosUser.php?id_user="+id_user);



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
                usnombre.setText(""+ja.getString(1)+"");
                usemail.setText(""+ja.getString(2)+"");
                usdire.setText(""+ja.getString(4)+"");
                telef.setText(""+ja.getString(5)+"");





            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }


    public void update(){
        SharedPreferences preferences = this.getActivity().getSharedPreferences("MyId", Context.MODE_PRIVATE);
        //  SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(HomeActivity.globalPreference_user, Context.MODE_PRIVATE);
        String id_user= preferences.getString("id_user", "Default id");

        new CargarDatos().execute("http://easycarwash.hol.es/updatecli.php?name="+usnombre.getText().toString()+
                "&email="+usemail.getText().toString()+
                "&direccion="+usdire.getText().toString()+
                "&telefono="+telef.getText().toString()+
                "&id_user="+id_user);


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

            Snackbar.make(getActivity().getWindow().findViewById(android.R.id.content), "Los datos se actualizaron correctamente!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            Log.i("ok","ok");
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
}