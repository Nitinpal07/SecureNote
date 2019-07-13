package nitin.luckyproject.securenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumPalette;

public class EditorActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mNote;
    ProgressDialog mProgressDialog;
    SpectrumPalette mSpectrumPalette;
    int color;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mTitle = findViewById(R.id.title);
        mNote = findViewById(R.id.note);




        mSpectrumPalette =findViewById(R.id.palette);
        mSpectrumPalette.setOnColorSelectedListener(
                clr -> color =clr);

        //default color
        mSpectrumPalette.setSelectedColor(getResources().getColor(R.color.white));
        color = getResources().getColor(R.color.white);

        mProgressDialog =new ProgressDialog(this);
        mProgressDialog.setMessage("Please Wait ...");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_editor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.save:
                String title = mTitle.getText().toString().trim();
                String note =mNote.getText().toString().trim();
                int color= this.color;
                if(title.isEmpty()){
                    Toast.makeText(this, "Enter Title", Toast.LENGTH_SHORT).show();
                }
                else if(note.isEmpty()){
                    Toast.makeText(this, "Enter Note", Toast.LENGTH_SHORT).show();
                }
                else{
                    savenote(title,note,color);
                }
                return true;
            default:
                    return super.onOptionsItemSelected(item);
        }


    }

    private void savenote(String title, String note, int color) {

        mProgressDialog.show();
    }
}
