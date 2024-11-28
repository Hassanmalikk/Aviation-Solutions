package com.example.aviationsolutions;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightApiHelper {

    private ApiInterface apiInterface;

    public FlightApiHelper() {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    }

    public void getFlights(String apiKey, int limit, final FlightCallback callback) {
        Call<FlightResponse> call = apiInterface.getFlights(apiKey, limit);

        call.enqueue(new Callback<FlightResponse>() {
            @Override
            public void onResponse(Call<FlightResponse> call, Response<FlightResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onFailure("API Error: Unable to fetch data");
                }
            }

            @Override
            public void onFailure(Call<FlightResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    public interface FlightCallback {
        void onSuccess(FlightResponse flightResponse);
        void onFailure(String errorMessage);
    }
}

