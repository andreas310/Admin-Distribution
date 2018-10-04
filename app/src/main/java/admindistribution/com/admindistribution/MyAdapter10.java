package admindistribution.com.admindistribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class MyAdapter10 extends RecyclerView.Adapter<MyAdapter10.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    Context c;





    public MyAdapter10(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.approve_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListItem listItem = listItems.get(position);

        holder.textnama.setText(listItem.getActid());
        holder.textstatus.setText(listItem.getRedeemtime());
        holder.texttime.setText(listItem.getActqr());
        holder.textsales.setText(listItem.getPoin());
        holder.textpoint.setText(listItem.getSerialcode() + " Poin");


        holder.counstrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                        alertDialog.setTitle(listItem.getInitial());
                        alertDialog.setMessage("Username : " + listItem.getPoin() + "\nBarang : " + listItem.getActid()+"\nPoin : " + listItem.getSerialcode() +
                                "\nDi Claim Oleh : " + listItem.getRstatus() +  "\nRedeem Time : " + listItem.getActqr() + "\nStatus : " + listItem.getRedeemtime());


                        alertDialog.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                        if(listItem.getRstatus() == "null") {
                            alertDialog.setPositiveButton("Accept",
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
                                                                Toast.makeText(context, obj.getString("data"), Toast.LENGTH_LONG).show();

                                                                //starting the profile activity
                                                                notifyDataSetChanged();


                                                                break;

                                                            default:
                                                                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_LONG).show();
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
                                                    final User user = SharedPrefManager.getInstance(context).getUser();
                                                    String id = user.getAddress();

                                                    //creating request parameters
                                                    HashMap<String, String> params = new HashMap<>();
                                                    params.put("redeem_id", listItem.getNama());
                                                    params.put("session_id", id);

                                                    //returing the response
                                                    return requestHandler.sendPostRequest(URLs.URL_APPROVE, params);





                                                }
                                            }

                                            UserLogin2 ul = new UserLogin2();
                                            ul.execute();
                                        }
                                    });
                        }
                        else{
                            alertDialog.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to execute after dialog
                                            dialog.cancel();
                                        }
                                    });
                        }


                        alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout counstrain;
        public TextView textnama;
        public TextView textstatus;
        public TextView texttime;
        public TextView textsales;
        public TextView textpoint;
        public Button btnclaim;

        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            counstrain = (ConstraintLayout) itemView.findViewById(R.id.constrain);
            textnama = (TextView) itemView.findViewById(R.id.nama);
            textstatus = (TextView) itemView.findViewById(R.id.textStatus);
            texttime = (TextView) itemView.findViewById(R.id.texttime);
            textsales = (TextView) itemView.findViewById(R.id.textSales);
            textpoint = (TextView) itemView.findViewById(R.id.textpoinsaleshis);
            btnclaim = (Button) itemView.findViewById(R.id.btnclaim);
        }
    }

}
