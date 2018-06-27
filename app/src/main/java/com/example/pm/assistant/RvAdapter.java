package com.example.pm.assistant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pm.assistant.data.Contato;

import java.util.Collections;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    List<Contato> contatos = Collections.emptyList();

    public RvAdapter(Context context, List<Contato> contatos) {
        inflater = LayoutInflater.from(context);
        this.contatos = contatos;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contato current = contatos.get(position);

        holder.name.setText(current.getContato_nome());
        holder.relationship.setText(current.getContato_relacionamento());
        holder.faceToken.setText(current.getContato_facetoken());
        //TODO colocar a imagem
//                holder.image.setImageBitmap(current.imagePath);
    }


    @Override
    public int getItemCount() {
        return contatos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView relationship;
        TextView faceToken;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView2);
            name = (TextView) itemView.findViewById(R.id.contactNameTextView);
            relationship = (TextView) itemView.findViewById(R.id.relationshipTextView);
            faceToken = (TextView) itemView.findViewById(R.id.faceToken);
        }
    }

}
