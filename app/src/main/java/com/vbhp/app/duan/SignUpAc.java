package com.vbhp.app.duan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.vbhp.app.duan.model.User;

public class SignUpAc extends AppCompatActivity {
    MaterialEditText edtPhone, edtConformPassword, edtPassword, edtEmail;
    Button btnSignUp;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        ImageView imageView = findViewById(R.id.imgBack);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpAc.this, LoginAc.class));
            }
        });

        edtPhone = (MaterialEditText)findViewById(R.id.edtPhone);
        edtConformPassword = (MaterialEditText)findViewById(R.id.edtConformPassword);
        edtPassword = (MaterialEditText)findViewById(R.id.edtPassWord);
        edtEmail = (MaterialEditText)findViewById(R.id.edtEmail);
        btnSignUp = findViewById(R.id.btnSignUp);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(SignUpAc.this,R.id.edtConformPassword,R.id.edtPassWord, R.string.fconformpassworderr);
        awesomeValidation.addValidation(SignUpAc.this,R.id.edtPassWord,regexPassword, R.string.fpassworderr);
        awesomeValidation.addValidation(SignUpAc.this,R.id.edtEmail,Patterns.EMAIL_ADDRESS, R.string.femailerr);
        awesomeValidation.addValidation(SignUpAc.this,R.id.edtPhone, RegexTemplate.TELEPHONE, R.string.fphoneerr);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.child(edtPhone.getText().toString()).exists()){
                        Toast.makeText(SignUpAc.this, "Error", Toast.LENGTH_SHORT).show();
                    }else if (awesomeValidation.validate()){
                        User  user = new User(edtConformPassword.getText().toString(),
                                edtPassword.getText().toString(),
                                edtEmail.getText().toString());
                        table_user.child(edtPhone.getText().toString()).setValue(user);
                       // Toast.makeText(SignUpAc.this, "Sign Up sccessfully", Toast.LENGTH_SHORT).show();
                        final ProgressDialog mDialog = new ProgressDialog(SignUpAc.this);
                        mDialog.setMessage("Please waiting...");
                        mDialog.show();
                        String sdt = edtPassword.getText().toString();
                        Intent intent = new Intent(SignUpAc.this, SignInAc.class);
//                        intent.putExtra("sdt", sdt);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(SignUpAc.this, "Error", Toast.LENGTH_SHORT).show();
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
