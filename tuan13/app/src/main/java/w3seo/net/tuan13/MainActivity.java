package w3seo.net.tuan13;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;

import w3seo.net.tuan13.api.SinhVienService;
import w3seo.net.tuan13.model.Sinhvien;

public class MainActivity extends AppCompatActivity {
    EditText txtMaSv, txtTenSv;
    Button btnLuu;
    ArrayList<Sinhvien> dsSv;
    ArrayAdapter<Sinhvien> adapter;
    ListView lvSv;
    Sinhvien chon = null;
    SinhVienService sinhVienService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        addControls();
        addEvents();
        hienthiDanhsach();
    }

    private void addControls() {
        txtMaSv = findViewById(R.id.txtMaSv);
        txtTenSv = findViewById(R.id.txtTenSv);
        btnLuu = findViewById(R.id.btnLuu);
        lvSv = findViewById(R.id.lvSv);
        dsSv = new ArrayList<>();
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                dsSv
        );
        lvSv.setAdapter(adapter);
        sinhVienService = new SinhVienService(this, adapter);
    }

    private void addEvents() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chon == null) {
                    // Thêm mới
                    int maSv = Integer.parseInt(txtMaSv.getText().toString());
                    String tenSv = txtTenSv.getText().toString();
                    Sinhvien sv = new Sinhvien(maSv, tenSv);
                    xuliThemSv(sv);
                    txtMaSv.setText("");
                    txtTenSv.setText("");
                    txtMaSv.requestFocus();
                } else {
                    // Cập nhật
                    String tenSv = txtTenSv.getText().toString();
                    chon.setTenSV(tenSv);
                    xuliCapnhatSv(chon);
                    chon = null;
                    txtMaSv.setText("");
                    txtTenSv.setText("");
                    txtMaSv.setEnabled(true);
                    txtMaSv.requestFocus();
                }
            }
        });
        lvSv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 0 && position < dsSv.size()) {
                    chon = dsSv.get(position);
                    txtMaSv.setText(chon.getMaSV() + "");
                    txtTenSv.setText(chon.getTenSV());
                    txtMaSv.setEnabled(false);
                }
            }
        });
        lvSv.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                                   int position, long id) {
                        if (position >= 0 && position < dsSv.size()) {
                            Sinhvien sv = dsSv.get(position);
                            xuliXoaSv(sv);
                        }
                        return true;
                    }
                });
    }

    private void hienthiDanhsach() {
        // Gọi service để lấy danh sách sinh viên từ API
        sinhVienService.getAllSinhVien();
    }

    private void xuliThemSv(Sinhvien sv) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(MainActivity.this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show();
                        hienthiDanhsach();
                    } else {
                        String message = jsonObject.optString("message", "Thêm sinh viên thất bại");
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show();
                    hienthiDanhsach();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi: " + (error.getMessage() != null ? error.getMessage() : "Không có kết nối"), Toast.LENGTH_LONG).show();
            }
        };

        sinhVienService.addSinhVien(sv, listener, errorListener);
    }

    private void xuliXoaSv(Sinhvien sv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên " + sv.getTenSV() + "?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(MainActivity.this, "Xóa sinh viên thành công!", Toast.LENGTH_SHORT).show();
                                hienthiDanhsach();
                            } else {
                                String message = jsonObject.optString("message", "Xóa sinh viên thất bại");
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Xóa sinh viên thành công!", Toast.LENGTH_SHORT).show();
                            hienthiDanhsach();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi: " + (error.getMessage() != null ? error.getMessage() : "Không có kết nối"), Toast.LENGTH_LONG).show();
                    }
                };

                sinhVienService.deleteSinhVien(sv.getMaSV(), listener, errorListener);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void xuliCapnhatSv(Sinhvien sv) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getBoolean("success")) {
                        Toast.makeText(MainActivity.this, "Cập nhật sinh viên thành công!", Toast.LENGTH_SHORT).show();
                        hienthiDanhsach();
                    } else {
                        String message = jsonObject.optString("message", "Cập nhật sinh viên thất bại");
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Cập nhật sinh viên thành công!", Toast.LENGTH_SHORT).show();
                    hienthiDanhsach();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Lỗi: " + (error.getMessage() != null ? error.getMessage() : "Không có kết nối"), Toast.LENGTH_LONG).show();
            }
        };

        sinhVienService.updateSinhVien(sv, listener, errorListener);
    }
}