package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class AdminActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{

    TextView textname, textrole, textpoint, nonotif;
    Button btnValidate,btnClaim, btnShop;
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;
    private List<User7> listItems;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        User users = SharedPrefManager.getInstance(this).getUser();
        String role = users.getRole().toString();
        OneSignal.sendTag("role", role);
        sliderurl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        listItems = new ArrayList<>();

        //getting the current user
        final User user = SharedPrefManager.getInstance(this).getUser();
        User2 user2 = SharedPrefManager.getInstance(this).getPoint();





        textname = (TextView) findViewById(R.id.textname);
        textrole = (TextView) findViewById(R.id.textrole);
        nonotif = (TextView) findViewById(R.id.textnotif);
        btnValidate = (Button)findViewById(R.id.btnValidate);
        btnClaim = (Button)findViewById(R.id.buttonClaims);
        btnShop = (Button)findViewById(R.id.buttonShops);

        textrole.setText(user.getRole());
//        textrole.setText(getApplicationContext().getPackageName());



        //setting the values to the textviews



        findViewById(R.id.buttonRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DealerlistActivity.class));

            }
        });


        switch (user.getRole()){
            case "superadmin":
                btnValidate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), AdminlistActivity.class));
                    }
                });

                break;
            default:
                btnValidate.setVisibility(View.INVISIBLE);
                break;
        }

        switch (user.getRole()){
            case "superadmin":
                btnClaim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ApprovepoinActivity.class));
                    }
                });

                break;
            default:
                btnClaim.setVisibility(View.INVISIBLE);
                break;
        }

        switch (user.getRole()){
            case "superadmin":
                btnShop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ApproveshopActivity.class));
                    }
                });

                break;
            default:
                btnShop.setVisibility(View.INVISIBLE);
                break;
        }

        findViewById(R.id.btnSalesHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProductlistActivity.class));
            }
        });


        findViewById(R.id.btnClaimHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ActivationlistActivity.class));
            }
        });

        findViewById(R.id.gambarnotif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), NotifActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnsetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShoplistActivity.class));
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        findViewById(R.id.btnsettingadmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), SettinglistActivity.class));
                finish();
//                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 10000ms
                switch (textname.getText().toString()){
                    case "nama":
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                        builder1.setTitle("Connection");
                        builder1.setMessage("Koneksi Error/Server Error\nPastikan Koneksi anda baik/hubungi pihak admin");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        finish();
                                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        break;
                    default:
                        break;
                }
            }
        }, 10000);


    }

    @Override
    protected void onStop() {


        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

//        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    private void sliderurl() {
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
                            String slide1 =  userJson.getString(0);
                            String slide2 = userJson.getString(1);
                            String slide3 = userJson.getString(2);
                            String slide4 = userJson.getString(3);
                            String slide5 = userJson.getString(4);

                            Hash_file_maps = new HashMap<String, String>();

                            sliderLayout = (SliderLayout) findViewById(R.id.slider);

                            Hash_file_maps.put("Android CupCake", slide1);
                            Hash_file_maps.put("Android Donut", slide2);
                            Hash_file_maps.put("Android Eclair", slide3);
                            Hash_file_maps.put("Android Eclair", slide4);
                            Hash_file_maps.put("Android Eclair", slide5);

                            for (String name : Hash_file_maps.keySet()) {

                                TextSliderView textSliderView = new TextSliderView(AdminActivity.this);
                                textSliderView
                                        .image(Hash_file_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(AdminActivity.this);
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);
                                sliderLayout.addSlider(textSliderView);
                            }
                            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            sliderLayout.setDuration(3000);
                            sliderLayout.addOnPageChangeListener(AdminActivity.this);
                            gettoko();

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
                return requestHandler.sendPostRequest(URLs.URL_SLIDE, params);





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

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    String status = obj.getString("status");



                    switch(status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONObject json1 = obj.getJSONObject("data");
                            JSONObject json3 = json1.getJSONObject("user");


                            String nama = json3.getString("name");
                            User users = SharedPrefManager.getInstance(AdminActivity.this).getUser();
                            String role = users.getRole().toString();
                            String username = users.getUsername().toString();
                            JSONObject json = new JSONObject();
                            json.put("role", role);
                            json.put("username", username);
                            json.put("id_dealer", "admin");
                            OneSignal.sendTags(json);

                            User2 user = new User2("admin");
                            SharedPrefManager2.getInstance(getApplicationContext()).userLogin(user);

                            textname.setText(nama);

                            notif();


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
                User user = SharedPrefManager.getInstance(AdminActivity.this).getUser();
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



    private void notif() {
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
                            String data = obj.getString("data");

                            nonotif.setText(data);

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
                User user = SharedPrefManager.getInstance(AdminActivity.this).getUser();
                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GETNOTIFCOUNT, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }





}
