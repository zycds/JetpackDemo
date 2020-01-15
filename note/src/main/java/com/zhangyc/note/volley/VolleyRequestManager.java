package com.zhangyc.note.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONObject;

public class VolleyRequestManager implements Cache, Network {

    private static final String url = "https://www.wanandroid.com/";

    private RequestQueue requestQueue;

    public VolleyRequestManager(Context context) {

//        RequestQueue requestQueue = new RequestQueue(this, this);
//        requestQueue.start();
//        requestQueue.add(stringRequest);

          requestQueue = Volley.newRequestQueue(context);
//        requestQueue1.add(stringRequest);


//        NetworkImageView networkImageView = new NetworkImageView(context);
//        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(networkImageView, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//        ImageLoader imageLoader = new ImageLoader(requestQueue1, new BitmapCache());
//        imageLoader.get(url, imageListener);
//        networkImageView.setImageUrl(url, imageLoader);
    }

    public void loadImage() {
        requestQueue.add(imageRequest);
    }

    public class BitmapCache implements ImageLoader.ImageCache {

        @Override
        public Bitmap getBitmap(String url) {
            return null;
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {

        }
    }


    StringRequest stringRequest = new StringRequest(url + "user/logout/json", new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });


    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(), new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {

        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });


    ImageRequest imageRequest = new ImageRequest("http://b-ssl.duitang.com/uploads/item/201209/07/20120907181244_tGiNN.jpeg", new Response.Listener<Bitmap>() {

        @Override
        public void onResponse(Bitmap response) {
        }
    }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,

            new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

    @Override
    public Entry get(String key) {
        return null;
    }

    @Override
    public void put(String key, Entry entry) {

    }

    @Override
    public void initialize() {

    }

    @Override
    public void invalidate(String key, boolean fullExpire) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public void clear() {

    }

    @Override
    public NetworkResponse performRequest(Request<?> request) throws VolleyError {
        return null;
    }
}
