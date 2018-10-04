package admindistribution.com.admindistribution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RedeemActivity extends AppCompatActivity {

    public static TextView name, poin, serial, initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        name = (TextView) findViewById(R.id.name);
        initial = (TextView) findViewById(R.id.pointsales);
        serial = (TextView) findViewById(R.id.textStatus);
        poin = (TextView)findViewById(R.id.initial);

        User3 user = SharedPrefManager2.getInstance(this).getbar();

        poin.setText(user.getPoin());
        name.setText(user.getNamePro());
        serial.setText(user.getSerial());
        initial.setText(user.getInitial());

        findViewById(R.id.redeemclaim).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RedeemActivity.this, Userpointredeem.class));
            }
        });

    }
    public void onBackPressed() {
        startActivity(new Intent(this, SalesActivity.class));
        finish();
    }



}
