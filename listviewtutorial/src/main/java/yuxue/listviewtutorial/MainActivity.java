package yuxue.listviewtutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button addBtn;
    private EditText unitNameEt;

    protected SimpleAdapter MyListAdapter;

    protected List<HashMap<String, String>> UnitListArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView unitList = (ListView) this.findViewById(R.id.listview);
        addBtn=(Button)findViewById(R.id.btn_add);
        unitNameEt=(EditText)findViewById(R.id.et_unit_name);
        UnitListArray = new ArrayList<HashMap<String, String>>();
        String[] colHEAD = new String[]{"CODE", "UNITS", "SEMESTER"};
        int[] dataCell = new int[]{R.id.UnitCode, R.id.UnitName, R.id.Semester};
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("CODE", "FIT5183");
        map.put("UNITS", "Mobile and Distributed Computing");
        map.put("SEMESTER", "Sem 2A 2017");
        UnitListArray.add(map);
        MyListAdapter = new SimpleAdapter(this, UnitListArray, R.layout.list_view, colHEAD, dataCell);
        unitList.setAdapter(MyListAdapter);
        addBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUnit=unitNameEt.getText().toString();
                String[] unitsArray=newUnit.split(",");
                if(unitsArray.length!=3){
                    Toast.makeText(v.getContext(),"Please input Unit code, name and semester",Toast.LENGTH_LONG).show();
                    return;
                }
                HashMap<String,String> map=new HashMap<String, String>();
                map.put("CODE", unitsArray[0]);
                map.put("UNITS", unitsArray[1]);
                map.put("SEMESTER", unitsArray[2]);
                addMap(map);
            }
        });
    }

    private void addMap(HashMap<String,String> map){
        UnitListArray.add(map);
        MyListAdapter.notifyDataSetChanged();
    }

}
