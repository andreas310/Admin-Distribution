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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private List<ShopItem> listItems;
    private Context context;

    public MyAdapter2(List<ShopItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_card,parent,false);
        return new ViewHolder(v);

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ShopItem listItem = listItems.get(position);

        holder.textnama.setText(listItem.getNama());
        holder.textpoin.setText(listItem.getPoin() + " Point");

        Picasso.with(context)
                .load(listItem.getPicture())
                .fit().centerCrop()
                .transform(new RoundedTransformation(25, 0))
                .into(holder.gambar);



        holder.btnclaim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                alertDialog.setTitle("Description");
                alertDialog.setMessage(listItem.getDescription()+"\n\nStatus : " + listItem.getStatus());


                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Write your code here to execute after dialog
                                dialog.cancel();
                            }
                        });

                alertDialog.setPositiveButton(
                        "Buy",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

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
                                        final User user = SharedPrefManager.getInstance(context).getUser();
                                        String salt = user.getSalt();
                                        String shop = listItem.getId();
                                        //creating request handler object
                                        RequestHandler requestHandler = new RequestHandler();

                                        //creating request parameters
                                        HashMap<String, String> params = new HashMap<>();
                                        params.put("user_id", salt);
                                        params.put("shop_id", shop);

                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_CLAIMSHOP, params);





                                    }
                                }

                                UserLogin2 ul = new UserLogin2();
                                ul.execute();
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

        public TextView textnama;
        public TextView textpoin;
        public ImageView gambar;
        public ImageView gambar2;
        public Button btnclaim;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            textnama = (TextView) itemView.findViewById(R.id.nama);
            textpoin = (TextView) itemView.findViewById(R.id.poin);
            gambar = (ImageView) itemView.findViewById(R.id.gambar);
            gambar2 = (ImageView) itemView.findViewById(R.id.gambar2);
            btnclaim = (Button) itemView.findViewById(R.id.btnclaim);
        }


    }
}
