package com.tanka.auctionapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tanka.auctionapp.Api.UsersAPI;
import com.tanka.auctionapp.LoginActivity;
import com.tanka.auctionapp.Model.User;
import com.tanka.auctionapp.R;
import com.tanka.auctionapp.ServerResponse.ImageResponse;
import com.tanka.auctionapp.ServerResponse.SignUpResponse;
import com.tanka.auctionapp.StrictMode.StrictModeClass;
import com.tanka.auctionapp.Url.Url;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFullname, etAddress, etMobile, etEmail, etPassword,etConfirm;
    private Button btnSignUp;
    private TextView Signin;
    private CircleImageView imgProfile;
    String imagePath;
    private String imageName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Registration Form");

        imgProfile = findViewById(R.id.profile);
        etFullname = findViewById(R.id.fname);
        etAddress = findViewById(R.id.address);
        etMobile = findViewById(R.id.mobile);
        etEmail = findViewById(R.id.userEmail);
        etPassword = findViewById(R.id.password);
        etConfirm = findViewById(R.id.cpassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        Signin = findViewById(R.id.textSignIn);
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseImage();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPassword.getText().toString().equals(etConfirm.getText().toString())) {
                        saveImageOnly();
                        signUp();

                } else {
                    Toast.makeText(RegisterActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

            }
        });
    }

    private void signUp() {

            String fullname = etFullname.getText().toString();
            String address = etAddress.getText().toString();
            String mobile_number = etMobile.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String cpassword = etConfirm.getText().toString();
            User users = new User(fullname, address, mobile_number, email, password,imageName);

            UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
            Call<SignUpResponse> signUpCall = usersAPI.registerUser(users);

            signUpCall.enqueue(new Callback<SignUpResponse>() {
                @Override
                public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Code" + response.code(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(RegisterActivity.this,"Registered Successfully!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<SignUpResponse> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                }
            });

    }

    //for image upload
    private void BrowseImage(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(data == null){
                Toast.makeText(this,"Please select an image", Toast.LENGTH_SHORT).show();
            }
        }

        Uri uri = data.getData();
        imgProfile.setImageURI(uri);
        imagePath = getRealPathFromUri(uri);
    }

    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(),
                uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void saveImageOnly() {
        File file = new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile",
                file.getName(), requestBody);

        UsersAPI usersAPI = Url.getInstance().create(UsersAPI.class);
        Call<ImageResponse> responseBodyCall = usersAPI.uploadImage(body);

        StrictModeClass.StrictMode();
        //Synchronous methid
        try {
            Response<ImageResponse> imageResponseResponse = responseBodyCall.execute();
            imageName = imageResponseResponse.body().getFilename();
            Toast.makeText(this, "Image inserted" + imageName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "Error" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //for validation


}
