package com.android.temesgen;



import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class BeneficiaryRecyclerAdapter extends RecyclerView.Adapter<BeneficiaryRecyclerAdapter.BeneficiaryViewHolder>  {

    private ArrayList<Beneficiary> listBeneficiary;
    private Context mContext;
    private ArrayList<Beneficiary> mFilteredList;


    public BeneficiaryRecyclerAdapter(ArrayList<Beneficiary> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;
        this.mFilteredList = listBeneficiary;


    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public  TextView textViewEmail;


        public BeneficiaryViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textViewname);
            textViewEmail = (TextView) view.findViewById(R.id.textViewemail);


        }


    }




    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_name_pass, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {
        holder.textViewName.setText(listBeneficiary.get(position).getName());
        holder.textViewEmail.setText(listBeneficiary.get(position).getEmail());


    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }



}


