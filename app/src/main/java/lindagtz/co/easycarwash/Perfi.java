package lindagtz.co.easycarwash;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class Perfi extends Fragment {
EditText edperfil;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v= inflater.inflate(R.layout.fragment_perfi, container, false);
        edperfil=(EditText) v.findViewById(R.id.edPer);
        return v;


    }


    @Override
    public void onStart() {
        super.onStart();
        edperfil.setText("hola");
    }
}