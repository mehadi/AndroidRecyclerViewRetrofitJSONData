package me.mehadi.androidrecyclerviewretrofitjsondata.api;

import java.util.List;
import me.mehadi.androidrecyclerviewretrofitjsondata.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequestData {
    @GET("users")
    Call<List<User>> getUsers();
}
