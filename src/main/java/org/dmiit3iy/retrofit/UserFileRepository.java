package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import org.dmiit3iy.model.UserFile;
import org.dmiit3iy.util.Constants;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.List;

public class UserFileRepository {

    private ObjectMapper objectMapper;
    private UserFileService service;

    public UserFileRepository(String username, String password) {
        objectMapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(username, password)).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "file/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();
        this.service = retrofit.create(UserFileService.class);
    }

    public UserFileRepository() {
        objectMapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.URL + "file/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper)).client(client).build();
        this.service = retrofit.create(UserFileService.class);
    }

    private <T> T getData(Response<ResponseResult<T>> execute) throws IOException {
        if (execute.code() != 200) {
            String string = execute.errorBody().string();
            System.out.println(string);
            String message = objectMapper.readValue(string,
                    new TypeReference<ResponseResult<T>>() {
                    }).getMessage();
            System.out.println(message);
            throw new IllegalArgumentException(message);
        }
        return execute.body().getData();
    }


    public List<UserFile> get() throws IOException {
        Response<ResponseResult<List<UserFile>>> execute = service.get().execute();
        return getData(execute);
    }

//    public List<UserFile> get(long id) throws IOException {
//        Response<ResponseResult<List<UserFile>>> execute = service.get(id).execute();
//        return getData(execute);
//    }
}
