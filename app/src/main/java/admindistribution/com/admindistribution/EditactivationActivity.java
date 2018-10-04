package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
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


public class EditactivationActivity extends AppCompatActivity {

    private TextView daftarnama,daftaremail,daftarstatus;
    private Context context =this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editactivation);

        daftarnama = (TextView) findViewById(R.id.daftarnama);
        daftaremail = (TextView) findViewById(R.id.daftaremail);
        daftarstatus = (TextView) findViewById(R.id.daftarstatus);

        daftarnama.setText(getIntent().getStringExtra("activation_id"));
        daftaremail.setText(getIntent().getStringExtra("activation_code"));
        daftarstatus.setText(getIntent().getStringExtra("status"));

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
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(EditactivationActivity.this, android.R.layout.select_dialog_singlechoice);
                final String[] conten = {"Active","Inactive"};
                alertDialog1.setItems(conten, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (conten[which] == "Active"){
                            daftarstatus.setText("active");
                        }
                        else if (conten[which] == "Inactive"){
                            daftarstatus.setText("inactive");
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
            daftarnama.setError("Please enter Activation ID");
            daftarnama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftaremail.getText())) {
            daftaremail.setError("Please enter Activation Code");
            daftaremail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(daftarstatus.getText())) {
            daftarstatus.setError("Please enter Status");
            daftarstatus.requestFocus();
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
                User user = SharedPrefManager.getInstance(EditactivationActivity.this).getUser();
                String id = user.getAddress();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("activation_id", daftarnama.getText().toString());
                params.put("activation_code", daftaremail.getText().toString());
                params.put("status", daftarstatus.getText().toString());
                params.put("session_id", id);
                params.put("id", getIntent().getStringExtra("id"));


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EDITACTIVATION, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

}
