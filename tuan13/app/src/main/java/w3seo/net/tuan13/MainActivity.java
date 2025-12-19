package w3seo.net.tuan13;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

import w3seo.net.tuan13.model.Sinhvien;
import w3seo.net.tuan13.utils.Configure;

public class MainActivity extends AppCompatActivity {
    EditText txtMaSv, txtTenSv;
    Button btnLuu;
    ArrayList<Sinhvien> dsSv;
    ArrayAdapter<Sinhvien> adapter;
    ListView lvSv;
    Sinhvien chon = null;

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
        // Hàng đợi các request lên server

    }

    private void xuliThemSv(Sinhvien sv) {

    }

    private void xuliXoaSv(Sinhvien sv) {
        // Sinh viên tự viết
    }

    private void xuliCapnhatSv(Sinhvien sv) {
        // Sinh viên tự viết
    }
}