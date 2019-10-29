package com.google.ai.googlesheet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

public class ListView extends AppCompatActivity {
private RecyclerView recyclerView;
private ArrayList<DataSheet> list = new ArrayList<>();
private ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ListAdapter(list);
        recyclerView.setAdapter(listAdapter);
getData();
    }

    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://script.googleusercontent.com/macros/echo?user_content_key=WJ13wY3AeR2rae9e9XsShVHAqfUKxcKFtmUWvf1bBG-NpgorjVp1Sx4M_BxYYTQkbvh-LbrXkv5V7vRi_u9VU7WW4PncCTAUm5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnNLxomAUm5UVC0wLJKK86cHeuKe4dYdQC0QxD0o6pDVVA5iLNhB6Mil7lkRRhVjTGvhcOEsz9Faz&lib=ME8W3UNRawTeVXQ7kZi02O_MFm8U83jC6";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("user");

                    list = new ArrayList<DataSheet>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject record = jsonArray.getJSONObject(i);
                        String     Id     = record.getString("id");
                        String     Title  = record.getString("nama");
                        String     Photo  = record.getString("umur");

                        DataSheet data = new DataSheet();

                        data.setNama(Title);
                        data.setId(Id);
                        data.setUmur(Photo);

                        list.add(data);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                listAdapter.notifyDataSetChanged();

                listAdapter = new ListAdapter(list);
                recyclerView.setAdapter(listAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        queue.add(request);
    }
}

