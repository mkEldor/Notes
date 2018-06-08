package adilbek.assigmentnotes;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * The configuration screen for the {@link NoteWidget NoteWidget} AppWidget.
 */
public class NoteWidgetConfigureActivity extends Activity {

    private static final String PREFS_NAME = "adilbek.assigmentnotes.NoteWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    MyWidgetAdapter myWidgetAdapter;
    ListView mAppWidgetListView;
    DataBase dataBase;

    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("!!!!!!", "CLICK!!!!");
            final Context context = NoteWidgetConfigureActivity.this;
            // When the button is clicked, store the string locally
            TextView textView = view.findViewById(R.id.nodeWidgetTextTextView);
            String widgetText = textView.getText().toString();
            TextView numberOfNoteTextView = view.findViewById(R.id.widgetnumberofnote);
            long numberOfNote = Long.parseLong(numberOfNoteTextView.getText().toString());
            saveTitlePref(context, mAppWidgetId, widgetText, dataBase.getItem(numberOfNote).getColor());

            // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            NoteWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };


    public NoteWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text, int color) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId, text);
        prefs.putInt(PREF_PREFIX_KEY + "COLOR" + appWidgetId, color);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.appwidget_text);
        }
    }

    static int loadColorPref(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int titleValue = prefs.getInt(PREF_PREFIX_KEY + "COLOR" + appWidgetId, -1);
        if (titleValue != -1) {
            return titleValue;
        } else {
            return -1;
        }
    }

    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.note_widget_configure);

        mAppWidgetListView = (ListView) findViewById(R.id.widgetListView);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        dataBase = new DataBase(getApplicationContext());

        myWidgetAdapter = new MyWidgetAdapter(getApplicationContext(), R.layout.note_widget_list_view_item, dataBase.getAllNotes());

        mAppWidgetListView.setAdapter(myWidgetAdapter);

        mAppWidgetListView.setOnItemClickListener(mOnItemClickListener);
    }
}

