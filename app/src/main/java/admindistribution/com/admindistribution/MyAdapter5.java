package admindistribution.com.admindistribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter5 extends RecyclerView.Adapter<MyAdapter5.ViewHolder> {

    private List<User6> listItems;
    private Context context;

    public MyAdapter5(List<User6> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dealersaleshistory_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User6 listItem = listItems.get(position);

        holder.textnama.setText(listItem.getName());
        holder.textstatus.setText(listItem.getRedeem());
        holder.texttime.setText(listItem.getTime());
        holder.textsales.setText(listItem.getSalesname());
        holder.textpoint.setText("Point : " + listItem.getPoindealer());


        holder.counstrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (listItem.getActqr()){
                    case "null":
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder((Activity) v.getContext());

                        alertDialog.setTitle(listItem.getName());
                        alertDialog.setMessage("Nama Sales : " + listItem.getSalesname() + "\nSerial Code : " + listItem.getSerial()+"\nInitial Code : " + listItem.getInitial() +
                        "\nPoint : " + listItem.getPoindealer() + "\nRedeem Time : " + listItem.getTime() + "\nStatus : " + listItem.getRedeem());


                        alertDialog.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        dialog.cancel();
                                    }
                                });

                        alertDialog.show();

                        break;

                    default:
                        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder((Activity) v.getContext());

                        alertDialog1.setTitle(listItem.getName());
                        alertDialog1.setMessage("Nama Sales : " + listItem.getSalesname() + "\nSerial Code : " + listItem.getSerial()+"\nInitial Code : " + listItem.getInitial() +
                                "\nActivation ID : " + listItem.getActid() + "\nActivation QR : " + listItem.getActqr() + "\nPoint : " + listItem.getPoindealer() + "\nRedeem Time : " + listItem.getTime() + "\nStatus : " + listItem.getRedeem());


                        alertDialog1.setNegativeButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        dialog.cancel();
                                    }
                                });

                        alertDialog1.show();
                        break;
                }

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
