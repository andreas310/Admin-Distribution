package admindistribution.com.admindistribution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class SettinglistActivity extends AppCompatActivity {

    public CardView cardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settinglist);

        cardView = (CardView) findViewById(R.id.cardView);

                User user = SharedPrefManager.getInstance(SettinglistActivity.this).getUser();

        switch (user.getRole()){
            case "superadmin":
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), SettingadminActivity.class));
                    }
                });

                break;
            default:
                cardView.setVisibility(View.INVISIBLE);
                break;
        }


        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                finish();

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
        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
        finish();
    }
}
