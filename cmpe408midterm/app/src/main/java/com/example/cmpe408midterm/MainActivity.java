package com.example.cmpe408midterm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button btncreate, btncalculate, btnreset, btnexit;
    private TextView result;
    private EditText editrownumbers, editcolumnnumbers;
    private TableLayout table;
    List<CheckBox> checkBoxList = new ArrayList<>();//checkboxları bulmak için

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btncreate = findViewById(R.id.btncreate);
        btncalculate = findViewById(R.id.btncalculate);
        btnreset = findViewById(R.id.btnreset);
        btnexit = findViewById(R.id.btnexit);
        result = findViewById(R.id.result);
        editrownumbers = findViewById(R.id.editrownumbers);
        editcolumnnumbers = findViewById(R.id.editcolumnnumbers);
        table = findViewById(R.id.table);
        btncreate.setOnClickListener(v -> {
            table.removeAllViews();//iki kere create basılınca ilk tabloyu temizliyor
            checkBoxList.clear();//  hafıza şişmesin diye temizliyor
            int r;
            int c;
            try {
                r = Integer.parseInt(editrownumbers.getText().toString());//integera çeviriyo
                c = Integer.parseInt(editcolumnnumbers.getText().toString());
                Toast.makeText(this, "row= " + r + "columns=" + c, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Row ve Col değerleri boş bırakılamaz", Toast.LENGTH_SHORT).show();
                //numara girilmezse de çalışmaya devam etsin diye
                return;
            }
            create(r, c);

        });
        btnreset.setOnClickListener(new View.OnClickListener()) {//reset butonu için
            @Override
            public void onClick(View v) {
                checkBoxList.clear();
                table.removeAllViews();
                editcolumnnumbers.setText("");
                editrownumbers.setText("");
                result.setText("");
            }
        });
        btncalculate.setOnClickListener(v -> {
            int total = 0;
            for (CheckBox box : checkBoxList) {
                if (box.isChecked()) {
                    String ob = (String) box.getTag();
                    int row = Integer.parseInt(ob.replace("check_", ""));
                    TableRow tableRow = (TableRow) table.getChildAt(row);
                    for (int k = 0; k < tableRow.getChildCount(); k++) {//seçili satırdaki değerleri almak için
                        if (!(tableRow.getChildAt(k) instanceof CheckBox)) {//checkbox değil sadece textview almak için
                            TextView textView = (TextView) tableRow.getChildAt(k);
                            total += Integer.parseInt(textView.getText().toString());

                        }
                    }
                }
                result.setText(total + "");
            }
        });
        btnexit.setOnClickListener(v -> { //lamda şeklinde yazma
            finish();
            System.exit(0);

        });
    }

    public void create(int row, int col) {
        for (int i = 0; i < row; i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(//params xmlde yazmadık diye burda bu şekilde yazıyoruz
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            if(i%2==0){//bi yeşil bi mavi yapmak için
                tableRow.setBackgroundColor(Color.GREEN);
            }else{
                tableRow.setBackgroundColor(Color.BLUE);

            }
            for (int j = 0; j < col; j++) {
                TextView v = new TextView(this);
                v.setLayoutParams(new TableRow.LayoutParams(
                        100,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                int random = new Random().nextInt(10);
                v.setText(random + "");
                tableRow.addView(v);
            }
            CheckBox checkBox = new CheckBox(this);
            checkBoxList.add(checkBox);
            checkBox.setTag("check_" + i);
            tableRow.addView(checkBox);
            table.addView(tableRow);
        }
    }
}