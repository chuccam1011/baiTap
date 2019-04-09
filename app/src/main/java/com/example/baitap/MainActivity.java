package com.example.baitap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
        RadioGroup radioGroup;

        Database database;
        ListView lvCongViec;
        ArrayList<CongViec> arrayCongViec;
        CongViecAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            lvCongViec=(ListView)findViewById(R.id.ListViewCongViec);
            arrayCongViec= new ArrayList<>();
            adapter= new CongViecAdapter(this,R.layout.dong_cong_viec,arrayCongViec);
            lvCongViec.setAdapter(adapter);



        //tao database ghi chu
        database = new Database(this,"ghichu.sqlite",null,1);
////tao bang ocng  viec
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT,TenCV VARCHAR(200))");
        //insert data
      //  database.QueryData("INSERT INTO CongViec VALUES(null,'Vieet ung dun ghi chu')");
            //selecx dta ta

        GetDataCV();
    }

    public void  DialogXoaCV(final String tenCV, final int id){
        AlertDialog.Builder dialogXoa= new AlertDialog.Builder(this);
        dialogXoa.setMessage(" Có xóa : "+ tenCV+" không ? ");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id ='"+id+" ' ");
                Toast.makeText(MainActivity.this," đã xóa"+ tenCV,Toast.LENGTH_SHORT).show();
                GetDataCV();
            }
        });
        dialogXoa.setNegativeButton("Không             ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();
    }


    public void DialogSuaCV(String ten , final int id){
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dilog_sua);
        final EditText edtTenCV= (EditText)dialog.findViewById(R.id.editTextTenCvEdit);
        Button btnxacNhan= (Button)dialog.findViewById(R.id.buttonXacNhan);
        Button btnHuy= (Button)dialog.findViewById(R.id.buttonhuy);

        edtTenCV.setText(ten);


        btnxacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi= edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV =' "+tenMoi+" ' WHERE Id = '"+id+" '   ");
                Toast.makeText(MainActivity.this," đa câp nhật ",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataCV();
            }
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    private void GetDataCV(){
        Cursor dataCongViec= database.GetData("SELECT * FROM CongViec");
        arrayCongViec.clear();
        while (dataCongViec.moveToNext()){
            String ten= dataCongViec.getString(1);
            int id= dataCongViec.getInt(0);
            arrayCongViec.add(new CongViec(id,ten)) ;
        }

        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_cong_viec,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override




    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menuAdd){
            DialogThem();
        }

        return super.onOptionsItemSelected(item);
    }

    private void  DialogThem() {
        final Dialog dialog= new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_cong_viec );

        final EditText edtTen=(EditText)dialog.findViewById(R.id.editTextTenCV);
        Button btnThem= (Button)dialog.findViewById(R.id.buttonThem);
        Button btnHuy=(Button)dialog.findViewById(R.id.buttonHuy);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tencv=edtTen.getText().toString();
                if(tencv.equals("")){
                    Toast.makeText(MainActivity.this,"Vui long  nham ten cong viec ",Toast.LENGTH_SHORT).show();

                }else {

                    database.QueryData("INSERT INTO CongViec VALUES(null,'"+ tencv+"')");
                    Toast.makeText(MainActivity.this,"da them",Toast.LENGTH_SHORT).show();                 
                    dialog.dismiss();
                    GetDataCV();
                }
            }
        });
        dialog.show();
    }
}
