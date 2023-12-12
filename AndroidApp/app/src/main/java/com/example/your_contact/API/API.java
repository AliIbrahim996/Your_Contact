package com.example.your_contact.API;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface API {

    //post
    @FormUrlEncoded
    @POST("User/create")
    Call<DefaultResponse> createUser(
            @Field("user_name") String user_name,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("User/logIn")
    Call<LogInResponse> logInUser(
            @Field("email") String email,
            @Field("password") String password);


    @FormUrlEncoded
    @POST("send")
    Call<DefaultResponse> send(@Field("senderId") String senderId,
                               @Field("reciverId") String reciverId,
                               @Field("message") String message);

    @FormUrlEncoded
    @POST("addTask")
    Call<DefaultResponse> addTask(
            @Field("id") String user_id,
            @Field("task") String task,
            @Field("content") String content);

    @FormUrlEncoded
    @POST("User/update")
    Call<DefaultResponse> updateUser(@Field("id") String id, @Field("user_name") String user_name,
                                     @Field("email") String email);
    //get

    @GET("{id}/allUsers")
    Call<UsersResponse> getUsers(@Path("id") String userId);

    @GET("{senderId}/{reciverId}/getAllmessages")
    Call<ChatResponse> getAllMessages(@Path("senderId") String senderId, @Path("reciverId") String reciverId);

    @GET("{id}/tasks")
    Call<TaskResponse> getAllTasks(@Path("id") String userId);


}