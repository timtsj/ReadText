package com.tsdreamdeveloper.readtext;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tsdreamdeveloper.readtext.model.Book;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String PAGE_ITEM = "pageItem";
    private ViewPager pager;
    private SharedPreferences sharedPreferences;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPager();
        pb = findViewById(R.id.pb);
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        getData();
    }

    private void initPager() {
        pager = findViewById(R.id.rv);

        BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
        bookFlipPageTransformer.setEnableScale(true);
        bookFlipPageTransformer.setScaleAmountPercent(10f);
        pager.setPageTransformer(true, bookFlipPageTransformer);
        pager.addOnPageChangeListener(this);
    }

    private void getData() {
        showLoading();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("books")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String text = document.getString("text");
                                result(text);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void result(String text) {
        Book book = new Book(text);

        pager.setAdapter(new TextAdapter(getSupportFragmentManager(), book.getTextList()));

        if (sharedPreferences.contains(PAGE_ITEM)) {
            int page = sharedPreferences.getInt(PAGE_ITEM, 0);
            pager.setCurrentItem(page);
        }
        int page = pager.getCurrentItem() + 1;
        setTitle(getString(R.string.app_name) + " page: " + page);

        hideLoading();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences.edit().putInt(PAGE_ITEM, pager.getCurrentItem()).apply();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int page = position + 1;
        setTitle(getString(R.string.app_name) + " page: " + page);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void showLoading() {
        pager.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        pager.setVisibility(View.VISIBLE);
        pb.setVisibility(View.GONE);
    }
}
