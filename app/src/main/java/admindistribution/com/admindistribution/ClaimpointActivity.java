package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ClaimpointActivity extends AppCompatActivity {

    private Context context = this;
    EditText poin;
    TextView currency,idr,rupiah,minimal,result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claimpoint);
        currency = (TextView) findViewById(R.id.current);
        idr = (TextView) findViewById(R.id.idr);
        minimal = (TextView) findViewById(R.id.minimal);
        rupiah = (TextView) findViewById(R.id.rupiah);
        result = (TextView) findViewById(R.id.result);
        poin = (EditText) findViewById(R.id.inputpoin);


        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(ClaimpointActivity.this).getUser();
                String role = user.getRole();
                switch(role) {
                    case "dealer":

                        startActivity(new Intent(ClaimpointActivity.this, DealerActivity.class));
                        finish();
                        break;
                    default:

                        startActivity(new Intent(ClaimpointActivity.this, SalesActivity.class));
                        finish();
                        break;
                }
            }
        });
        findViewById(R.id.claimpointbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postpoin();
            }
        });

        exchange();
    }

    private void exchange() {
        class UserLogin2 extends AsyncTask<Void, Void, String> {

            ProgressBar progressBar;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar);

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
                            JSONObject userJson = obj.getJSONObject("data");
                            User4 user2 = new User4(
                                    userJson.getString("currency"),
                                    userJson.getString("to"),
                                    userJson.getString("minimum"),
                                    userJson.getString("rate"),
                                    userJson.getString("rate"),
                                    userJson.getString("rate")
                            );

                            //starting the profile activity
                            currency.setText(user2.getIdAct());
                            idr.setText(user2.getActQr());
                            minimal.setText("Minimal Claim " + user2.getSerCod());
                            rupiah.setText(user2.getIniCod());
                            progressBar.setVisibility(View.GONE);
                            break;

                        default:

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("user_id", "");

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EXCHANGE, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }


    private void postpoin() {

        final String point = poin.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(point)) {
            poin.setError("Please enter Poin");
            poin.requestFocus();
            return;
        }

        class UserLogin2 extends AsyncTask<Void, Void, String> {

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
                            //starting the profile activity



                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Status");
                            builder1.setMessage(userJson);
                            builder1.setCancelable(true);
                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            User user = SharedPrefManager.getInstance(ClaimpointActivity.this).getUser();
                                            String role = user.getRole();
                                            switch(role) {
                                                case "dealer":

                                                    startActivity(new Intent(ClaimpointActivity.this, DealerActivity.class));
                                                    finish();
                                                    break;
                                                default:

                                                    startActivity(new Intent(ClaimpointActivity.this, SalesActivity.class));
                                                    finish();
                                                    break;
                                            }


                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
                            break;

                        default:
                            String message = obj.getString("message");
                            progressBar.setVisibility(View.GONE);
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                            builder2.setTitle("Status");
                            builder2.setMessage(message);
                            builder2.setCancelable(true);
                            builder2.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert2 = builder2.create();
                            alert2.show();
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
                final User user = SharedPrefManager.getInstance(ClaimpointActivity.this).getUser();
                String salt = user.getSalt();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);
                params.put("poin", point);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_CLAIMPOIN, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }


    public void onBackPressed() {
        User user = SharedPrefManager.getInstance(ClaimpointActivity.this).getUser();
        String role = user.getRole();
        switch(role) {
            case "dealer":

                startActivity(new Intent(ClaimpointActivity.this, DealerActivity.class));
                finish();
                break;
            default:

                startActivity(new Intent(ClaimpointActivity.this, SalesActivity.class));
                finish();
                break;
        }
    }
}
