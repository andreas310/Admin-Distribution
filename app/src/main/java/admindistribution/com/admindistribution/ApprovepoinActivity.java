package admindistribution.com.admindistribution;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class ApprovepoinActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<User7> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approvepoint);

        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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

                            //getting the user from the response
                            JSONArray json1 = obj.getJSONArray("data");


                            for (int i = 0 ; i < json1.length(); i++) {
                                JSONObject count = json1.getJSONObject(i);

                                //creating a new user object
                                User7 item = new User7(
                                        count.getString("id"),
                                        count.getString("username"),
                                        count.getString("bank_account_name"),
                                        count.getString("bank_account_number"),
                                        count.getString("bank_name"),
                                        count.getString("bank_code"),
                                        count.getString("status"),
                                        count.getString("created_at"),
                                        count.getString("approved_by"),
                                        count.getString("value")

                                );
                                listItems.add(item);
                            }

//                            for (int i = 0 ; i < json3.length(); i++) {
//                                JSONObject count = json3.getJSONObject(i);
//
//                                //creating a new user object
//                                ShopItem item = new ShopItem(
//                                        count.getString("redeems_status"),
//                                        count.getString("value"),
//                                        count.getString("poin"),
//                                        count.getString("value"),
//                                        count.getString("redeems_at"),
//                                        count.getString("redeems_at")
//
//                                );
//                                listItems.add(item);
//                            }


                            adapter = new MyAdapter9(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            //starting the profile activity

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                final User user = SharedPrefManager.getInstance(ApprovepoinActivity.this).getUser();
                String id = user.getAddress();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("session_id", id);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_APPROVEPOIN, params);





            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }

    public void onBackPressed() {
        finish();
    }

}
