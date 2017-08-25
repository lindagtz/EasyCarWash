package lindagtz.co.easycarwash;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class NavDrawer extends AppCompatActivity {
    /**
     * Instancia del drawer
     */
    protected DrawerLayout drawerLayout;


    /**
     * Titulo inicial del drawer
     */
    private String drawerTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        setToolbar(); // Setear Toolbar como action bar

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
        if (navigationView != null) {
            setupDrawerContent(navigationView);        }

        drawerTitle = getResources().getString(R.string.home_item);
        if (savedInstanceState == null) {
            // Seleccionar item
        }

    }

    protected void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
               drawerLayout.openDrawer(GravityCompat.START);
                Log.i(String.valueOf("g"),"mensaje");
                return true;

        }


        return super.onOptionsItemSelected(item);


    }

    protected void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Marcar item presionado
                        menuItem.setChecked(true);
                        // Crear nuevo fragmento
                        int id=menuItem.getItemId();
                        if (id == R.id.nav_perfil) {
                            // Handle the camera action
                            startActivity(new Intent(getApplicationContext(), Perfil.class));
                        } else if (id == R.id.nav_map) {
                            startActivity(new Intent(getApplicationContext(), Maps.class));
                        } else if (id == R.id.nav_about) {
                            startActivity(new Intent(getApplicationContext(), Welcome.class));
                        } else if (id == R.id.nav_serv) {
                            startActivity(new Intent(getApplicationContext(), Welcome.class));
                        } else if (id == R.id.nav_log_out) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        }

                        return true;
                    }
                }
        );
    }


}

