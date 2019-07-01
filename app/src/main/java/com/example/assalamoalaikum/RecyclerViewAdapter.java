package com.example.assalamoalaikum;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    Context mContext;
    List<Contact> mData;
    Dialog myDialog;
    Button call;
    Button msg;

    public RecyclerViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        final MyViewHolder vHolder = new MyViewHolder(v);

        myDialog = new Dialog(mContext);
        myDialog.setContentView(R.layout.dialog_contact);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        vHolder.item_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dialog_name_tv = (TextView) myDialog.findViewById(R.id.dialog_name_id);
                TextView dialog_phone_tv = (TextView) myDialog.findViewById(R.id.dialog_phone_id);
                ImageView dialog_contact_img = (ImageView) myDialog.findViewById(R.id.dialog_img);
                dialog_name_tv.setText(mData.get(vHolder.getAdapterPosition()).getName());
                dialog_phone_tv.setText(mData.get(vHolder.getAdapterPosition()).getNumber());
                if (mData.get(vHolder.getAdapterPosition()).getImage_Uri() != null) {
                    dialog_contact_img.setImageURI(Uri.parse(mData.get(vHolder.getAdapterPosition()).getImage_Uri()));
                }

                call = (Button) myDialog.findViewById(R.id.callbutton);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel: "+mData.get(vHolder.getAdapterPosition()).getNumber()));
                        mContext.startActivity(callIntent);

                    }
                });
                msg=(Button) myDialog.findViewById(R.id.msgbutton);
                msg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent msgIntent = new Intent(Intent.ACTION_VIEW);
                        msgIntent.setData(Uri.parse("sms: "+mData.get(vHolder.getAdapterPosition()).getNumber()));
                        mContext.startActivity(msgIntent);
                    }
                });
                myDialog.show();
            }
        });


        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getNumber());
        if(mData.get(position).getImage_Uri()!=null){
            holder.img.setImageURI(Uri.parse(mData.get(position).getImage_Uri()));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name;
        private TextView tv_phone;
        private ImageView img;
        private LinearLayout item_contact;

        public MyViewHolder(View itemView){
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.name_Contact);
            tv_phone = (TextView) itemView.findViewById(R.id.phone_contact);
            img = (ImageView) itemView.findViewById(R.id.img_contact);
            item_contact=(LinearLayout) itemView.findViewById(R.id.contact_item);
        }
    }
}