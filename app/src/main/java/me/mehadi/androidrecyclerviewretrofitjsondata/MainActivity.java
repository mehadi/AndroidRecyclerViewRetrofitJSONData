package me.mehadi.androidrecyclerviewretrofitjsondata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.mehadi.androidrecyclerviewretrofitjsondata.adapter.AdapterAllView;
import me.mehadi.androidrecyclerviewretrofitjsondata.api.ApiRequestData;
import me.mehadi.androidrecyclerviewretrofitjsondata.api.RetroServer;
import me.mehadi.androidrecyclerviewretrofitjsondata.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecycler;
    private AdapterAllView mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<User> mItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recycler
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTemp);
        mRecycler.setHasFixedSize(true);
        mManager = new GridLayoutManager(this, 1);
        mRecycler.setLayoutManager(mManager);
        mRecycler.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        mRecycler.setItemAnimator(new DefaultItemAnimator());
        getData();
    }

    // Get Matches Data
    public void getData() {
        ApiRequestData apiService = RetroServer.getRetrofitServer();
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    mItems = response.body();
                    mAdapter = new AdapterAllView(getApplicationContext()  , mItems);
                    mRecycler.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    Log.w("Home",mItems.toString());
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });
    }



    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}