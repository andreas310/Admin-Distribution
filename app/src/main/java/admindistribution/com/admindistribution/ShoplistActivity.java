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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShoplistActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter14 adapter;
    EditText editsearch;

    private List<User9> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);


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
                startActivity(new Intent(getApplicationContext(), AddshopActivity.class));
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

                            //getting the user from the response
                            JSONArray userJson = obj.getJSONArray("data");

                            for (int i = 0 ; i < userJson.length(); i++) {
                                JSONObject count = userJson.getJSONObject(i);

                                //creating a new user object
                                User9 item = new User9(
                                        count.getString("id"),
                                        count.getString("name"),
                                        count.getString("poin"),
                                        count.getString("description"),
                                        count.getString("picture"),
                                        count.getString("status"),
                                        count.getString("stock")

                                );
                                listItems.add(item);
                            }


                            adapter = new MyAdapter14(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            //starting the profile activity



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                final User user = SharedPrefManager.getInstance(ShoplistActivity.this).getUser();
                String ids = user.getAddress();
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("session_id", ids);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_SHOPLIST, params);





            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<User9> filterdlist = new ArrayList<>();

        //looping through existing elements
        for (User9 item : listItems) {
            //if the existing elements contains the search input
            if (item.getInitial().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdlist.add(item);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filteredlist(filterdlist);
    }

}
