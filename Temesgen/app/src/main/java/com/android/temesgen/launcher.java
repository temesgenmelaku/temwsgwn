package com.android.temesgen;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class launcher extends AppCompatActivity {
    private EditText user;
    private EditText pass;
    private Button button;
    private Button account;
    public Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login );
        button = (Button) findViewById( R.id.loginbutton );
        user = (EditText) findViewById( R.id.entername );
        pass = (EditText) findViewById( R.id.enterpassword );
        final  Session session = new Session( this );
        account = (Button) findViewById( R.id.click_to_register );
        final    DatabaseHelper data= new DatabaseHelper( this );
        button.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userr = user.getText().toString();
                String email = pass.getText().toString();
                session.editor.putString( "name",userr );
                // new Session( Session ).editor
                session.editor.putString( "email",email );
                session.editor.commit();
                Intent intent = new Intent();
                intent.putExtra( "yihunie", userr);
                ;                if(data.getUser(userr,email)){

                    //   session.setLoggedin(true);
                    if(true==data.getUser(userr,email )){
                        startActivity(new Intent(launcher.this, BeneficiaryListActivity.class));
                        finish();}
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong email/password",Toast.LENGTH_SHORT).show();
                }

            }
        } );
        account.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( launcher.this,MainActivity.class ) );
            }
        } );
    }
}
