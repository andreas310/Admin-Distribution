package admindistribution.com.admindistribution;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by POPO on 2/4/2018.
 */

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.ViewHolder> {

    private List<ShopItem> listItems;
    private Context context;
    Context c;





    public MyAdapter4(List<ShopItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.claim_card,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ShopItem listItem = listItems.get(position);
        ;
        if(listItem.getPicture() == listItem.getStatus())
        {
            Picasso.with(context)
                    .load("http://wis.co.id/images/no-image.png")
                    .fit().centerCrop()
                    .into(holder.imagehis);
            holder.textnama.setText("Rp." + listItem.getNama());
            holder.textpoin.setText(listItem.getPoin());
            holder.textdes.setText("Claim Poin");
            holder.textdate.setText(listItem.getPicture());
            holder.textstatus.setText(listItem.getId());
        }
        else {
            if (listItem.getStatus() == "null"){
                Picasso.with(context)
                        .load("http://wis.co.id/images/no-image.png")
                        .fit().centerCrop()
                        .into(holder.imagehis);
            }
            else {
                Picasso.with(context)
                        .load(listItem.getStatus())
                        .fit().centerCrop()
                        .into(holder.imagehis);
            }
            holder.textnama.setText(listItem.getNama());
            holder.textpoin.setText(listItem.getPoin());
            holder.textdes.setText(listItem.getDescription());
            holder.textdate.setText(listItem.getPicture());
            holder.textstatus.setText(listItem.getId());
        }




    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textnama;
        public TextView textpoin;
        public TextView textdes;
        public TextView textstatus;
        public TextView textdate;
        public ImageView imagehis;

        public ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            textnama = (TextView) itemView.findViewById(R.id.nama);
            textpoin = (TextView) itemView.findViewById(R.id.textpoinclaimhis);
            textdes = (TextView) itemView.findViewById(R.id.description);
            textdate = (TextView) itemView.findViewById(R.id.textStatus);
            textstatus = (TextView) itemView.findViewById(R.id.statusclaimhis);
            imagehis = (ImageView) itemView.findViewById(R.id.imagehis);
        }
    }

}
