package com.tanka.auctionapp.Api;

import com.tanka.auctionapp.Model.User;
import com.tanka.auctionapp.ServerResponse.ImageResponse;
import com.tanka.auctionapp.ServerResponse.SignUpResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UsersAPI {

   @POST("users/register")
   Call<SignUpResponse> registerUser(@Body User users);

   @Multipart
   @POST("upload")
   Call<ImageResponse> uploadImage(@Part MultipartBody.Part img);

   @FormUrlEncoded
   @POST("users/login")
   Call<SignUpResponse> checkUser(@Field("email") String email, @Field("password") String password);

}
