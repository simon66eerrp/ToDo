package com.example.user.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
private EditText editText;
    private EditText editText2;
    private EditText editText3;
    private Button button;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String world = bundle.getString("str");
        id =bundle.getInt("id");
        editText=(EditText) findViewById(R.id.editText);
        editText2=(EditText) findViewById(R.id.editText2);
        editText3=(EditText) findViewById(R.id.editText3);

button =(Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // mList.set(pos,"GGGGGG");

                Intent intent = new Intent();
                intent.putExtra("result", editText.getText().toString());
                intent.putExtra("id",id);
                intent.putExtra("result2", editText2.getText().toString());
                intent.putExtra("result3", editText3.getText().toString());
                /*
                 * 调用setResult方法表示我将Intent对象返回给之前的那个Activity，这样就可以在onActivityResult方法中得到Intent对象，
                 */
                setResult(1001, intent);
                //    结束当前这个Activity对象的生命
                finish();
            }
        });
        editText.setText(world);

    }
}
