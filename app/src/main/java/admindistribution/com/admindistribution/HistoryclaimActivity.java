package admindistribution.com.admindistribution;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryclaimActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ShopItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyclaim);

        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = SharedPrefManager.getInstance(HistoryclaimActivity.this).getUser();
                String role = user.getRole();
                switch(role) {
                    case "dealer":

                        startActivity(new Intent(HistoryclaimActivity.this, DealerActivity.class));
                        finish();
                        break;
                    default:

                        startActivity(new Intent(HistoryclaimActivity.this, SalesActivity.class));
                        finish();
                        break;
                }
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

//        for (int i =0; i<=10;i++){
//            ListItem listItem = new ListItem(
//              "keyboard" + i+1,
//                    "10000",
//                    "21-12-12",
//                    "uoiu98",
//                    "iq9ue9932"
//            );
//            listItems.add(listItem);
//        }
//
//        adapter = new MyAdapter(listItems,this);
//        recyclerView.setAdapter(adapter);
        hist();

    }

    private void hist() {
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
                            JSONObject json1 = obj.getJSONObject("data");
                            JSONArray json2 = json1.getJSONArray("shops");
                            JSONArray json3 = json1.getJSONArray("poin");

                            for (int i = 0 ; i < json2.length(); i++) {
                                JSONObject count = json2.getJSONObject(i);

                                //creating a new user object
                                ShopItem item = new ShopItem(
                                        count.getString("redeems_status"),
                                        count.getString("shops_name"),
                                        count.getString("poin"),
                                        count.getString("shops_description"),
                                        count.getString("redeems_at"),
                                        count.getString("shops_picture")

                                );
                                listItems.add(item);
                            }

                            for (int i = 0 ; i < json3.length(); i++) {
                                JSONObject count = json3.getJSONObject(i);

                                //creating a new user object
                                ShopItem item = new ShopItem(
                                        count.getString("redeems_status"),
                                        count.getString("value"),
                                        count.getString("poin"),
                                        count.getString("value"),
                                        count.getString("redeems_at"),
                                        count.getString("redeems_at")

                                );
                                listItems.add(item);
                            }


                            adapter = new MyAdapter4(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
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
                final User user = SharedPrefManager.getInstance(HistoryclaimActivity.this).getUser();
                String salt = user.getSalt();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", salt);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_HISTCLAIM, params);





            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    public void onBackPressed() {
        User user = SharedPrefManager.getInstance(HistoryclaimActivity.this).getUser();
        String role = user.getRole();
        switch(role) {
            case "dealer":

                startActivity(new Intent(HistoryclaimActivity.this, DealerActivity.class));
                finish();
                break;
            default:

                startActivity(new Intent(HistoryclaimActivity.this, SalesActivity.class));
                finish();
                break;
        }
    }

}
