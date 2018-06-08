package adilbek.assigmentnotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DataBase extends SQLiteOpenHelper {

    /** Database first version */
    public static final int DATABASE_VERSION = 1;
    /** Our datebase name */
    public static final String DATABASE_NAME = "MYDATABASE";
    /** Notes table name*/
    public static final String NOTES_TABLE = "NOTESTABLE";

    /** Colums Name */
    public static final String ID = "_id";
    public static final String DATE = "_date";
    public static final String TEXT = "_text";
    public static final String COLOR = "_color";
    public static final String NUMBEROFNOTE = "_numberofnote";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + NOTES_TABLE + "("
            + ID +" integer primary key," + DATE + " text," + TEXT + " text," + COLOR + " integer," + NUMBEROFNOTE + " integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + NOTES_TABLE);
        onCreate(sqLiteDatabase);
    }

    /** Method for add new note*/
    public void addNewNote(Note newNote) {
        /** Create WRITABLE, READABLE database */
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /** Create contentValues*/
        ContentValues contentValues = new ContentValues();

        /** Put new note (date, text) to contentValue */
        contentValues.put(DATE, newNote.getDate());
        contentValues.put(TEXT, newNote.getText());
        contentValues.put(COLOR, newNote.getColor());
        contentValues.put(NUMBEROFNOTE, newNote.getNumber());

        /** Insert content values to database */
        sqLiteDatabase.insert(NOTES_TABLE, null, contentValues);
        sqLiteDatabase.close();
    }

    /** Method for take all notes from database*/
    public ArrayList<Note> getAllNotes() {
        /** Create notes list */
        ArrayList<Note> notes = new ArrayList<>();

        /** Create WRITABLE, READABLE database */
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /** Create cursor for NOTES_TABLE without filters */
        Cursor cursor = sqLiteDatabase.query(NOTES_TABLE, null, null, null, null, null, null);

        /** Check if database is not empty */
        if(cursor.moveToFirst()) {
            do {
                /** Take columns index */
                int dateIndex = cursor.getColumnIndex(DATE);
                int textIndex = cursor.getColumnIndex(TEXT);
                int colorIndex = cursor.getColumnIndex(COLOR);
                int numberIndex = cursor.getColumnIndex(NUMBEROFNOTE);
                /** Take date and text from this row */
                String date = cursor.getString(dateIndex);
                String text = cursor.getString(textIndex);
                int color = cursor.getInt(colorIndex);
                long number = cursor.getLong(numberIndex);
                /** Add note to list */
                Note newNote = new Note(date, text, color, number);
                notes.add(newNote);
            } while (cursor.moveToNext());
        }

        /** ALWAYS CLOSE DATABASE AND CURSOR*/
        sqLiteDatabase.close();
        cursor.close();

        /** Return notes (ArrayList<Note>) */
        return notes;
    }

    /** Delete note */
    public boolean deleteNote(long number) {
        /** Create WRITABLE, READABLE database */
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /** Delete row by number */
        sqLiteDatabase.delete(NOTES_TABLE, NUMBEROFNOTE + "= " + number, null);

        /** ALWAYS CLOSE DATABASE*/
        sqLiteDatabase.close();

        /** If note deletes return true */
        return true;
    }

    public boolean update(Note oldNote, Note newNote) {

        deleteNote(oldNote.getNumber());

        addNewNote(newNote);

        /** If note updates return true */
        return true;
    }

    public Note getItem(long searchNumber) {
        /** Create WRITABLE, READABLE database */
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /** Create cursor for NOTES_TABLE without filters */
        Cursor cursor = sqLiteDatabase.query(NOTES_TABLE, null, null, null, null, null, null);

        /** Check if database is not empty */
        if(cursor.moveToFirst()) {
            do {
                /** Take columns index */
                int dateIndex = cursor.getColumnIndex(DATE);
                int textIndex = cursor.getColumnIndex(TEXT);
                int colorIndex = cursor.getColumnIndex(COLOR);
                int numberIndex = cursor.getColumnIndex(NUMBEROFNOTE);
                /** Take date and text from this row */
                String date = cursor.getString(dateIndex);
                String text = cursor.getString(textIndex);
                int color = cursor.getInt(colorIndex);
                long number = cursor.getLong(numberIndex);
                /** Add note to list */
                if(searchNumber == number) {
                    Note newNote = new Note(date, text, color, number);
                    return newNote;
                }
            } while (cursor.moveToNext());
        }

        /** ALWAYS CLOSE DATABASE AND CURSOR*/
        sqLiteDatabase.close();
        cursor.close();

        /** Return notes (ArrayList<Note>) */
        return null;
    }

    public ArrayList<Note> getItemsByColors(int colorID) {
        /** Create notes list */
        ArrayList<Note> notes = new ArrayList<>();

        /** Create WRITABLE, READABLE database */
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        /** Create cursor for NOTES_TABLE without filters */
        Cursor cursor = sqLiteDatabase.query(NOTES_TABLE, null, null, null, null, null, null);

        /** Check if database is not empty */
        if(cursor.moveToFirst()) {
            do {
                /** Take columns index */
                int dateIndex = cursor.getColumnIndex(DATE);
                int textIndex = cursor.getColumnIndex(TEXT);
                int colorIndex = cursor.getColumnIndex(COLOR);
                int numberIndex = cursor.getColumnIndex(NUMBEROFNOTE);
                /** Take date and text from this row */
                String date = cursor.getString(dateIndex);
                String text = cursor.getString(textIndex);
                int color = cursor.getInt(colorIndex);
                long number = cursor.getLong(numberIndex);
                /** Add note to list */
                if(colorID == color) {
                    Note newNote = new Note(date, text, color, number);
                    notes.add(newNote);
                }
            } while (cursor.moveToNext());
        }

        /** ALWAYS CLOSE DATABASE AND CURSOR*/
        sqLiteDatabase.close();
        cursor.close();

        /** Return notes (ArrayList<Note>) */
        return notes;
    }

}
