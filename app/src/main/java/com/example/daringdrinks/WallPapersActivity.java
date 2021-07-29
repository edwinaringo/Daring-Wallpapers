package com.example.daringdrinks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WallPapersActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    private EditText searchEdit;
    private ImageView searchIV;
    private RecyclerView categoryRV,wallpaperRV;
    private ProgressBar loadingPB;
    private ArrayList<String> wallpaperArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private WallpaperRVAdapter wallpaperRVAdapter;
    //563492ad6f91700001000001b5bb63b623514e0e9b596e11a4ac229f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);

        searchEdit = findViewById(R.id.idEditSearch);
        searchIV = findViewById(R.id.idIVSearch);
        categoryRV = findViewById(R.id.idRVCategory);
        wallpaperRV = findViewById(R.id.iRVWallpapers);
        loadingPB = findViewById(R.id.idPBLoading);
        wallpaperArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(WallPapersActivity.this, RecyclerView.HORIZONTAL, false);
        categoryRV.setLayoutManager(linearLayoutManager);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList, this, this::onCategoryClick);
        categoryRV.setAdapter(categoryRVAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        wallpaperRV.setLayoutManager(gridLayoutManager);
        wallpaperRVAdapter = new WallpaperRVAdapter(wallpaperArrayList, this);
        wallpaperRV.setAdapter(wallpaperRVAdapter);

        getCategories();
        getWallpapers();

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchStr = searchEdit.getText().toString();
                if(searchStr.isEmpty()) {
                    Toast.makeText(WallPapersActivity.this, "Please enter search query", Toast.LENGTH_SHORT).show();
                }else{
                    getWallpapersByCategory(searchStr);

                }
            }
        });
    }

    private void getWallpapersByCategory(String category){
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/search?query="+category+"&per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(WallPapersActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray photoArray = null;
                try {
                    photoArray = response.getJSONArray("photos");
                    for(int i=0; i<photoArray.length(); i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);

                    }
                    wallpaperRVAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WallPapersActivity.this, "Failed to load wallpapers", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String>headers = new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001b5bb63b623514e0e9b596e11a4ac229f");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void getCategories() {
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://wallpaperaccess.com/full/829012.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Programming", "https://images.unsplash.com/photo-1516116216624-53e697fedbea?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fHByb2dyYW1taW5nfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Nature", "https://wallpaperaccess.com/full/31189.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Arts", "https://images.unsplash.com/photo-1536924940846-227afb31e2a5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8NXx8fGVufDB8fHx8&w=1000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Music", "https://image.winudf.com/v2/image1/Y29tLm1vYmlsZXVuaXZlcnNpdHkubXVzaWNfc2NyZWVuXzFfMTU0MzI5MzcwMF8wMTY/screen-1.jpg?fakeurl=1&type=.jpg"));
        categoryRVModalArrayList.add(new CategoryRVModal("Travel", "https://images.unsplash.com/photo-1508672019048-805c876b67e2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8NHx8fGVufDB8fHx8&w=1000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Architecture","https://images.unsplash.com/photo-1487958449943-2429e8be8625?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YXJjaGl0ZWN0dXJlfGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&w=1000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Abstract","https://images.unsplash.com/photo-1541701494587-cb58502866ab?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YWJzdHJhY3R8ZW58MHx8MHx8&ixlib=rb-1.2.1&w=1000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Cars","https://images.unsplash.com/photo-1493238792000-8113da705763?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8Mnx8fGVufDB8fHx8&w=1000&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Flowers","https://images.unsplash.com/photo-1490750967868-88aa4486c946?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8M3x8fGVufDB8fHx8&w=1000&q=80"));
        categoryRVAdapter.notifyDataSetChanged();

    }

    private void getWallpapers() {
        wallpaperArrayList.clear();
        loadingPB.setVisibility(View.VISIBLE);
        String url = "https://api.pexels.com/v1/curated?per_page=30&page=1";
        RequestQueue requestQueue = Volley.newRequestQueue(WallPapersActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                loadingPB.setVisibility(View.GONE);
                try {
                    JSONArray photoArray = response.getJSONArray("photos");
                    for(int i=0; i<photoArray.length(); i++){
                        JSONObject photoObj = photoArray.getJSONObject(i);
                        String imgUrl = photoObj.getJSONObject("src").getString("portrait");
                        wallpaperArrayList.add(imgUrl);
                    }
                    wallpaperRVAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WallPapersActivity.this, "Failed to load wallpapers", Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers = new HashMap<>();
                headers.put("Authorization","563492ad6f91700001000001b5bb63b623514e0e9b596e11a4ac229f");
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);


    }

    @Override
    public void onCategoryClick(int position){
        String category = categoryRVModalArrayList.get(position).getCategory();
        getWallpapersByCategory(category);

        }
}