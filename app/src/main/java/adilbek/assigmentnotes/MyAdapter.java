package adilbek.assigmentnotes;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter<Note> {

    DataBase dateBase;
    MainActivity mainActivity;

    public MyAdapter(Context context, int resours, ArrayList<Note> users) {
        super(context, resours, users);
        dateBase = new DataBase(context);
        mainActivity = (MainActivity)context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /** Get note by position */
        final Note note = getItem(position);

        /** If convertView is empty update it */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_view_items, parent, false);
        }

        /** Create TextView for date */
        final TextView dateTextView = (TextView) convertView.findViewById(R.id.nodeDateTextView);
        /** Set date to date TextView */
        dateTextView.setText(note.getDate());

        /** Create TextView for text */
        final TextView textTextView = (TextView) convertView.findViewById(R.id.nodeTextTextView);
        /** Set text to text TextView*/
        textTextView.setText(note.getText());
        textTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateNoteDialog updateNoteDialog = new UpdateNoteDialog(getContext(), note);
                updateNoteDialog.show();
            }
        });


        TextView numberOfNote = (TextView) convertView.findViewById(R.id.numberofnote);
        numberOfNote.setText(String.valueOf(note.getNumber()));

        /** Create ImageView for delete */
        final ImageView deleteImageView = (ImageView) convertView.findViewById(R.id.nodeWidgetDeleteButton);
        /** Delete action if click deleteImageView */
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /** Call delete method from dateBase (by name and text) */
                dateBase.deleteNote(note.getNumber());
                mainActivity.updateList(dateBase.getAllNotes());
            }
        });

        final MyColors myColors = new MyColors(note.getColor());

        GradientDrawable backgroundGradient = (GradientDrawable)textTextView.getBackground();
        backgroundGradient.setColor(getContext().getColor(myColors.getColor()));

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradientDrawable backgroundGradient = (GradientDrawable)textTextView.getBackground();
                backgroundGradient.setColor(getContext().getColor(myColors.getNextColor()));
                Note newNote = new Note(note.getDate(), note.getText(), myColors.getColor(), note.getNumber());
                dateBase.update(note, newNote);
                note.setColor(newNote.getColor());
            }
        });

        dateBase.close();

        /** Always return convertView */
        return convertView;
    }


}
