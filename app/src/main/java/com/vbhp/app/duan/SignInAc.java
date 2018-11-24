package com.vbhp.app.duan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.vbhp.app.duan.model.User;

public class SignInAc extends AppCompatActivity {
    EditText edtPassword, edtPhoneNumber;
    Button btnSignIn;
    ImageView imgBack;
    TextView tvSignIn;
    Typeface typeface;
    String sodienthoai = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        Intent intent = getIntent();
//        String sdt = intent.getStringExtra("sdt");
//        if (sdt.equals("")){
//            sodienthoai = "sdt";
//            edtPassword.setText(sodienthoai);
//        }else {
//            sodienthoai = sdt;
//            edtPassword.setText(sdt);
//        }

        edtPassword = findViewById(R.id.edtPassWord);
        // edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        btnSignIn = findViewById(R.id.btnSignIn);
        AppCompatCheckBox checkbox;
        imgBack = findViewById(R.id.imgbackSignIn);
        tvSignIn = findViewById(R.id.textviewSignIn);
        typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Regular.ttf");
        tvSignIn.setTypeface(typeface);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInAc.this, LoginAc.class));
            }
        });


        checkbox = (AppCompatCheckBox) findViewById(R.id.checkbox);
        edtPassword = (EditText) findViewById(R.id.edtPassWord);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    // show password
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_use = database.getReference("User");

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_use.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(edtPhoneNumber.getText().toString()).exists()) {
                            User user = dataSnapshot.child(edtPhoneNumber.getText().toString()).getValue(User.class);
                            if (edtPhoneNumber.getText().toString().equals("")) {
                                Toast.makeText(SignInAc.this, "Email Available", Toast.LENGTH_SHORT).show();
                            } else if (edtPassword.getText().toString().equals("")) {
                                Toast.makeText(SignInAc.this, "Password Available", Toast.LENGTH_SHORT).show();
                            } else if (user.getPassword().equals(edtPassword.getText().toString())) {
                                final ProgressDialog mDialog = new ProgressDialog(SignInAc.this);
                                mDialog.setMessage("Please waiting...");
                                mDialog.show();
                                startActivity(new Intent(SignInAc.this, SearchAc.class));
                               // Toast.makeText(SignInAc.this, "Sign In Sccessfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignInAc.this, "Sign In Error", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(SignInAc.this, "User not exist in Database", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
