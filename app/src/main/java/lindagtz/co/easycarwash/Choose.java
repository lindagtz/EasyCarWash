package lindagtz.co.easycarwash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Choose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Button Cliente;
        Button Autol;

        Cliente = (Button) findViewById(R.id.btnCli);

        Cliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Cliente= new Intent(Choose.this, RegistroCliente.class);
                startActivity(Cliente);
            }
        });


        Autol = (Button) findViewById(R.id.btnAut);

        Autol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Autol= new Intent(Choose.this, RegistroAuto.class);
                startActivity(Autol);
            }
        });










    }
}
