package admindistribution.com.admindistribution;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        holder.textnama.setText(listItem.getNama());
        holder.textserial.setText(listItem.getSerialcode());
        holder.textpoin.setText(listItem.getPoin());
        holder.textredeemtime.setText(listItem.getRedeemtime());


        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (listItem.getActqr()){
                    case "null":
                        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder((Activity) v.getContext());
                        alertDialog.setTitle(listItem.getNama());
                        alertDialog.setMessage("Serial Code : " + listItem.getSerialcode()+ "\nInitial Code : " +
                                listItem.getInitial() + "\nPoint : "+listItem.getPoin() +"\nStatus : " + listItem.getRstatus());



                        alertDialog.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        dialog.cancel();
                                    }
                                });
                        alertDialog.show();
                        break;
                    default:
                        android.support.v7.app.AlertDialog.Builder alertDialog1 = new android.support.v7.app.AlertDialog.Builder((Activity) v.getContext());
                        alertDialog1.setTitle(listItem.getNama());
                        alertDialog1.setMessage("Serial Code : " + listItem.getSerialcode()+ "\nInitial Code : " +
                                listItem.getInitial() + "\nActivation ID : "+listItem.getActid() + "\nActivation QR : "+
                                listItem.getActqr()+"\nPoint : "+listItem.getPoin() +"\nStatus : " + listItem.getRstatus());



                        alertDialog1.setPositiveButton("OK",
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

        public TextView textnama;
        public TextView textpoin;
        public TextView textserial;
        public TextView textinitial;
        public TextView textredeemtime;
        public ConstraintLayout constraintLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textnama = (TextView) itemView.findViewById(R.id.nama);
            textpoin = (TextView) itemView.findViewById(R.id.poin);
            textredeemtime = (TextView) itemView.findViewById(R.id.time);
            textserial = (TextView) itemView.findViewById(R.id.textStatus);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.constrain);
        }
    }
}
