package com.example.takwiratn;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.takwiratn.models.Booking;
import com.example.takwiratn.models.Stade;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter_HistoryUser extends RecyclerView.Adapter<MyAdapter_HistoryUser.MyViewHolder> {

    Context context;
    ArrayList<Booking> list2;


    public MyAdapter_HistoryUser(Context context, ArrayList<Booking> list2) {
        this.context = context;
        this.list2 = list2;
    }

    @NonNull
    @Override
    public MyAdapter_HistoryUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history_user, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter_HistoryUser.MyViewHolder holder, final int position) {

        final Booking book = list2.get(position);
        holder.stadename.setText(book.getStade_name());
        holder.nbrtot.setText(book.getNbr_total_personne());
        holder.pricetot.setText(book.getPrix_total_order());
        holder.dateorder.setText(book.getDate_order());

        holder.deleteview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databasereff = FirebaseDatabase.getInstance().getReference("Booking").child(book.getId_book());
                databasereff.removeValue();
                Toast.makeText(context, "Supprimé ", Toast.LENGTH_SHORT).show();
                list2.remove(position);
                notifyItemRemoved(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder  {

        TextView stadename, nbrtot, pricetot,dateorder;
        CircleImageView image;
        CardView cardvname;
        ImageView deleteview;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stadename = itemView.findViewById(R.id.tvfirstName);
            nbrtot = itemView.findViewById(R.id.tvnbrtot);
            pricetot = itemView.findViewById(R.id.tvprice);
            dateorder = itemView.findViewById(R.id.tvdate);
            cardvname = itemView.findViewById(R.id.cardvimage);
            deleteview = itemView.findViewById(R.id.deleteview);

        }
    }
}


