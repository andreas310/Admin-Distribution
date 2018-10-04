package admindistribution.com.admindistribution;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class    Actphone extends AppCompatActivity {
    EditText phonenum;
    TextView activid, activcode;
    private Context context =this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actphone);
        phonenum = (EditText) findViewById(R.id.phonenum);
        activid = (TextView) findViewById(R.id.activid);
        activcode = (TextView) findViewById(R.id.activcode);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneValidate();
                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        findViewById(R.id.activid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(activid.getText());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Auto Copy");
                builder1.setMessage("Check in your clipboard");
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
            }
        });
        findViewById(R.id.activcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(activcode.getText());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setTitle("Auto Copy");
                builder1.setMessage("Check in your clipboard");
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
            }
        });
    }

    private void phoneValidate() {
        //first getting the values
        final String phones = phonenum.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(phones)) {
            phonenum.setError("Please enter your Phone");
            phonenum.requestFocus();
            return;
        }

        class UserLogin2 extends AsyncTask<Void, Void, String> {

            ImageView qrinfo,invalid;
            TextView judul,judul1,judul2,actid,actcode;
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

                qrinfo = (ImageView) findViewById(R.id.qrinfo);
                invalid = (ImageView)findViewById(R.id.invalid);
                judul = (TextView) findViewById(R.id.judul);
                judul1 = (TextView) findViewById(R.id.judul1);
                judul2 = (TextView) findViewById(R.id.judul2);
                actid = (TextView) findViewById(R.id.activid);
                actcode = (TextView) findViewById(R.id.activcode);



                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch (status) {
                        //if no error in response
                        case "1":


                            //getting the user from the response

                            JSONObject json = obj.getJSONObject("data");
                            //JSONObject w = json.getJSONObject("product");


                            //creating a new user object
                            User5 user2 = new User5(
                                    json.getString("activation_code"),
                                    json.getString("activation_id")

                            );

                            progressBar.setVisibility(View.GONE);
                            qrinfo.setVisibility(View.VISIBLE);
                            judul.setVisibility(View.VISIBLE);
                            judul1.setVisibility(View.VISIBLE);
                            judul2.setVisibility(View.VISIBLE);
                            actid.setVisibility(View.VISIBLE);
                            actcode.setVisibility(View.VISIBLE);
                            invalid.setVisibility(View.GONE);

                            activid.setText(user2.getActid());
                            activcode.setText(user2.getActcode());


                            //starting the profile activity


                            break;

                        default:
                            qrinfo.setVisibility(View.GONE);
                            judul.setVisibility(View.GONE);
                            judul1.setVisibility(View.GONE);
                            judul2.setVisibility(View.GONE);
                            actid.setVisibility(View.GONE);
                            actcode.setVisibility(View.GONE);
                            progressBar.setVisibility(View.GONE);
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Error");
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
                User4 activation = SharedPrefManager2.getInstance(Actphone.this).getActiv();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("activation_qr", activation.getActQr());
                params.put("phone", phones );


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ACTREDEEM, params);
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
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }
    }
}
