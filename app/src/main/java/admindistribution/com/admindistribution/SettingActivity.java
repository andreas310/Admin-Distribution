package admindistribution.com.admindistribution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findViewById(R.id.textedit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(SettingActivity.this).getUser();
                String role = user.getRole();
                switch(role) {
                    case "dealer":

                        startActivity(new Intent(SettingActivity.this, DealerprofileActivity.class));
                        finish();
                        break;
                    default:

                        startActivity(new Intent(SettingActivity.this, UserprofileActivity.class));
                        finish();
                        break;
                }
            }
        });

        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(SettingActivity.this).getUser();
                String role = user.getRole();
                switch(role) {
                    case "dealer":

                        startActivity(new Intent(SettingActivity.this, DealerActivity.class));
                        finish();
                        break;
                    default:

                        startActivity(new Intent(SettingActivity.this, SalesActivity.class));
                        finish();
                        break;
                }

            }
        });

        findViewById(R.id.textlogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }

    public void onBackPressed() {
        User user = SharedPrefManager.getInstance(SettingActivity.this).getUser();
        String role = user.getRole();
        switch(role) {
            case "dealer":

                startActivity(new Intent(SettingActivity.this, DealerActivity.class));
                finish();
                break;
            default:

                startActivity(new Intent(SettingActivity.this, SalesActivity.class));
                finish();
                break;
        }
    }
}
