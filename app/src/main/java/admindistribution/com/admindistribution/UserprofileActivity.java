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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;


public class UserprofileActivity extends AppCompatActivity {

    ProgressBar progressBar;
    private Context context = this;
    EditText oldpass,newpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        User user = SharedPrefManager.getInstance(this).getUser();
        User2 user2 = SharedPrefManager2.getInstance(this).getUser();

        String username =user.getUsername();
        String email =user.getEmail();


        TextView useredit = (TextView)findViewById(R.id.useredit);
        TextView emailview = (TextView) findViewById(R.id.emailview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);


        useredit.setText(username);
        emailview.setText(email);

        findViewById(R.id.textbank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getchoice();
            }
        });

        findViewById(R.id.btnchange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postchange();
            }
        });
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserprofileActivity.this, SettingActivity.class));
                finish();
            }
        });


        gettoko();

    }

    private void getacoun() {
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
                            JSONObject userJson = obj.getJSONObject("data");

                            String bankid = userJson.getString("bank_id");
                            String acounnum = userJson.getString("account_number");
                            String acounname = userJson.getString("account_name");

                            EditText acounnum1 =  (EditText)findViewById(R.id.acounnum);
                            EditText acounname1 = (EditText)findViewById(R.id.acounname);
                            TextView bankid1 =(TextView)findViewById(R.id.textbank);


                            acounnum1.setText(acounnum);
                            acounname1.setText(acounname);
                            bankid1.setText(bankid);

                            getbank();
                            //starting the profile activity

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
                User user = SharedPrefManager.getInstance(UserprofileActivity.this).getUser();
                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_USERBANK, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    private void getbank() {
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
                            JSONArray userJson = obj.getJSONArray("data");
                            for (int i = 0 ; i < userJson.length(); i++) {
                                JSONObject count = userJson.getJSONObject(i);
                                String item;
                                item = count.getString("id");
                                TextView idrec = (TextView)findViewById(R.id.idrec);
                                idrec.setText(item);
                                TextView bankid1 =(TextView)findViewById(R.id.textbank);
                                String bankid2 = bankid1.getText().toString();
                                if (Objects.equals(item,bankid2)){
                                    bankid1.setText(count.getString("short_name"));
                               }
                               else {

                                }
                            }



                            //starting the profile activity

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
                return requestHandler.sendPostRequest(URLs.URL_BANK, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    private void gettoko() {
        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.GONE);
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONObject json1 = obj.getJSONObject("data");
                            JSONObject userJson2 = json1.getJSONObject("user");
                            JSONObject userJson = json1.getJSONObject("dealer");
                            String toko = userJson.getString("name");
                            String phone = userJson.getString("phone");
                            String address = userJson.getString("address");
                            String nama = userJson2.getString("name");
                            String phoneuser = userJson2.getString("phone");
                            String addressuser = userJson2.getString("address");

                            TextView tokoview = (TextView)findViewById(R.id.tokoview);
                            TextView phonetokoview = (TextView)findViewById(R.id.phonetokoview);
                            TextView addresstoko = (TextView)findViewById(R.id.addresstoko);
                            EditText phoneedit = (EditText) findViewById(R.id.phoneedit);
                            EditText addressedit = (EditText)findViewById(R.id.addressedit);
                            EditText namaedit = (EditText) findViewById(R.id.namaedit);

                            tokoview.setText(toko);
                            phonetokoview.setText(phone);
                            addresstoko.setText(address);
                            phoneedit.setText(phoneuser);
                            namaedit.setText(nama);
                            addressedit.setText(addressuser);

                            //starting the profile activity
                            getacoun();
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
                User user = SharedPrefManager.getInstance(UserprofileActivity.this).getUser();
                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_USERDETAIL, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }



    private void getchoice() {
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
                            AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
                            alertDialog1.setTitle("Pilih Bank Anda");
                            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(UserprofileActivity.this, android.R.layout.select_dialog_singlechoice);

                            //getting the user from the response
                            JSONArray userJson = obj.getJSONArray("data");
                            final String[] conten =new String[userJson.length()];
                            final String[] ids =new String[userJson.length()];
                            for (int i = 0 ; i < userJson.length(); i++) {
                                JSONObject count = userJson.getJSONObject(i);
                                String bankchoice = count.getString("short_name");
                                String idbank = count.getString("id");
                                conten[i] = bankchoice;
                                ids[i] = idbank;


                            }

                            alertDialog1.setItems(conten, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String item = conten[which];
                                    TextView textbank =(TextView)findViewById(R.id.textbank);
                                    textbank.setText(item);

                                    String item2 = ids[which];
                                    TextView idrec = (TextView)findViewById(R.id.idrec);
                                    idrec.setText(item2);
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
                            //starting the profile activity

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
                User user = SharedPrefManager.getInstance(UserprofileActivity.this).getUser();
                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_BANK, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }


    private void postchange() {
        //first getting the values
        final EditText name = (EditText)findViewById(R.id.namaedit);
        final EditText oldpass = (EditText)findViewById(R.id.oldpass);
        final EditText newpass = (EditText) findViewById(R.id.newpass);
        final EditText phone = (EditText) findViewById(R.id.phoneedit);
        final EditText address = (EditText) findViewById(R.id.addressedit);
        final EditText acounnum = (EditText) findViewById(R.id.acounnum);
        final EditText acounname = (EditText) findViewById(R.id.acounname);
        final TextView idrec = (TextView)findViewById(R.id.idrec);
        if (TextUtils.isEmpty(oldpass.getText())) {
            oldpass.setError("Please enter your old password");
            oldpass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(newpass.getText())) {
            newpass.setError("Please enter your new password");
            newpass.requestFocus();
            return;
        }

        class UserLogin2 extends AsyncTask<Void, Void, String> {



            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressBar = (ProgressBar) findViewById(R.id.progressBar3);
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");

                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            startActivity(new Intent(getApplicationContext(), SalesActivity.class));
                            Toast.makeText(getApplicationContext(), obj.getString("data"), Toast.LENGTH_SHORT).show();
                            finish();

                            //starting the profile activity

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
                User user = SharedPrefManager.getInstance(UserprofileActivity.this).getUser();

                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);
                params.put("name", name.getText().toString());
                params.put("old_password", oldpass.getText().toString());
                params.put("new_password", newpass.getText().toString());
                params.put("phone", phone.getText().toString());
                params.put("address", address.getText().toString());
                params.put("bank_id", idrec.getText().toString());
                params.put("account_number", acounnum.getText().toString());
                params.put("account_name", acounname.getText().toString());

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EDITPROFILE, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }





    public void onBackPressed() {
        startActivity(new Intent(this, SettingActivity.class));
        finish();
    }



}
