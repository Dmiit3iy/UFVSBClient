package org.dmiit3iy.retrofit;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.dmiit3iy.dto.ResponseResult;
import retrofit2.Call;
import retrofit2.http.*;

public interface FileUploadService {
    @Multipart
    @POST(".")
    Call<ResponseResult<String>> upload(  @Part("id") long id,@Part MultipartBody.Part file);

    @GET("mime/{filename}")
    Call<ResponseBody> showFile(@Path("filename")String filename, @Query("id") long id, @Query("version") long version);

    @GET("zip")
    Call<ResponseBody> getZip();
}