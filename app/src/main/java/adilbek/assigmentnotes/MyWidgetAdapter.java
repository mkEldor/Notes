package adilbek.assigmentnotes;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by adilbek on 23.10.17.
 */

public class MyWidgetAdapter extends ArrayAdapter<Note> {


    public MyWidgetAdapter(Context context, int resours, ArrayList<Note> users) {
        super(context, resours, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /** Get note by position */
        final Note note = getItem(position);

        /** If convertView is empty update it */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.note_widget_list_view_item, parent, false);
        }

        /** Create TextView for date */
        final TextView dateTextView = (TextView) convertView.findViewById(R.id.nodeWidgetDateTextView);
        /** Set date to date TextView */
        dateTextView.setText(note.getDate());

        /** Create TextView for text */
        final TextView textTextView = (TextView) convertView.findViewById(R.id.nodeWidgetTextTextView);
        /** Set text to text TextView*/
        textTextView.setText(note.getText());

        final TextView numberOfNoteTextView = (TextView) convertView.findViewById(R.id.widgetnumberofnote);

        numberOfNoteTextView.setText(note.getNumber()+"");

        final MyColors myColors = new MyColors(note.getColor());

        Log.d("COLOR!!", myColors.getColor()+"");
        GradientDrawable backgroundGradient = (GradientDrawable)textTextView.getBackground();
        backgroundGradient.setColor(getContext().getColor(myColors.getColor()));

        /** Always return convertView */
        return convertView;
    }

}
