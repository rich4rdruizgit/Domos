package proyecto.com.domos.ui.activities.register;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import proyecto.com.domos.R;
import proyecto.com.domos.util.HelperUtil;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextInputEditText txtIdentificationType,
            txtNumberDocument,txtFullName,txtEmail
            ,txtPassword,txtCel;
    private TextView txtTerms;
    private CheckBox chkTerms;
    private Button btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txtIdentificationType = findViewById(R.id.txtIdentificationType);
        txtNumberDocument = findViewById(R.id.txtNumberDocument);
        txtFullName = findViewById(R.id.txtFullName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtCel = findViewById(R.id.txtCel);
        chkTerms = findViewById(R.id.chkTerms);
        txtTerms = findViewById(R.id.txtTerms);
        btnSignIn = findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(this);
        chkTerms.setOnCheckedChangeListener(this);
        txtIdentificationType.setKeyListener(null);
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.btnSignIn:
                signIn();
                break;
        }
    }

    private void signIn()
    {
        if(validateFields())
        {

        }
    }

    private boolean validateFields()
    {
        String identificationType = txtIdentificationType.getText().toString();
        String numberDocument = txtNumberDocument.getText().toString();
        String fullName = txtFullName.getText().toString();
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String cel = txtCel.getText().toString();

        boolean response = true;

        if(identificationType.equals(""))
        {
            response = false;
            txtIdentificationType.setError(getResources().getString(R.string.err_select_identification_type));
        }
        if(numberDocument.equals(""))
        {
            response = false;
            txtNumberDocument.setError(getResources().getString(R.string.err_type_identification_number));
        }
        if(fullName.equals(""))
        {
            response = false;
            txtFullName.setError(getResources().getString(R.string.err_type_name));
        }
        if(email.equals(""))
        {
            response = false;
            txtEmail.setError(getResources().getString(R.string.err_type_email));
        }
        else if(!HelperUtil.validaTeEmail(email))
        {
            response = false;
            txtEmail.setError(getResources().getString(R.string.err_invalid_email_format));
        }
        if(password.equals(""))
        {
            response = false;
            txtPassword.setError(getResources().getString(R.string.err_type_password));
        }
        if(cel.equals(""))
        {
            response = false;
            txtCel.setError(getResources().getString(R.string.err_type_cel_number));
        }
        else if(cel.length()!=10)
        {
            response = false;
            txtCel.setError(getResources().getString(R.string.err_invalid_cell_phone_number));
        }
        if(!chkTerms.isChecked())
        {
            response = false;
            txtTerms.setError(getResources().getString(R.string.err_accept_terms_and_conditions));
        }

        return response;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        switch (compoundButton.getId())
        {
            case R.id.chkTerms:
                txtTerms.setError(null);
                break;
        }
    }
}
