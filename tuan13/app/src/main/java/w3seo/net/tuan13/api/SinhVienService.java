package w3seo.net.tuan13.api;

import android.content.Context;
import android.net.Uri;
import android.widget.Adapter;
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

import w3seo.net.tuan13.MainActivity;
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

    public ArrayList<Sinhvien> getAllSinhVien(){

        /// 1. Khoi tao response
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //khi co phan hoi 200 tu server
            }
        };

        /// 2. khai bao listen cua ma loi
        //Response.ErrorListener errorListener = new Response.ErrorListener() {
          //  @Override
            //public void onErrorResponse(VolleyError volleyError) {
                //co loi server thi chay ham nay
            //}
        //};
        ArrayList<Sinhvien> sinhviens = new ArrayList<>();

        // Lắng nghe kết quả trả về
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    sinhviens.clear();
                    // Server trả về một chuỗi có dạng mảng JSON, nên ta ép nó thành JSONArray rồi lặp trên Array để lấy ra từng JSONObject
                    JSONArray jsonArray = new JSONArray(response);
                    int len = jsonArray.length();
                    for (int i = 0; i < len; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int ma = jsonObject.getInt("masv");
                        String ten = jsonObject.getString("tensv");
                        sinhviens.add(new Sinhvien(ma, ten));
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception ex) {
                }
            }
        };

        // Lắng nghe lỗi trả về (thường là lỗi kết nối)
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        Activity,
                        error.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        };

        /// 3. xay dung request
        // Tao url đến service
        //Uri.Builder builder = Uri.parse(Configure.URL).buildUpon();

        // Chèn thêm tham số cho url, dùng trong phương thức $_GET
        //builder.appendQueryParameter("action", "getall");
        //String url = builder.build().toString();

        String url = Configure.URL + "/ws/api.php?action=getall";

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,listener,errorListener);
        requestQueue.add(stringRequest);

        StringRequest request = new StringRequest(
                Request.Method.GET, // nếu dùng $_POST thì đổi thành POST
                url,
                responseListener,
                errorListener
        );


        requestQueue.add(request);

        return sinhviens;
    }
}
