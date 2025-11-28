package com.example.aviationsolutions;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {



    @GET("flights")
    Call<FlightResponse> getFlights(
            @Query("access_key") String apiKey,
            @Query("limit") int limit
    );
}

