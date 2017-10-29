package lindagtz.co.easycarwash;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class HomeActivity extends AppIntro {
String id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        id_user=bundle.getString("id_user");
        Log.i("iduser",id_user);



        addSlide(AppIntroFragment.newInstance("Hola!, es un gusto verte de nuevo!","Una aplicación que te permitirá tener tu automóvil siempre limpio!",R.drawable.welcomee,
                Color.parseColor("#29B6F6")));
        addSlide(AppIntroFragment.newInstance("Localiza autolavados cerca de tí!","Y consulta su información con la ayuda de Google Maps!",R.drawable.gglemps,
                Color.parseColor("#b71c1c")));
        addSlide(AppIntroFragment.newInstance("Servicio de autolavado a domicilio","Solicita ya un servicio de autolavado a domicilio! \n ¡No te quedes atrás!",R.drawable.casawashh,

                Color.parseColor("#AED581")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#039BE5"));
        setSeparatorColor(Color.parseColor("#039BE5"));






    }

    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent i = new Intent(this,NaviDrawer.class);
        i.putExtra("id_user",id_user);
        startActivity(i);
        finish();

        // Do something when users tap on Skip button.
    }

    public void onDonePressed() {
        Intent intent = new Intent(this,NaviDrawer.class);
        intent.putExtra("id_user",id_user);
        startActivity(intent);
        finish();// Do something when users tap on Done button.

    }

    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }






}
