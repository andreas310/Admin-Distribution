package admindistribution.com.admindistribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter12 extends RecyclerView.Adapter<MyAdapter12.ViewHolder>  {

    private List<User6> listItems;
    private Context context;
    Context c;


    public MyAdapter12(List<User6> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User6 listItem = listItems.get(position);

        holder.textnama.setText(listItem.getName());
        holder.textstatus.setText(listItem.getSerial());
        holder.texttime.setText(listItem.getTime());


        holder.counstrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                        AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                        if(listItem.getActid() != "null") {
                            alertDialog.setTitle(listItem.getName());
                            alertDialog.setMessage("Serial Code : " + listItem.getSerial() + "\nInitial Code : " + listItem.getInitial() + "\nPoin Dealer : " + listItem.getPoindealer() +
                                    "\nPoin Sales : " + listItem.getSalesname() + "\nActivation ID : " + listItem.getActid() + "\nActivation QR : " + listItem.getActqr() + "\nStatus : " + listItem.getTime());
                        }
                        else {
                            alertDialog.setTitle(listItem.getName());
                            alertDialog.setMessage("Serial Code : " + listItem.getSerial() + "\nInitial Code : " + listItem.getInitial() + "\nPoin Dealer : " + listItem.getPoindealer() +
                                    "\nPoin Sales : " + listItem.getSalesname() + "\nStatus : " + listItem.getTime());
                        }


                        alertDialog.setNegativeButton("Delete",
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
                                        User user = SharedPrefManager.getInstance(context).getUser();
                                        String id = user.getAddress();
                                        //creating request parameters
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("session_id", id);
                                        params.put("product_id", listItem.getRedeem());


                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_DELETEPRODUCT, params);





                                    }
                                }

                                UserLogin2 ul = new UserLogin2();
                                ul.execute();
                            }
                        });

                            alertDialog.setPositiveButton("Edit",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to execute after dialog
                                            Intent intent = new Intent(context, EditproductActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.putExtra("product_id", listItem.getRedeem());
                                            intent.putExtra("serial_code", listItem.getSerial());
                                            intent.putExtra("initial_code", listItem.getInitial());
                                            intent.putExtra("name", listItem.getName());
                                            intent.putExtra("activation_id", listItem.getActid());
                                            intent.putExtra("activation_qr", listItem.getActqr());
                                            intent.putExtra("poin_sales", listItem.getSalesname());
                                            intent.putExtra("poin_dealer", listItem.getPoindealer());
                                            intent.putExtra("status", listItem.getTime());
                                            context.startActivity(intent);
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

        public ConstraintLayout counstrain;
        public TextView textnama;
        public TextView textstatus;
        public TextView texttime;
        public TextView textpoint;
        public Button btnclaim;

        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            counstrain = (ConstraintLayout) itemView.findViewById(R.id.constrain);
            textnama = (TextView) itemView.findViewById(R.id.nama);
            textstatus = (TextView) itemView.findViewById(R.id.textStatus);
            texttime = (TextView) itemView.findViewById(R.id.texttime);
            textpoint = (TextView) itemView.findViewById(R.id.textpoinsaleshis);
            btnclaim = (Button) itemView.findViewById(R.id.btnclaim);
        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filteredlist(ArrayList<User6> filterdlist) {
        this.listItems = filterdlist;
        notifyDataSetChanged();
    }







}
