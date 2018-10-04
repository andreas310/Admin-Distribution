package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;


public class EditdealerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView daftarnama,daftaradd,daftarphone,daftardename,daftardeadd,daftardephone;
    private Context context =this;
    private List<User8> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editdealer);

        daftarnama = (TextView) findViewById(R.id.daftarnama);
        daftaradd = (TextView) findViewById(R.id.daftaradd);
        daftarphone = (TextView) findViewById(R.id.daftarphone);
        daftardename = (TextView) findViewById(R.id.daftardename);
        daftardeadd= (TextView) findViewById(R.id.daftardeadd);
        daftardephone = (TextView) findViewById(R.id.daftarstatus);

        daftarnama.setText(getIntent().getStringExtra("user_name"));
        daftaradd.setText(getIntent().getStringExtra("user_address"));
        daftarphone.setText(getIntent().getStringExtra("user_phone"));
        daftardename.setText(getIntent().getStringExtra("dealer_name"));
        daftardeadd.setText(getIntent().getStringExtra("dealer_address"));
        daftardephone.setText(getIntent().getStringExtra("dealer_phone"));

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
                            builder1.setTitle(getIntent().getStringExtra("pos"));
                            builder1.setMessage(userJson);
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
//                                            User8 item = new User8 (
//                                                    (getIntent().getStringExtra("id")),
//                                                    (daftarnama.getText().toString()),
//                                                    (daftardename.getText().toString()),
//                                                    (daftardephone.getText().toString()),
//                                                    (daftardeadd.getText().toString()),
//                                                    (getIntent().getStringExtra("user_username")),
//                                                    (getIntent().getStringExtra("created_at")),
//                                                    (getIntent().getStringExtra("user_email")),
//                                                    (daftaradd.getText().toString()),
//                                                    (daftarphone.getText().toString()),
//                                                    (getIntent().getStringExtra("salt"))
//
//                                            );
//                                            listItems.set(Integer.valueOf(getIntent().getStringExtra("posi")), item);
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
                params.put("dealer_id", getIntent().getStringExtra("id"));
                params.put("user_id", getIntent().getStringExtra("salt"));
                params.put("user_name", daftarnama.getText().toString());
                params.put("user_phone", daftarphone.getText().toString());
                params.put("user_address", daftaradd.getText().toString());
                params.put("dealer_name", daftardename.getText().toString());
                params.put("dealer_address", daftardeadd.getText().toString());
                params.put("dealer_phone", daftardephone.getText().toString());


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EDITDEALER, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, DealerlistActivity.class));
        finish();
    }

}
