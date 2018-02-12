package proyecto.com.domos.ui.activities.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import proyecto.com.domos.R;
import proyecto.com.domos.ui.fragments.delivery.DeliveryFragment;
import proyecto.com.domos.ui.fragments.feedback.FeedbackFragment;
import proyecto.com.domos.ui.fragments.profile.ProfileFragment;
import proyecto.com.domos.ui.fragments.shop.ShopFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigationMenu);
        navigationView.setOnNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container_fragment, new DeliveryFragment()).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (item.getItemId()){
            case R.id.navDelivery:
                transaction.replace(R.id.container_fragment, new DeliveryFragment()).commit();
                Toast.makeText(this, "Domicilios", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navShop:
                transaction.replace(R.id.container_fragment, new ShopFragment()).commit();
                Toast.makeText(this, "Compras", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navFeedback:
                transaction.replace(R.id.container_fragment, new FeedbackFragment()).commit();
                Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.navProfile:
                transaction.replace(R.id.container_fragment, new ProfileFragment()).commit();
                Toast.makeText(this, "Perfil", Toast.LENGTH_SHORT).show();
                return true;
            }

        return false;
    }
}
