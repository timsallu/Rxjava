package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.util.Log;

import com.example.test.models.Comment;
import com.example.test.models.Post;
import com.example.test.requests.ServiceGenerator;

import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private static final String TAG = "Salman";

    //ui
    private RecyclerView recyclerView;

    // vars
    private CompositeDisposable disposables = new CompositeDisposable();
    private RecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();


        Call<String> call=ServiceGenerator.getRequestApi1().getPostss();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                //Log.d(TAG, "Response:"+response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure:"+t.toString());
            }
        });

        Call<List<Post>> taskCall=ServiceGenerator.getRequestApitask().getPoststask();
        taskCall.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d(TAG, "Response1:"+response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure1:"+t.toString());
            }
        });

        getPostObservable()
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Post, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(Post post) throws Exception {
                        return getCommentPostObservable(post); //return an updated Observable with commnets
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        disposables.add(d);

                    }

                    @Override
                    public void onNext(Post post) {
                      //update the post in the list
                        updatePosts(post);
                    }

                    @Override
                    public void onError(Throwable e) {
                         Log.e(TAG, "onerror",e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void updatePosts(Post post)
    {
        adapter.updatePost(post);
    }

    private Observable<Post> getCommentPostObservable(final Post post)
    {
        return ServiceGenerator.getRequestApi()
                .getComments(post.getId())
                .map(new Function<List<Comment>, Post>() {
                    @Override
                    public Post apply(List<Comment> comments) throws Exception {
                        int delay=(new Random().nextInt(5)+1)*1000;
                        Thread.sleep(delay);

                        Log.d(TAG, "apply:sleepng thread"+Thread.currentThread().getName() + "for "
                        +String.valueOf(delay)+"ms");
                        post.setComments(comments);
                        return post;
                    }
                })
                .subscribeOn(Schedulers.io());

    }

    private Observable<Post> getPostObservable(){
        return ServiceGenerator.getRequestApi()
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Post>, ObservableSource<Post>>() {
                    @Override
                    public ObservableSource<Post> apply(List<Post> posts) throws Exception {
                        adapter.setPosts(posts);
                        return Observable.fromIterable(posts)
                                .subscribeOn(Schedulers.io());
                    }
                });
    }

    private void initRecyclerView(){
        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
