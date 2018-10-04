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

public class MyAdapter8 extends RecyclerView.Adapter<MyAdapter8.ViewHolder> {

    private List<User6> listItems;
    private Context context;

    public MyAdapter8(List<User6> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notif_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User6 listItem = listItems.get(position);

        holder.textnama.setText(listItem.getName());
        holder.textdescrip.setText(listItem.getInitial());


        holder.counstrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                        alertDialog.setTitle(listItem.getName());
                        alertDialog.setMessage("Subject : " + listItem.getName() + "\nto : " + listItem.getPoindealer()+"\nby : " + listItem.getActqr() +
                        "\nStatus : " + listItem.getSalesname() + "\nRedeem Time : " + listItem.getTime());


                        alertDialog.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        dialog.cancel();
                                    }
                                });

                        alertDialog.show();

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
                                        params.put("notification_id", listItem.getSerial());


                                        //returing the response
                                        return requestHandler.sendPostRequest(URLs.URL_READNOTIF, params);





                                    }
                                }

                                UserLogin2 ul = new UserLogin2();
                                ul.execute();
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
        public TextView textdescrip;
        public TextView texttime;
        public TextView textsales;
        public TextView textpoint;
        public Button btnclaim;
        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            counstrain = (ConstraintLayout) itemView.findViewById(R.id.constrain);
            textnama = (TextView) itemView.findViewById(R.id.nama);
            textdescrip = (TextView) itemView.findViewById(R.id.descrip);

        }
    }
}
