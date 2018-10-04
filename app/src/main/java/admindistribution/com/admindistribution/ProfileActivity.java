package admindistribution.com.admindistribution;

/**
 * Created by POPO on 1/21/2018.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static java.lang.Integer.valueOf;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        String role = user.getRole();
        switch(role) {
            case "admin":
                startActivity(new Intent(this, AdminActivity.class));
                finish();
                break;

            case "superadmin":
                startActivity(new Intent(this, AdminActivity.class));
                finish();
                break;

            default:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Aplikasi Untuk Admin");
                builder1.setMessage("Pastikan anda adalah admin");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                SharedPrefManager.getInstance(getApplicationContext()).logout();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

                break;
        }

        //setting the values to the textviews
        textViewId.setText(user.getSalt());
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getRole());

        //when the user presses logout button
        //calling the logout method



    }


}