package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Userpointredeem extends AppCompatActivity {

    private Context context = this;
    TextView status,wes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        userLogin();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpointredeem);
    }
    private void userLogin() {

        class UserLogin extends AsyncTask<Void, Void, String> {



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




                            //storing the user in shared preferences
                            startActivity(new Intent(getApplicationContext(), SalesActivity.class));

                            finish();

                            //starting the profile activity


                            break;

                        default:

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                            builder1.setTitle("Error");
                            builder1.setMessage(obj.getString("message"));
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            finish();
                                            startActivity(new Intent(getApplicationContext(), SalesActivity.class));
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
                User user = SharedPrefManager.getInstance(Userpointredeem.this).getUser();
                String salt = user.getSalt();
                User3 id = SharedPrefManager2.getInstance(Userpointredeem.this).getbar();
                String we = id.getId();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("product_id",we);
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REDEEMPOIN, params);
            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }
}
