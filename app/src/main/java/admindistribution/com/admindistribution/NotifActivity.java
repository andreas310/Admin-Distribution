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

public class NotifActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<User6> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();
        hist();
    }

    private void hist() {
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
                            JSONArray userJson = obj.getJSONArray("data");

                            for (int i = 0; i < userJson.length(); i++) {
                                JSONObject count = userJson.getJSONObject(i);

                                //creating a new user object
                                User6 item = new User6(
                                        count.getString("id"),
                                        count.getString("by"),
                                        count.getString("subject"),
                                        count.getString("to"),
                                        count.getString("picture"),
                                        count.getString("by"),
                                        count.getString("created_at"),
                                        count.getString("type"),
                                        count.getString("status")
                                );
                                listItems.add(item);
                            }

                            adapter = new MyAdapter8(listItems, getApplicationContext());
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
                User user = SharedPrefManager.getInstance(NotifActivity.this).getUser();
                String id = user.getSalt();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("user_id", id);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_GETNOTIF, params);


            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }

    public void onBackPressed() {
        startActivity(new Intent(this, ProfileActivity.class));
        finish();
    }

}
