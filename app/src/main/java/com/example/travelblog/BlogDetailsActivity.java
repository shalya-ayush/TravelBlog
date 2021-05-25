package com.example.travelblog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.travelblog.http.Blog;
import com.example.travelblog.http.BlogArticlesInterface;
import com.example.travelblog.http.BlogHttpClient;

import java.util.List;

public class BlogDetailsActivity extends AppCompatActivity {
    private ImageView mainImage, imageAvatar, imageBack;
    private TextView textTitle, textDate, textAuthor, textViews, textRating, textDescription;
     private RatingBar ratingBar;
     private ProgressBar progressBar;


//    public static final String IMAGE_URL =
//            "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/" +
//                    "3436e16367c8ec2312a0644bebd2694d484eb047/images/sydney_image.jpg";
//    public static final String AVATAR_URL =
//            "https://bitbucket.org/dmytrodanylyk/travel-blog-resources/raw/" +
//                    "3436e16367c8ec2312a0644bebd2694d484eb047/avatars/avatar1.jpg";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        mainImage = findViewById(R.id.mainImage);
//        Glide.with(this)
//                .load(IMAGE_URL)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(mainImage);

        imageAvatar = findViewById(R.id.imageAvatar);
//        Glide.with(this)
//                .load(AVATAR_URL)
//                .transform(new CircleCrop())
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(imageAvatar);

        textTitle = findViewById(R.id.textTile);
        textDate = findViewById(R.id.textDate);
        textAuthor = findViewById(R.id.textAuthor);
        textRating = findViewById(R.id.textRating);
        textViews = findViewById(R.id.textViews);
        ratingBar = findViewById(R.id.ratingbar);
        imageBack = findViewById(R.id.imageBack);
        progressBar = findViewById(R.id.progressBar);
        imageBack.setOnClickListener(view -> finish());
        loadData();
    }

    private void loadData() {
        BlogHttpClient.INSTANCE.loadBlogArticles(new BlogArticlesInterface() {
            @Override
            public void onSuccess(List<Blog> blogList) {
                runOnUiThread(() -> showData(blogList.get(0)));
            }

            @Override
            public void onError() {

            }
        });
    }

    private void showData(Blog blog) {
        progressBar.setVisibility(View.GONE);
        textTitle.setText(blog.getTitle());
        textDate.setText(blog.getDate());
        textAuthor.setText(blog.getAuthor().getName());
        textRating.setText(String.valueOf(blog.getRating()));
        textViews.setText(String.format("%d views", blog.getViews()));
        ratingBar.setRating(blog.getRating());
        textDescription.setText(blog.getDescription());
        Glide.with(this)
                .load(blog.getImage())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(mainImage);

        Glide.with(this)
                .load(blog.getAuthor().getAvatar())
                .transform(new CircleCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageAvatar);
    }
}
