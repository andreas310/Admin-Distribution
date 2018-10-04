package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class AdddealerActivity extends AppCompatActivity {

    private TextView daftarnama,daftaremail,daftarpass,daftarconpass,daftaruser,daftaradd,daftarphone,daftardename,daftardeadd,daftardephone;
    private Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddealer);

        daftarnama = (TextView) findViewById(R.id.daftarnama);
        daftaremail = (TextView) findViewById(R.id.daftaremail);
        daftarpass = (TextView) findViewById(R.id.daftarpass);
        daftarconpass = (TextView) findViewById(R.id.daftarconpass);
        daftaruser = (TextView) findViewById(R.id.daftaruser);
        daftaradd = (TextView) findViewById(R.id.daftaradd);
        daftarphone = (TextView) findViewById(R.id.daftarphone);
        daftardename = (TextView) findViewById(R.id.daftardename);
        daftardeadd= (TextView) findViewById(R.id.daftardeadd);
        daftardephone = (TextView) findViewById(R.id.daftarstatus);

        findViewById(R.id.daftarsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
    }


    private void userLogin() {
        //first getting the values

        //validating inputs
        if (TextUtils.isEmpty(daftarnama.getText())) {
            daftarnama.setError("Please enter your Name");
            daftarnama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftaremail.getText())) {
            daftaremail.setError("Please enter your Email");
            daftaremail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftarpass.getText())) {
            daftarpass.setError("Please enter your password");
            daftarpass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftarconpass.getText())) {
            daftarconpass.setError("Please enter your Confirmation password");
            daftarconpass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftaruser.getText())) {
            daftaruser.setError("Please enter your Username");
            daftaruser.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftaradd.getText())) {
            daftaradd.setError("Please enter your Address");
            daftaradd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftarphone.getText())) {
            daftarphone.setError("Please enter your Phone");
            daftarphone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftardename.getText())) {
            daftardename.setError("Please enter your Dealer Name");
            daftardename.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftardeadd.getText())) {
            daftardeadd.setError("Please enter your Dealer Address");
            daftardeadd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftardephone.getText())) {
            daftardephone.setError("Please enter your Dealer Phone");
            daftardephone.requestFocus();
            return;
        }

        //if everything is fine

        class UserLogin extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar.setVisibility(View.GONE);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            String userJson = obj.getString("data");

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Complete");
                            builder1.setMessage(userJson);
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                            //starting the profile activity


                            break;

                        default:
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Failed");
                            builder.setMessage(obj.getString("message"));
                            builder.setCancelable(true);

                            builder.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert1 = builder.create();
                            alert1.show();
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
                params.put("name", daftarnama.getText().toString());
                params.put("email", daftaremail.getText().toString());
                params.put("password", daftarpass.getText().toString());
                params.put("confirm_password", daftarconpass.getText().toString());
                params.put("username", daftaruser.getText().toString());
                params.put("phone", daftarphone.getText().toString());
                params.put("address", daftaradd.getText().toString());
                params.put("dealer_name", daftardename.getText().toString());
                params.put("dealer_address", daftardeadd.getText().toString());
                params.put("dealer_phone", daftardephone.getText().toString());


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

}
