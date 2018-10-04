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
import android.widget.ArrayAdapter;
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

import java.util.HashMap;
import java.util.List;

public class SalesActivity extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    TextView textname, textrole, textpoint,nonotif;
    SliderLayout sliderLayout;
    HashMap<String, String> Hash_file_maps;
    private List<ListItem2> listItems;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sliderurl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);


        textname = (TextView) findViewById(R.id.textname);
        textrole = (TextView) findViewById(R.id.textrole);
        textpoint = (TextView) findViewById(R.id.textpoint);
        nonotif =(TextView) findViewById(R.id.textnotif);


        //setting the values to the textviews


        findViewById(R.id.buttonRedeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
                alertDialog1.setTitle("Pilih cara redeem Anda");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SalesActivity.this, android.R.layout.select_dialog_singlechoice);
                final String[] conten = {"Scan with Camera","Input Manual"};
                alertDialog1.setItems(conten, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (conten[which] == "Scan with Camera"){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ScanActivity.class));
                        }
                        else if (conten[which] == "Input Manual"){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ManualActivity.class));
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

        findViewById(R.id.btnValidate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(context);
                alertDialog1.setTitle("Pilih cara redeem Anda");
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SalesActivity.this, android.R.layout.select_dialog_singlechoice);
                final String[] conten = {"Scan with Camera","Input Manual"};
                alertDialog1.setItems(conten, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (conten[which] == "Scan with Camera"){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ScanActivity2.class));
                        }
                        else if (conten[which] == "Input Manual"){
                            finish();
                            startActivity(new Intent(getApplicationContext(), Manual2Activity.class));
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

        findViewById(R.id.btnSalesHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
            }
        });

        findViewById(R.id.buttonClaims).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), ClaimpointActivity.class));
            }
        });

        findViewById(R.id.buttonShops).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShopActivity.class));
                finish();
            }
        });

        findViewById(R.id.btnClaimHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(getApplicationContext(), HistoryclaimActivity.class));
            }
        });

        findViewById(R.id.btnsetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
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

    private void poin() {
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

                    switch (status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONObject userJson = obj.getJSONObject("data");

                            //creating a new user object


                            textpoint.setText(userJson.getString("total_poin"));
                            nama();

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
                final User user = SharedPrefManager.getInstance(SalesActivity.this).getUser();
                String salt = user.getSalt();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_POIN, params);


            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
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

                    switch (status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONArray userJson = obj.getJSONArray("data");
                            String slide1 = userJson.getString(0);
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

                                TextSliderView textSliderView = new TextSliderView(SalesActivity.this);
                                textSliderView
                                        .image(Hash_file_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(SalesActivity.this);
                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);
                                sliderLayout.addSlider(textSliderView);
                            }
                            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            sliderLayout.setDuration(3000);
                            sliderLayout.addOnPageChangeListener(SalesActivity.this);
                            poin();

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

    private void nama() {
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
                    JSONObject objs = new JSONObject(s);

                    String status = objs.getString("status");

                    switch (status) {
                        //if no error in response
                        case "1":

                            //getting the user from the response
                            JSONObject userJson = objs.getJSONObject("data");
                            JSONObject userJson2 = userJson.getJSONObject("user");
                            textname.setText(userJson2.getString("name"));

                            JSONObject userJson3 = userJson.getJSONObject("dealer");
                            String toko = userJson3.getString("id");
                            //creating a new user object
                            User2 user2 = new User2(
                                    userJson2.getString("name")
                            );
                            SharedPrefManager2.getInstance(getApplicationContext()).userLogin(user2);

                            textrole.setText(userJson3.getString("name"));


                            User users = SharedPrefManager.getInstance(SalesActivity.this).getUser();
                            String role = users.getRole().toString();
                            String username = users.getUsername().toString();
                            JSONObject json = new JSONObject();
                            json.put("role", role);
                            json.put("username", username);
                            json.put("id_dealer", toko);
                            OneSignal.sendTags(json);

                            notif();
                            //starting the profile activity

                            break;

                        default:

                            Toast.makeText(getApplicationContext(), objs.getString("message"), Toast.LENGTH_SHORT).show();
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                final User user = SharedPrefManager.getInstance(SalesActivity.this).getUser();
                String salt = user.getSalt();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

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
                            JSONArray userJson = obj.getJSONArray("data");
                            String data = String.valueOf(userJson.length());

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
                User user = SharedPrefManager.getInstance(SalesActivity.this).getUser();
                String salt = user.getSalt();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GETNOTIF, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }


}
