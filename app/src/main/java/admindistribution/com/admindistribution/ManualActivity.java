package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by POPO on 2/20/2018.
 */

public class ManualActivity extends AppCompatActivity {
    EditText qrcode;
    private Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);

        qrcode = (EditText) findViewById(R.id.phonenum);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               qrcode();
            }
        });


    }

    private void qrcode(){
        final String qrcodes = qrcode.getText().toString();
        if (TextUtils.isEmpty(qrcodes)) {
            qrcode.setError("Please enter your QR Code");
            qrcode.requestFocus();
            return;
        }
        class UserLogin2 extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":


                            //getting the user from the response

                            JSONObject json= obj.getJSONObject("data");
                            //JSONObject w = json.getJSONObject("product");


                            //creating a new user object
                            User3 user2 = new User3(
                                    json.getString("poin_sales"),
                                    json.getString("serial_code"),
                                    json.getString("initial_code"),
                                    json.getString("name"),
                                    json.getString("activation_id"),
                                    json.getString("id")
                            );

                            SharedPrefManager2.getInstance(getApplicationContext()).getBarcode(user2);

                            finish();
                            startActivity(new Intent(getApplicationContext(), RedeemActivity.class));



                            //starting the profile activity


                            break;

                        default:

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("QR not Valid");
                            builder1.setMessage(obj.getString("message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("serial_code", qrcode.getText().toString().trim());

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REDEEM, params);
            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        else {
            finish();
            startActivity(new Intent(getApplicationContext(), SalesActivity.class));
        }
    }
}
