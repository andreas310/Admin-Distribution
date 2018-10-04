package admindistribution.com.admindistribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;





    public MyAdapter3(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sales_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);

        holder.textnama.setText(listItem.getRedeemtime());
        holder.textpoin.setText(listItem.getSerialcode());

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

                    String status1 = obj.getString("status");

                    switch (status1) {
                        //if no error in response
                        case "1":


                            //getting the user from the response
                            holder.textstatus.setText(obj.getString("data"));


                            //starting the profile activity


                            break;

                        default:
                            Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("user_id", listItem.getActid());


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_SALESSTATUS2, params);


            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();

        final String status = listItem.getRstatus();
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String statusaction;
                switch (holder.textstatus.getText().toString()) {
                    case "active":
                        statusaction = "inactive";
                        break;
                    default:
                        statusaction = "active";
                        break;
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());
                alertDialog.setTitle("Detail Sales");
                alertDialog.setMessage("Username : " + listItem.getNama() + "\nEmail : " + listItem.getPoin() +
                "\nNama : " + listItem.getRedeemtime() + "\nPhone : " + listItem.getSerialcode() +"\nAddress : " + listItem.getInitial() +
                "\nStatus : " + holder.textstatus.getText());



                alertDialog.setNegativeButton(statusaction,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
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

                                            String status1 = obj.getString("status");

                                            switch(status1) {
                                                //if no error in response
                                                case "1":


                                                    //getting the user from the response
                                                    Toast.makeText(context, obj.getString("data"), Toast.LENGTH_SHORT).show();


                                                    notifyDataSetChanged();

                                                    //starting the profile activity


                                                    break;

                                                default:
                                                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                                        params.put("user_id", listItem.getActid());
                                        params.put("status", statusaction);


                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_SALESSTATUS, params);





                                    }
                                }

                                UserLogin2 ul = new UserLogin2();
                                ul.execute();

                            }
                        });

                alertDialog.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
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
                                                    Toast.makeText(context, obj.getString("data"), Toast.LENGTH_SHORT).show();
                                                    listItems.remove(position);
                                                    notifyItemRemoved(position);
                                                    notifyItemRangeChanged(position,listItems.size());


                                                    //starting the profile activity


                                                    break;

                                                default:
                                                    Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                                        params.put("user_id", listItem.getActid());


                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_DELETESALES, params);





                                    }
                                }

                                UserLogin2 ul = new UserLogin2();
                                ul.execute();

                            }
                        });



            alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textnama;
        public TextView textpoin;
        public TextView textstatus;
        public ProgressBar progressBar;
        public ImageView select;

        public ViewHolder(View itemView) {
            super(itemView);

            textnama = (TextView) itemView.findViewById(R.id.nama);
            textpoin = (TextView) itemView.findViewById(R.id.textStatus);
            textstatus = (TextView) itemView.findViewById(R.id.statustext);
            select = (ImageView) itemView.findViewById(R.id.select);
        }
    }

}
