package app.hungnt.test.com.mvc;
/*

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String APP_TAG = "com.mrbool.mvc";

    private ListView lvTask;
    private Button btNewTask;
    private EditText etNewTask;

    private MVCController controller;

    @Override
    public void onCreate(final Bundle bundle)
    {
        super.onCreate(bundle);

        this.setContentView(R.layout.activity_main);

        this.controller = new MVCController(this);

        this.lvTask = (ListView) this.findViewById(R.id.lvTask);
        this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
        this.etNewTask = (EditText)
                this.findViewById(R.id.etNewTask);

        this.btNewTask.setOnClickListener(this.handleNewTaskEvent);

        this.populateTasks();
    }

    private void populateTasks()
    {
        final List<String> tasks = this.controller.getTasks();

        //Log.d(MVCView.APP_TAG, String.format("%d found tasks ",            tasks.size()));

        this.lvTask.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1,
                        tasks.toArray(new String[] {})));

        this.lvTask.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(final AdapterView<?> parent,
                                    final View view, final int position, final long id)
            {
                Log.d(MainActivity.APP_TAG, String.format("task id: %d  and position: %d", id, position));

                final TextView v = (TextView) view;

                MainActivity.this.controller.deleteTask
                        (v.getText().toString());


                MainActivity.this.populateTasks();
            }
        });
    }

    private final View.OnClickListener handleNewTaskEvent =
            new View.OnClickListener()
            {
                @Override
                public void onClick(final View view)
                {
                    Log.d(APP_TAG, "New Task button added");

                    MainActivity.this.controller.addTask(MainActivity.this
                            .etNewTask.getText().toString());

                    MainActivity.this.populateTasks();
                }
            };


    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }




}
*/
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements Response.Listener,
        Response.ErrorListener {
    public static final String REQUEST_TAG = "MainVolleyActivity";
    private TextView mTextView;
    private Button mButton;
    private FirebaseAnalytics mFirebaseAnalytics;
    private RequestQueue mQueue;
    private NetworkImageView imageView;
    //ImageView imageView;
    String url="https://en.wikipedia.org/wiki/Image#/media/File:Image_created_with_a_mobile_phone.png";
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "121");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "12121");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        imageView=(NetworkImageView) findViewById(R.id.imageView);
        loadImage();
        ImageRequest ir = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, null, null);
    }
    private void loadImage(){


        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(url, ImageLoader.getImageListener(imageView,
                android.R.drawable
                        .ic_dialog_alert, android.R.drawable
                        .ic_dialog_alert));
        imageView.setImageUrl(url, imageLoader);
    }
    @Override
    protected void onStart() {
        super.onStart();
       /* // Instantiate the RequestQueue.
        mQueue = CustomVolleyRequestQueue.getInstance(this.getApplicationContext())
                .getRequestQueue();
        String url = "http://api.geonames.org/citiesJSON?north=44.1&south=-9.9&east=-22.4&west=55.2&lang=de&username=demo";
        final CustomJSONObjectRequest jsonRequest = new CustomJSONObjectRequest(Request.Method
                .GET, url,
                new JSONObject(), this, this);
        jsonRequest.setTag(REQUEST_TAG);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mQueue.add(jsonRequest);
            }
        });*/
       /* RequestQueue test= Volley.newRequestQueue(this);
        test.*/
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mQueue != null) {
            mQueue.cancelAll(REQUEST_TAG);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mTextView.setText(error.getMessage());
    }

    @Override
    public void onResponse(Object response) {
        mTextView.setText("Response is: " + response);
        try {
            mTextView.setText(mTextView.getText() + "\n\n" + ((JSONObject) response).getString
                    ("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}