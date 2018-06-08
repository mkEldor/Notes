package adilbek.assigmentnotes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    MyAdapter simpleAdapter;
    ListView listView;
    DataBase dataBase;
    AddNewNoteDialog addNewNoteDialog;
    ArrayList<Note> items;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);

        dataBase = new DataBase(this);

        items = dataBase.getAllNotes();

        if(items.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else
            Collections.reverse(items);

        simpleAdapter = new MyAdapter(this, R.layout.list_view_items, items);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addbutton:
                addNewNoteDialog = new AddNewNoteDialog(MainActivity.this);
                addNewNoteDialog.show();
                break;
            case R.id.allColor:
                updateList(dataBase.getAllNotes());
                break;
            case R.id.redColor:
                updateList(dataBase.getItemsByColors(R.color.changeColorFourRed));
                break;
            case R.id.blueColor:
                updateList(dataBase.getItemsByColors(R.color.changeColorThreeDarkBlue));
                break;
            case R.id.lightBlueColor:
                updateList(dataBase.getItemsByColors(R.color.changeColorTwoLightBlue));
                break;
            case R.id.yellowColor:
                updateList(dataBase.getItemsByColors(R.color.changeColorOneYellow));
                break;
            default:
                break;
        }
        return true;
    }

    public void updateList(ArrayList<Note> list) {
        items = list;
        Collections.reverse(items);

        if(items.isEmpty())
            textView.setVisibility(View.VISIBLE);
        else
            textView.setVisibility(View.INVISIBLE);

        simpleAdapter = new MyAdapter(this, R.layout.list_view_items, items);
        listView.setAdapter(simpleAdapter);
    }
}
