package com.example.api_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button btnDel, btnBack,btnPost,btnPut,btnLogout;
    EmployeeAdapter adt;
    ListView lsName;
//    TextView txtName,txtID;
    EditText txtName,txtID;
    ArrayList<Employee> nameList;
    String url = "https://60b4edcefe923b0017c8324c.mockapi.io/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        txtName = findViewById(R.id.txtName);
//        txtID = findViewById(R.id.txtID);
        txtName =findViewById(R.id.txtName);
        txtID = findViewById(R.id.txtID);
        btnDel = findViewById(R.id.btnDel);
        btnPost = findViewById(R.id.btnPost);
        btnPut = findViewById(R.id.btnPut);
        btnBack = findViewById(R.id.btnBack);
        lsName = findViewById(R.id.lsName);
        btnLogout = findViewById(R.id.btnLogout);

        // Get data từ mocki
        String getAll = url + "Employee";
        getData(getAll);
        nameList = new ArrayList<>();
        lsName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Employee employee =nameList.get(position);
                id =employee.getId();
                String name = employee.getName();
                System.setProperty("ID", id+"");
                System.setProperty("Name",name);

                txtName.setText(System.getProperty("Name"));
                txtID.setText(System.getProperty("ID"));
                btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String urlDel="https://60b4edcefe923b0017c8324c.mockapi.io/Employee";
                        DeleteApi(url);

                        getData(urlDel);
                        adt.notifyDataSetChanged();

                    }
                });
                btnPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String urlPost="https://60b4edcefe923b0017c8324c.mockapi.io/";
                        PostApi(url);
                        getData(getAll);
                    }
                });
                btnPut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        String urlPut="https://60b4edcefe923b0017c8324c.mockapi.io/";
                        PutApi(url);
                        getData(getAll);
                    }
                });
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }
    private void getData(String url) {

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Toast.makeText(MainActivity.this, "true", Toast.LENGTH_SHORT).show();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject object = response.getJSONObject(i);
                        nameList.add(new Employee(object.getInt("id"), object.getString("name")));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adt = new EmployeeAdapter(getApplicationContext(), nameList, R.layout.item_listview);
                        lsName.setAdapter(adt);
                        adt.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void DeleteApi(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE, url + "Employee/" + txtID.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void PutApi(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT, url + "Employee/" + txtID.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();

                params.put("name", txtName.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void PostApi(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url + "Employee",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error by Post data!", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", txtName.getText().toString());
                return params;
            }
        };RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}