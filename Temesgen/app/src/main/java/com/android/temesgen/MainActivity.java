package com.android.temesgen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    EditText userDname;
    EditText userDfullname;
    EditText userDpassword;
    EditText userDmobile;
    EditText userDsex;
    EditText userDemail;
    Button register;
    String uname,ufullname,upassword,uemail,usex,umobil;
    private Beneficiary beneficiary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.registration );
        final   DatabaseHelper databaseHelper = new DatabaseHelper(this);
        beneficiary = new Beneficiary();
        final  Session session = new Session(  );
        register = (Button) findViewById( R.id.register );
        userDname = (EditText) findViewById( R.id.uid );
        userDfullname = (EditText) findViewById( R.id.ifull );
        userDpassword = (EditText) findViewById( R.id.ipass );
        userDmobile = (EditText) findViewById( R.id.imobil );
        userDsex = (EditText) findViewById( R.id.isex );
        userDemail = (EditText) findViewById( R.id.iemail );
///////////////////////////

        uname = userDname.getText().toString();
        ufullname = userDfullname.getText().toString();
        upassword = userDpassword.getText().toString();
        uemail = userDemail.getText().toString();
        usex = userDsex.getText().toString();
        umobil = userDmobile.getText().toString();
        register.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.editor.putString( "col_1",uname );
                session.editor.putString( "col_2",upassword );
                session.editor.putString( "col_3",ufullname);
                session.editor.putString( "col_4",uemail);
                session.editor.putString( "col_5",umobil );
                session.editor.putString( "col_6",usex);
                session.editor.commit();

                if (uemail.isEmpty() && usex.isEmpty() && umobil.isEmpty() && uname.isEmpty() &&
                        ufullname.isEmpty() && upassword.isEmpty()) {
                    Toast.makeText(MainActivity.this, "fill gentely the above questions ", Toast.LENGTH_LONG ).show();
                } else {

                    beneficiary.setName(uname.trim());
                    beneficiary.setEmail(uemail.trim());
                    beneficiary.setFullname(ufullname.trim());
                    beneficiary.setPass(upassword.trim());
                    beneficiary.setMobile(umobil.trim());
                    beneficiary.setSex(usex.trim());
                    if (!databaseHelper.checkUser(uemail.trim())) {
                        databaseHelper.addBeneficiary( beneficiary );
                        Intent accountsIntent = new Intent( MainActivity.this, BeneficiaryListActivity.class );
                        Toast.makeText( MainActivity.this, "Registration Successful!", Toast.LENGTH_SHORT )
                                .show();

                        accountsIntent.putExtra( "NAME", uname.trim() );

                        accountsIntent.putExtra( "Email", uemail.trim() );

                        accountsIntent.putExtra( "pass", upassword.trim() );

                        accountsIntent.putExtra( "fullname", ufullname.trim() );

                        accountsIntent.putExtra( "mobile", umobil.trim() );

                        accountsIntent.putExtra( "sex", usex.trim() );
                        startActivity( accountsIntent );

                    }
                }
            }

        } );
    }
}
