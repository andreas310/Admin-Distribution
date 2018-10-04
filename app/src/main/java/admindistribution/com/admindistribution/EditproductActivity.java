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
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class EditproductActivity extends AppCompatActivity {

    private TextView daftarnama,daftaremail,daftarpass,daftarconpass,daftaruser,daftaradd,daftarphone,daftardename,daftardeadd,daftardephone;
    private Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editproduct);

        daftarnama = (TextView) findViewById(R.id.daftarnama);
        daftaremail = (TextView) findViewById(R.id.daftaremail);
        daftaruser = (TextView) findViewById(R.id.daftaruser);
        daftaradd = (TextView) findViewById(R.id.daftaradd);
        daftarphone = (TextView) findViewById(R.id.daftarphone);
        daftardename = (TextView) findViewById(R.id.daftardename);
        daftardeadd= (TextView) findViewById(R.id.daftardeadd);
        daftardephone = (TextView) findViewById(R.id.daftarstatus);

        daftarnama.setText(getIntent().getStringExtra("name"));
        daftaremail.setText(getIntent().getStringExtra("serial_code"));
        daftaruser.setText(getIntent().getStringExtra("initial_code"));
        daftardename.setText(getIntent().getStringExtra("poin_sales"));
        daftardeadd.setText(getIntent().getStringExtra("poin_dealer"));
        daftardephone.setText(getIntent().getStringExtra("status"));
        daftaradd.setText(getIntent().getStringExtra("activation_id"));
        daftarphone.setText(getIntent().getStringExtra("activation_qr"));


        switch (daftaradd.getText().toString()){
            case "null":
                daftaradd.setText("");
                daftarphone.setText("");
                break;
        }

        findViewById(R.id.daftarsubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        findViewById(R.id.daftarstatus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
                alertDialog1.setTitle("Pilih Status");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditproductActivity.this, android.R.layout.select_dialog_singlechoice);
                final String[] conten = {"Active","Inactive"};
                alertDialog1.setItems(conten, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (conten[which] == "Active"){
                            daftardephone.setText("active");
                        }
                        else if (conten[which] == "Inactive"){
                            daftardephone.setText("inactive");
                        }
                    }
                });

                alertDialog1.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });
                alertDialog1.show();

            }
        });
    }


    private void userLogin() {
        //first getting the values

        //validating inputs
        if (TextUtils.isEmpty(daftarnama.getText())) {
            daftarnama.setError("Please enter your Name Product");
            daftarnama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftaremail.getText())) {
            daftaremail.setError("Please enter Serialcode");
            daftaremail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftaruser.getText())) {
            daftaruser.setError("Please enter Initialcode");
            daftaruser.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(daftardename.getText())) {
            daftardename.setError("Please enter Point Sales");
            daftardename.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftardeadd.getText())) {
            daftardeadd.setError("Please enter Point Dealer");
            daftardeadd.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftardephone.getText())) {
            daftardephone.setError("Please enter Status");
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
                User user = SharedPrefManager.getInstance(EditproductActivity.this).getUser();
                String id = user.getAddress();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("name", daftarnama.getText().toString());
                params.put("serial_code", daftaremail.getText().toString());
                params.put("initial_code", daftaruser.getText().toString());
                params.put("activation_id", daftarphone.getText().toString());
                params.put("activation_qr", daftaradd.getText().toString());
                params.put("poin_sales", daftardename.getText().toString());
                params.put("poin_dealer", daftardeadd.getText().toString());
                params.put("status", daftardephone.getText().toString());
                params.put("session_id", id);
                params.put("product_id", getIntent().getStringExtra("product_id"));


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EDITPRODUCT, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

}
