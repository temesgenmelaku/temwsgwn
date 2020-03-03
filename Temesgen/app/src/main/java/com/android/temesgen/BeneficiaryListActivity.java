package com.android.temesgen;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.widget.Toast.*;

public class BeneficiaryListActivity extends AppCompatActivity {
    private AppCompatActivity activity = BeneficiaryListActivity.this;
    Context context = BeneficiaryListActivity.this;
    private RecyclerView recyclerViewBeneficiary;
    private ArrayList<Beneficiary> listBeneficiary;
    private BeneficiaryRecyclerAdapter beneficiaryRecyclerAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        recyclerViewBeneficiary = (RecyclerView)findViewById(R.id.recycler);

        initObjects();
        DatabaseHelper data=new DatabaseHelper(context);

        recyclerViewBeneficiary.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper data=new DatabaseHelper(context);
                String user;
                RecyclerView.ViewHolder viewHolder=(RecyclerView.ViewHolder)v.getTag();
                int pos=viewHolder.getAdapterPosition();
                Beneficiary detail=listBeneficiary.get(pos);


                Toast.makeText(BeneficiaryListActivity.this,detail.getName()+
                        detail.getEmail()+detail.getFullname()+detail.getPass()+
                        detail.getMobile()+detail.getSex(), LENGTH_SHORT).show();
            }
        } );

        recyclerViewBeneficiary.setOnLongClickListener( new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent();

                return databaseHelper.deleteData();
            }
        } );
    }

    private void initObjects() {
        listBeneficiary = new ArrayList<>();
        beneficiaryRecyclerAdapter = new BeneficiaryRecyclerAdapter(listBeneficiary, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewBeneficiary.setLayoutManager(mLayoutManager);
        recyclerViewBeneficiary.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBeneficiary.setHasFixedSize(true);
        recyclerViewBeneficiary.setAdapter(beneficiaryRecyclerAdapter);
        databaseHelper = new DatabaseHelper(activity);

        getDataFromSQLite();

    }

    private void getDataFromSQLite() {


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listBeneficiary.clear();
                listBeneficiary.addAll(databaseHelper. getAllBeneficiary());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                beneficiaryRecyclerAdapter.notifyDataSetChanged();


            }
        }.execute();
    }

}


