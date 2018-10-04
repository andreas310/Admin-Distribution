package admindistribution.com.admindistribution;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivationlistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter13 adapter;
    EditText editsearch;

    private List<User4> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activationlist);


        editsearch = (EditText) findViewById(R.id.editsearch);

        //adding a TextChangedListener
        //to call a method whenever there is some change on the EditText
        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        findViewById(R.id.btnsearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editsearch.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editsearch, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        findViewById(R.id.backbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.tambahproduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), AddactivationActivity.class));
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
                                User4 item = new User4(
                                        count.getString("activation_id"),
                                        count.getString("activation_code"),
                                        count.getString("DT_RowId"),
                                        count.getString("created_at"),
                                        count.getString("id"),
                                        count.getString("status")
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


                            adapter = new MyAdapter13(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            //starting the profile activity

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                final User user = SharedPrefManager.getInstance(ActivationlistActivity.this).getUser();
                String id = user.getAddress();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("session_id", id);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_ACTIVATION, params);





            }
        }

        UserLogin ul = new UserLogin();
        ul.execute();
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<User4> filterdlist = new ArrayList<>();

        //looping through existing elements
        for (User4 item : listItems) {
            //if the existing elements contains the search input
            if (item.getIdAct().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdlist.add(item);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filteredlist(filterdlist);
    }


    public void onBackPressed() {
        finish();
    }

}
