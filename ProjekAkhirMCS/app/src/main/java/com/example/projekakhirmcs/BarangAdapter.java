package com.example.projekakhirmcs;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.Vector;

//public class BarangAdapter extends FirebaseRecyclerAdapter<Barang, BarangAdapter.barangViewholder>{
public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.barangViewholder> {

    private Vector<Barang> brg = new Vector<Barang>();
    private ItemClickListener ICL;

    public BarangAdapter(Vector<Barang> options){
        this.brg = options;

    }

    Barang getItem(int id) {
        return brg.get(id);
    }

    void setICL(ItemClickListener ICL){
        this.ICL=ICL;
    }

    public interface  ItemClickListener{
        void onItemClick(View view, int position);
    }


//    public BarangAdapter(@NonNull FirebaseRecyclerOptions<Barang> options){
//        super(options);
//    }

//    @Override
//    protected void
//    onBindViewHolder(@NonNull barangViewholder holder, int position, @NonNull Barang model){
//        holder.nama.setText(model.getNamabarang());
//        holder.id.setText(model.getIdbarang());
//        holder.harga.setText(model.getHargabarang());
//    }

    @NonNull
    @Override
    public barangViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listbarang,parent,false);
        return new BarangAdapter.barangViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull barangViewholder holder, int position) {
        holder.nama.setText(brg.get(position).getNamabarang());
        holder.rating.setText(brg.get(position).getRatingbarang());
        holder.harga.setText(brg.get(position).getHargabarang());

    }

    @Override
    public int getItemCount() {
        return brg.size();
    }

    class barangViewholder extends RecyclerView.ViewHolder{
        TextView nama, rating, harga;
        public barangViewholder(@NonNull View itemView){
            super(itemView);
            nama = itemView.findViewById(R.id.namabarang);
            rating = itemView.findViewById(R.id.ratingbarang);
            harga = itemView.findViewById(R.id.hargabarang);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(ICL != null){
                        Log.d("getNameeee", "klik");
                        ICL.onItemClick(view, getAdapterPosition());

                    }
                }
            });
        }
    }

}