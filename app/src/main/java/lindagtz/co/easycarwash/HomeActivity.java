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



        addSlide(AppIntroFragment.newInstance("1","hola",R.drawable.material_background,
                Color.parseColor("#00E676")));
        addSlide(AppIntroFragment.newInstance("2","como",R.drawable.material_background2,
                Color.parseColor("#26C6DA")));
        addSlide(AppIntroFragment.newInstance("3","tas",R.drawable.material_background3,
                Color.parseColor("#f44336")));

        showStatusBar(false);
        setBarColor(Color.parseColor("#039BE5"));
        setSeparatorColor(Color.parseColor("#039BE5"));






    }

    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);

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
