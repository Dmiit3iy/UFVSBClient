package org.dmiit3iy.retrofit;

import org.dmiit3iy.dto.ResponseResult;
import org.dmiit3iy.model.User;
import org.dmiit3iy.model.UserFile;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface UserFileService {

    @GET("{id}")
    Call<ResponseResult<List<UserFile>>> get(@Path("id") long id);


}
