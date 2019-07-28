package com.example.test.requests;

import com.example.test.Task;
import com.example.test.models.Comment;
import com.example.test.models.Post;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestApi {

    @GET("posts")
    Call<String> getPostss();

    @GET("posts")
    Call<List<Post>> getPoststask();

    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("posts/{id}/comments")
    Observable<List<Comment>> getComments(
            @Path("id") int id
    );
}
