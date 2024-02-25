package org.dmiit3iy.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.util.Constants;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileUploadRepository {
    private final ObjectMapper objectMapper;
    private FileUploadService service;

    public FileUploadRepository() {
        objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "file/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(FileUploadService.class);
    }

    public FileUploadRepository(String username, String password) {
        objectMapper = new ObjectMapper();
        //objectMapper.registerModule(new JavaTimeModule());
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new BasicAuthInterceptor(username, password)).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.URL + "file/")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .client(client)
                .build();
        this.service = retrofit.create(FileUploadService.class);
    }
    public void uploadFile(long id, File file) throws IOException {
        RequestBody requestFile =RequestBody.create(MediaType.parse(Files.probeContentType(file.toPath())),file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("document", file.getName(), requestFile);

        Call<ResponseResult<String>> call = service.upload(id,body);
        ResponseResult<String> res = call.execute().body();

    }

    public void downloadFile(String filename, long id, long version) throws IOException {
        Call<ResponseBody> call = this.service.showFile(filename, id, version);
        ResponseBody body = call.execute().body();
        String client = "C:\\client\\downloaded";
        File file = new File(client);
        file.mkdirs();
        try (FileOutputStream outputStream = new FileOutputStream(new File(file, filename))) {
            outputStream.write(body.bytes());
        }
    }


}
