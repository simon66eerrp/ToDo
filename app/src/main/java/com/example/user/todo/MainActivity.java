package com.example.user.todo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    String[] names = new String[] { "今天", "明天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天", "後天" };
    MyAdapter adapter = null;
    TodoDAO todoDAO;
    private Button edit;
    private Button create;
    private ArrayList<TodoData> mList =new ArrayList();
    MainActivity self =this;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000 && resultCode == 1001)
        {
            String result_value = data.getStringExtra("result");
            String result_value2 = data.getStringExtra("result2");
            String result_value3 = data.getStringExtra("result3");
            int id =(int)data.getIntExtra("id",0);
            TodoData todoData = new TodoData();
            todoData.setContent(result_value);
            todoData.setMonth(result_value2);
            todoData.setDay(result_value3);
            todoData.setId(mList.get(id).getId());
            mList.set(id,todoData);
            todoDAO.update(todoData);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView);
        edit=(Button)findViewById(R.id.edit);
        create =(Button)findViewById(R.id.create);
        todoDAO = new TodoDAO(getApplicationContext());
        TodoData todoData =new TodoData();
        todoData.setContent("sssssDB");
        todoData.setMonth("12");
        todoData.setDay("1");
        todoData.setId(0);

        todoDAO.insert(todoData);
        List<TodoData > todoDataList=todoDAO.getAll();

        for (int i =0 ;i<todoDataList.size();i++){
            mList.add(todoDataList.get(i));
        }
       // mList.add("GG1");

        create.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TodoData todoData =new TodoData();
                todoData.setContent("sssss");
                todoData.setMonth("12");
                todoData.setDay("1");
                todoData.setId(0);
                mList.add(todoData);
                adapter.notifyDataSetChanged();
            }
        });
        adapter = new MyAdapter(this);
        list.setAdapter(adapter);
//
//        list.setOnItemClickListener(new ListView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//                                    long arg3) {
//                // TODO Auto-generated method stub
//                ListView listView = (ListView) arg0;
//                TextView  name =(TextView) arg0.findViewById(R.id.name);
//                name.setText(mList.get((int)arg3));
//                Intent  intent = new Intent(self, Main2Activity.class);
//
//                Bundle bundle = new Bundle();  //　　Bundle的底层是一个HashMap<String, Object
//                bundle.putString("str",mList.get((int)arg3));
//                bundle.putInt("id",(int)arg3);
//                intent.putExtra("bundle", bundle);
//
//                startActivityForResult(intent, 1000);
//
//            }
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.);
        return true;
    }

    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;

        public MyAdapter(Context c) {

            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount() {
// TODO Auto-generated method stub
            return mList.size();
           // return names.length;
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return names[position];
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }



        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            convertView = myInflater.inflate(R.layout.todo, null);
            final TextView name = (TextView) convertView.findViewById(R.id.name);
            final TextView time = (TextView) convertView.findViewById(R.id.textView);

            Button edit = (Button) convertView.findViewById(R.id.edit);
            Button delete = (Button) convertView.findViewById(R.id.delete);
            final  int pos = position;
            edit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   // mList.set(pos,"GGGGGG");

                    name.setText(mList.get(pos).getContent());
                    Intent  intent = new Intent(self, Main2Activity.class);

                    Bundle bundle = new Bundle();  //　　Bundle的底层是一个HashMap<String, Object
                    bundle.putString("str",mList.get(pos).getContent());
                    bundle.putInt("id",pos);
                    intent.putExtra("bundle", bundle);

                    startActivityForResult(intent, 1000);


                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    todoDAO.delete(mList.get(pos).getId());
                    mList.remove(pos);
                    adapter.notifyDataSetChanged();
                }
            });
            name.setText(mList.get(position).getContent());
            time.setText(mList.get(position).getMonth()+"/"+mList.get(position).getDay());
            return convertView;
        }

    }
}
