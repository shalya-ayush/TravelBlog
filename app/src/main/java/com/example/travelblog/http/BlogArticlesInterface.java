package com.example.travelblog.http;

import java.util.List;

public interface BlogArticlesInterface {
    void onSuccess(List<Blog> blogList);
    void onError();
}
