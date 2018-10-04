package admindistribution.com.admindistribution;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class AddsalesActivity extends AppCompatActivity {

    EditText edituser,editpass,editconfirm,editnama,editphone,editaddress, editemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addsales);

        edituser = (EditText) findViewById(R.id.edituser);
        editpass = (EditText) findViewById(R.id.editpass);
        editconfirm = (EditText) findViewById(R.id.editconfirm);
        editnama = (EditText) findViewById(R.id.editnama);
        editphone = (EditText) findViewById(R.id.editphone);
        editaddress = (EditText) findViewById(R.id.editaddress);
        editemail = (EditText) findViewById(R.id.editemail);

        findViewById(R.id.btnadd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               post();
            }
        });
    }

    private void post() {
        //first getting the values
        final String username = edituser.getText().toString();
        final String password = editpass.getText().toString();
        final String confirm = editconfirm.getText().toString();
        final String nama = editnama.getText().toString();
        final String phone = editphone.getText().toString();
        final String address = editaddress.getText().toString();
        final String email = editemail.getText().toString();


        //validating inputs
        if (TextUtils.isEmpty(username)) {
            edituser.setError("Please enter sales username");
            edituser.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editpass.setError("Please enter sales password");
            editpass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(confirm)) {
            editconfirm.setError("Please Confirm sales password");
            editconfirm.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(nama)) {
            editnama.setError("Please enter sales Name");
            editnama.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            editphone.setError("Please enter sales telp");
            editphone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)) {
            editaddress.setError("Please enter sales address");
            editaddress.requestFocus();
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

                            Toast.makeText(getApplicationContext(), userJson, Toast.LENGTH_SHORT).show();
                            finish();

                            //starting the profile activity
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

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
                User2 user = SharedPrefManager.getInstance(AddsalesActivity.this).getPoint();
                String id = user.getPoin();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("dealer_id", id);
                params.put("name", nama);
                params.put("email", email);
                params.put("username", username);
                params.put("password", password);
                params.put("confirm_password", confirm);
                params.put("phone", phone);
                params.put("address", address);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTERSALES, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }


}
