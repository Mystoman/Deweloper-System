package myst.developersystem.api.service;

import java.util.ArrayList;

import myst.developersystem.api.model.responses.BasicServerResponse;
import myst.developersystem.api.model.responses.BuildingServerResponse;
import myst.developersystem.api.model.responses.FlatServerResponse;
import myst.developersystem.api.model.responses.InvestmentsServerResponse;
import myst.developersystem.api.model.responses.UserServerResponse;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HTTP;
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
            @Field("password") String password,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("/login")
    Call<UserServerResponse> login(
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

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/deleteInvestment", hasBody = true)
    Call<BasicServerResponse> deleteInvestment(
            @Header("username") String username,
            @Header("password") String password,
            @Field("investmentID") String investmentID
    );

    @FormUrlEncoded
    @POST("/buildingList")
    Call<BuildingServerResponse> buildingList(
            @Header("username") String username,
            @Header("password") String password,
            @Field("investmentID") String investmentID
    );

    @FormUrlEncoded
    @POST("/loadBuildingData")
    Call<BuildingServerResponse> loadBuildingData(
            @Header("username") String username,
            @Header("password") String password,
            @Field("buildingID") String buildingID
    );

    @FormUrlEncoded
    @POST("/addBuilding")
    Call<BasicServerResponse> addBuilding(
            @Header("username") String username,
            @Header("password") String password,
            @Field("data[]") ArrayList<String> data
    );

    @FormUrlEncoded
    @PUT("/updateBuilding")
    Call<BasicServerResponse> updateBuilding(
            @Header("username") String username,
            @Header("password") String password,
            @Field("buildingID") String buildingID,
            @Field("data[]") ArrayList<String> data
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/deleteBuilding", hasBody = true)
    Call<BasicServerResponse> deleteBuilding(
            @Header("username") String username,
            @Header("password") String password,
            @Field("buildingID") String buildingID
    );

    @FormUrlEncoded
    @POST("/flatList")
    Call<FlatServerResponse> flatList(
            @Header("username") String username,
            @Header("password") String password,
            @Field("buildingID") String buildingID
    );

    @FormUrlEncoded
    @POST("/loadFlatData")
    Call<FlatServerResponse> loadFlatData(
            @Header("username") String username,
            @Header("password") String password,
            @Field("flatID") String flatID
    );

    @FormUrlEncoded
    @POST("/addFlat")
    Call<BasicServerResponse> addFlat(
            @Header("username") String username,
            @Header("password") String password,
            @Field("data[]") ArrayList<String> data
    );

    @FormUrlEncoded
    @PUT("/updateFlat")
    Call<BasicServerResponse> updateFlat(
            @Header("username") String username,
            @Header("password") String password,
            @Field("flatID") String flatID,
            @Field("data[]") ArrayList<String> data
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "/deleteBuilding", hasBody = true)
    Call<BasicServerResponse> deleteFlat(
            @Header("username") String username,
            @Header("password") String password,
            @Field("flatID") String flatID
    );

}
