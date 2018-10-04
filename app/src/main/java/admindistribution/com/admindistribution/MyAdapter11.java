package admindistribution.com.admindistribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter11 extends RecyclerView.Adapter<MyAdapter11.ViewHolder> {

    private List<User8> listItems;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public MyAdapter11(List<User8> listItems, Context context) {
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
        final User8 listItem = listItems.get(position);

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
                params.put("user_id", listItem.getTesting());


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_SALESSTATUS2, params);


            }
        }

        UserLogin2 ul = new UserLogin2();
        ul.execute();

        holder.textnama.setText(listItem.getName());
        holder.textpoin.setText(listItem.getInitial());
        final String statusaction;
        switch (holder.textstatus.getText().toString()) {
            case "active":
                statusaction = "inactive";
                break;
            default:
                statusaction = "active";
                break;
        }

        holder.textnama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        params.put("user_id", listItem.getTesting());
                        params.put("status", statusaction);


                        //returing the response
                        return requestHandler.sendPostRequest(URLs.URL_SALESSTATUS, params);





                    }
                }

                UserLogin2 ul = new UserLogin2();
                ul.execute();
            }
        });
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());
                alertDialog.setTitle(listItem.getName());
                alertDialog.setMessage("Phone : " + listItem.getPoindealer() + "\nAddress : " + listItem.getActid() +
                        "\n\nNama User : " + listItem.getInitial() + "\nEmail : " + listItem.getRedeem() + "\nPhone : " + listItem.getTest() +"\nAddress : " + listItem.getSalesname());



                alertDialog.setNegativeButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(context, EditdealerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("posi", String.valueOf(position));
                                intent.putExtra("id", listItem.getSerial());
                                intent.putExtra("salt", listItem.getTesting());
                                intent.putExtra("user_name", listItem.getInitial());
                                intent.putExtra("user_address", listItem.getSalesname());
                                intent.putExtra("user_phone", listItem.getTest());
                                intent.putExtra("user_username", listItem.getActqr());
                                intent.putExtra("dealer_name", listItem.getName());
                                intent.putExtra("dealer_address", listItem.getActid());
                                intent.putExtra("dealer_phone", listItem.getPoindealer());
                                intent.putExtra("created_at", listItem.getTime());
                                intent.putExtra("user_email", listItem.getRedeem());
                                context.startActivity(intent);


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
                                        params.put("id", listItem.getSerial());


                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_DELETEDEALER, params);





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
