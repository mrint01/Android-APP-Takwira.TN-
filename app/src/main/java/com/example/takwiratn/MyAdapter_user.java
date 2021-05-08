package com.example.takwiratn;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.example.takwiratn.models.Stade;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter_user extends RecyclerView.Adapter<MyAdapter_user.MyViewHolder> {
    Context context;
    ArrayList<Stade> list;


    public MyAdapter_user(Context context, ArrayList<Stade> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_stades_user, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



            final Stade stade = list.get(position);
            holder.title.setText(stade.getNom_stade());
            final Uri uri = Uri.parse(String.valueOf(Uri.parse(stade.getPimage())));
            Glide.with(context).load(uri).into(holder.image);



        holder.cardvname.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Info_StadeReservation_user.class);

                intent.putExtra("image_stade",uri.toString());
                intent.putExtra("stade_name",stade.getNom_stade());
                intent.putExtra("address",stade.getAddresse());
                intent.putExtra("ville",stade.getVille());
                intent.putExtra("eclairage",stade.getEclairage());
                intent.putExtra("vest_stade",stade.getVestiaire());
                intent.putExtra("h_open",stade.getH_open());
                intent.putExtra("h_close",stade.getH_close());
                intent.putExtra("type_tapis",stade.getType());
                intent.putExtra("prix_stade",stade.getPrix());
                intent.putExtra("id_directeur",stade.getId_directeur());
                v.getContext().startActivity(intent);



            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,url,vest;
        CircleImageView image;
        CardView cardvname;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvfirstName);
            image = itemView.findViewById(R.id.imagestade);
            vest = itemView.findViewById(R.id.id_vestiare_stade);
            cardvname = itemView.findViewById(R.id.cardvimage);

        }
    }
}

