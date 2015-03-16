package example.android.vollytest;

        import org.json.JSONObject;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.ImageLoader;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.NetworkImageView;
        import com.android.volley.toolbox.Volley;
        import com.android.volley.toolbox.ImageLoader.ImageCache;
        import com.android.volley.toolbox.ImageLoader.ImageListener;

        import android.app.Activity;
        import android.graphics.Bitmap;
        import android.os.Bundle;
        import android.support.v4.util.LruCache;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

/**
 *
 * Volley?Android?????????????????? volley?????? 1.JSON??????? 2.???????
 * 3.?????????? 4.?? 5.???????? 6.?Activity??????
 *
 *
 * ??Volley git clone
 * https://android.googlesource.com/platform/frameworks/volley
 */

public class MainActivity extends Activity implements View.OnClickListener{

    private ImageView iv1;
    private NetworkImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
//        getJSONVolley();

    }

    public void init() {
        iv1 = (ImageView) findViewById(R.id.iv);
        iv2 = (NetworkImageView) findViewById(R.id.imageView1);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    // ??json???
    public void getJSONVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String JSONDateUrl = "http://www.wwtliu.com/jsondata.html";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, JSONDateUrl, null,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        System.out.println("response=" + response);
                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(com.android.volley.VolleyError arg0) {
                System.out.println("???????");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void loadImageVolley(String imageurl) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lurcache = new LruCache<String, Bitmap>(20);
        ImageCache imageCache = new ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                lurcache.put(key, value);
            }
            @Override
            public Bitmap getBitmap(String key) {

                return lurcache.get(key);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        ImageListener listener = imageLoader.getImageListener(iv1,R.drawable.tk, R.drawable.tk);
        imageLoader.get(imageurl, listener);
    }

    public void NetWorkImageViewVolley(String imageUrl){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(20);
        ImageCache imageCache = new ImageCache() {
            @Override
            public void putBitmap(String key, Bitmap value) {
                lruCache.put(key, value);
            }
            @Override
            public Bitmap getBitmap(String key) {
                return lruCache.get(key);
            }
        };
        ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
        iv2.setTag("url");
        iv2.setImageUrl(imageUrl, imageLoader);
    }

    public static boolean f = true;
    private Button button;

    @Override
    public void onClick(View v) {
        f = !f;
        if (f){
            String imageurl = "http://img1.gtimg.com/ninja/0/ninja142614424619038.jpg";

            loadImageVolley(imageurl);
            //NetWorkImageViewVolley(imageurl);

        }else{
            String imageurl = "http://img1.gtimg.com/ninja/0/ninja142614456110666.jpg";

            loadImageVolley(imageurl);
            //NetWorkImageViewVolley(imageurl);

        }
    }
}




