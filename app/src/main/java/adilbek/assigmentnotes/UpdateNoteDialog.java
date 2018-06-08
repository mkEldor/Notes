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
import android.widget.RelativeLayout;

import java.util.Date;


public class UpdateNoteDialog extends Dialog {
    private EditText dialogText;
    DataBase dataBase;
    Button yesButton;
    Button noButton;
    MainActivity mainActivity;
    Note oldNote;

    public UpdateNoteDialog(@NonNull Context context, Note thisNote) {
        super(context);
        mainActivity = (MainActivity)context;
        this.oldNote = thisNote;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.note_add_dialog_layout);

        dialogText = (EditText) findViewById(R.id.noteEditText);
        dialogText.setText(oldNote.getText());
        dataBase = new DataBase(getContext());

        yesButton = (Button) findViewById(R.id.yesButton);
        noButton = (Button) findViewById(R.id.noButton);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                updateNote();
                dismiss();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        final RelativeLayout dialogLayout = (RelativeLayout) findViewById(R.id.dialogLayout);

        dialogLayout.setBackgroundColor(getContext().getColor(oldNote.getColor()));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateNote() {
        /** Take text from dialog */
        String text = dialogText.getText().toString();

        /** Check if is empty delete note */
        if(text.isEmpty()) {
            dataBase.deleteNote(oldNote.getNumber());
            mainActivity.updateList(dataBase.getAllNotes());
            return;
        }

        /** Check date */
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        String date = dateFormat.format(new Date());


        /** Update note from database */
        Note newNote = new Note(date, text, oldNote.getColor(), oldNote.getNumber());
        dataBase.update(oldNote, newNote);

        /** ALWAYS CLOSE DATABASE */
        dataBase.close();

        mainActivity.updateList(dataBase.getAllNotes());
    }
}
