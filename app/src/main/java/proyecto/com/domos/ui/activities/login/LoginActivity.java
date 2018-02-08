package proyecto.com.domos.ui.activities.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

import proyecto.com.domos.R;

import proyecto.com.domos.net.api.RetroFitApi;
import proyecto.com.domos.net.models.Login;
import proyecto.com.domos.net.models.User;
import proyecto.com.domos.ui.activities.RegisterActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final String TAG = LoginActivity.class.toString();
    private Retrofit retrofit;
    private RetroFitApi retroFitApi;
    private EditText mEditEmail, mEditPassword;
    private Button mBtnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mEditEmail = findViewById(R.id.edt_login_email);
        mEditPassword = findViewById(R.id.edt_login_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        retrofit = new Retrofit.Builder().baseUrl(RetroFitApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        retroFitApi = retrofit.create(RetroFitApi.class);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:

                String username = mEditEmail.getText().toString();
                String password = mEditPassword.getText().toString();
                Call<User> userCall = retroFitApi.login(new Login(username,password));
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.d(TAG,"retrofit:"+response.body().getResult());
                        Log.d(TAG, "retrofit:"+response.body().getSid());
                        Log.d(TAG, "retrofit:"+response.body().getUserDatos().toString());
                        if(response.body().getResult().toString().equals("0")){
                            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                            Toast.makeText(LoginActivity.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Error Intenta de Nuevo", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Problemas de conexion", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, t.getMessage());
                        t.fillInStackTrace();
                    }
                });
                break;
        }
    }
}
