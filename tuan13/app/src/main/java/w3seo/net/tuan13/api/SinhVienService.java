package w3seo.net.tuan13.api;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import w3seo.net.tuan13.model.Sinhvien;
import w3seo.net.tuan13.utils.Configure;

public class SinhVienService {

    private RequestQueue requestQueue;
    private Context Activity;

    ArrayAdapter<Sinhvien> adapter;

    public SinhVienService(Context context, ArrayAdapter<Sinhvien> adapter) {
        this.Activity = context;
        this.requestQueue = Volley.newRequestQueue(Activity);
        this.adapter = adapter;
    }

    public void getAllSinhVien(){
        ArrayList<Sinhvien> sinhviens = new ArrayList<>();

        // Lắng nghe kết quả trả về
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // Server trả về một chuỗi có dạng mảng JSON, nên ta ép nó thành JSONArray rồi lặp trên Array để lấy ra từng JSONObject
                    JSONArray jsonArray = new JSONArray(response);
                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ma = jsonObject.getInt("masv");
                        String ten = jsonObject.getString("tensv");
                        sinhviens.add(new Sinhvien(ma, ten));
                    }
                    adapter.clear();
                    adapter.addAll(sinhviens);
                    adapter.notifyDataSetChanged();
                } catch (Exception ex) {
                    Toast.makeText(Activity, "Lỗi parse JSON: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Lắng nghe lỗi trả về (thường là lỗi kết nối)
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        Activity,
                        "Lỗi kết nối: " + (error.getMessage() != null ? error.getMessage() : "Không có kết nối"),
                        Toast.LENGTH_LONG
                ).show();
            }
        };

        StringRequest request = new StringRequest(
                Request.Method.POST,
                Configure.URL,
                responseListener,
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "getall");
                return params;
            }
        };

        requestQueue.add(request);
    }

    public void addSinhVien(Sinhvien sv, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = Configure.URL;
        
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "insert");
                params.put("masv", String.valueOf(sv.getMaSV()));
                params.put("tensv", sv.getTenSV());
                return params;
            }
        };
        
        requestQueue.add(request);
    }

    public void updateSinhVien(Sinhvien sv, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = Configure.URL;
        
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "update");
                params.put("masv", String.valueOf(sv.getMaSV()));
                params.put("tensv", sv.getTenSV());
                return params;
            }
        };
        
        requestQueue.add(request);
    }

    public void deleteSinhVien(int maSv, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String url = Configure.URL;
        
        StringRequest request = new StringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "delete");
                params.put("masv", String.valueOf(maSv));
                return params;
            }
        };
        
        requestQueue.add(request);
    }
}
