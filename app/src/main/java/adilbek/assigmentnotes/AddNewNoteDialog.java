package adilbek.assigmentnotes;

import android.app.Dialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class AddNewNoteDialog extends Dialog {
    private EditText dialogText;
    DataBase dataBase;
    Button yesButton;
    Button noButton;
    MainActivity mainActivity;

    public AddNewNoteDialog(@NonNull Context context) {
        super(context);
        mainActivity = (MainActivity)context;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.note_add_dialog_layout);

        dialogText = (EditText) findViewById(R.id.noteEditText);
        dataBase = new DataBase(getContext());

        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                saveNewNote();
                dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveNewNote() {
        /** Take text from dialog */
        String text = dialogText.getText().toString();
        if(text.isEmpty())
            return;

        /** Check date */
        Date dateMS = new Date();
        dateMS.setTime(21600000+dateMS.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String date = dateFormat.format(dateMS);


        /** Add new note to database */
        Note note = new Note(date, text, R.color.changeColorOneYellow, System.nanoTime());
        dataBase.addNewNote(note);

        /** ALWAYS CLOSE DATABASE */
        dataBase.close();

        mainActivity.updateList(dataBase.getAllNotes());
    }
}
