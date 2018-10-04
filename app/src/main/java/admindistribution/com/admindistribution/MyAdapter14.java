package admindistribution.com.admindistribution;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter14 extends RecyclerView.Adapter<MyAdapter14.ViewHolder> {

    private List<User9> listItems;
    private Context context;

    public MyAdapter14(List<User9> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopadmin_card,parent,false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final User9 listItem = listItems.get(position);

        holder.textnama.setText(listItem.getInitial());
        holder.textpoin.setText(listItem.getName() + " Point");
        holder.textdate.setText(listItem.getTime() + " Stock");

        Picasso.with(context)
                .load(listItem.getActid())
                .fit().centerCrop()
                .transform(new RoundedTransformation(25, 0))
                .into(holder.gambar);



        holder.constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                alertDialog.setTitle(listItem.getInitial());
                alertDialog.setMessage("Deskripsi : " + listItem.getPoindealer() + "\nPoin : " + listItem.getName() +
                        "\nGambar : " + listItem.getActid() + "\nStock : " + listItem.getTime() + "\n\nStatus : " + listItem.getActqr());


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
                                        params.put("shop_id", listItem.getSerial());


                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_DELSHOP, params);





                                    }
                                }

                                UserLogin2 ul = new UserLogin2();
                                ul.execute();
                            }
                        });

                alertDialog.setPositiveButton(
                        "Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                Intent intent = new Intent(context, EditshopActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("id", listItem.getSerial());
                                intent.putExtra("name", listItem.getInitial());
                                intent.putExtra("descrip", listItem.getPoindealer());
                                intent.putExtra("poin", listItem.getName());
                                intent.putExtra("stock", listItem.getTime());
                                intent.putExtra("status", listItem.getActqr());
                                context.startActivity(intent);
                            }
                        }
                );


            alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ConstraintLayout constraint;
        public TextView textnama;
        public TextView textpoin;
        public TextView textdate;
        public ImageView gambar;
        public ImageView gambar2;
        public Button btnclaim;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            constraint = (ConstraintLayout) itemView.findViewById(R.id.constrain);
            textnama = (TextView) itemView.findViewById(R.id.nama);
            textpoin = (TextView) itemView.findViewById(R.id.poin);
            textdate = (TextView) itemView.findViewById(R.id.textdate);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            gambar2 = (ImageView) itemView.findViewById(R.id.gambar2);
            btnclaim = (Button) itemView.findViewById(R.id.btnclaim);
        }


    }



    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filteredlist(ArrayList<User9> filterdlist) {
        this.listItems = filterdlist;
        notifyDataSetChanged();
    }
}
