package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {
    @POST(".")
    Call<ResponseResult<User>> post (@Query("fio") String fio, @Query("login") String login, @Query("password") String password);

    @GET("{id}")
    Call<ResponseResult<User>> get(@Path("id") long id);

    @GET("{login}")
    Call<ResponseResult<User>> get(@Path("login") String login);

    @GET(".")
    Call<ResponseResult<User>> get();

}
