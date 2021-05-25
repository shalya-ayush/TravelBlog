package com.example.travelblog.http;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public final class BlogHttpClient {
    private Executor executor;
    private OkHttpClient client;
    private Gson gson;

    public static final BlogHttpClient INSTANCE = new BlogHttpClient();
    private static final String BASE_URL = "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/";
    private static  final String BLOG_ARTICLES_URL = BASE_URL + "8550ef2064bf14fcf3b9ff322287a2e056c7e153/blog_articles.json";

    private BlogHttpClient(){
        executor = Executors.newFixedThreadPool(4);
        client = new OkHttpClient();
        gson = new Gson();

    }
    public void loadBlogArticles(BlogArticlesInterface callback){
        Request request = new Request.Builder()
                .get()
                .url(BLOG_ARTICLES_URL)
                .build();
        executor.execute(() ->{
            try{
                Response response = client.newCall(request).execute();
                ResponseBody responseBody = response.body();
                if(responseBody != null){
                    String json = responseBody.string();
                    BlogData blogData = gson.fromJson(json,BlogData.class);
                    if(blogData != null){
                        callback.onSuccess(blogData.getData());
                        return;
                    }
                }
            }
            catch (IOException e){
                Log.e("BlogHttpClient", "ErrorLoadingArticles",e);

            }
            callback.onError();
        });

    }
}
