package myst.developersystem.api.service;

import java.util.ArrayList;

import myst.developersystem.api.model.responses.BasicServerResponse;
import myst.developersystem.api.model.responses.InvestmentsServerResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by Michal on 11.11.17.
 */

public interface CallsInterface {

    @FormUrlEncoded
    @POST("/register")
    Call<BasicServerResponse> register(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("/login")
    Call<BasicServerResponse> login(
            @Field("username") String username,
            @Field("password") String password
    );

    @POST("/investmentList")
    Call<InvestmentsServerResponse> investmentList(
            @Header("username") String username,
            @Header("password") String password
    );

    @FormUrlEncoded
    @POST("/loadInvestmentData")
    Call<InvestmentsServerResponse> loadInvestmentData(
            @Header("username") String username,
            @Header("password") String password,
            @Field("investmentID") String investmentID
    );

    @FormUrlEncoded
    @POST("/addInvestment")
    Call<BasicServerResponse> addInvestment(
            @Header("username") String username,
            @Header("password") String password,
            @Field("data[]") ArrayList<String> data
    );

    @FormUrlEncoded
    @PUT("/updateInvestment")
    Call<BasicServerResponse> updateInvestment(
            @Header("username") String username,
            @Header("password") String password,
            @Field("investmentID") String investmentID,
            @Field("data[]") ArrayList<String> data
    );

}