package nitin.luckyproject.securenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditorActivity extends AppCompatActivity {

    private EditText mTitle;
    private EditText mNote;
    ProgressDialog mProgressDialog;
    SpectrumPalette mSpectrumPalette;
    int color;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseReference;
    public EditorActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);


        getSupportActionBar().setTitle("NEW NOTE");


        mTitle = findViewById(R.id.title);
        mNote = findViewById(R.id.note);

         mFirebaseAuth = FirebaseAuth.getInstance();
         mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Notes");

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

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


        if(mFirebaseAuth.getCurrentUser() != null){

            DatabaseReference databaseReference = mDatabaseReference.child(mFirebaseAuth.getCurrentUser().getUid()).push();
            Map<String, Object> map = new HashMap<>();
            map.put("id", databaseReference.getKey());
            map.put("title", title);
            map.put("note", note);
            map.put("timestamp",ServerValue.TIMESTAMP);
            map.put("color",color);

            databaseReference.setValue(map);
            Toast.makeText(this, "Added note", Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, "User is not signed in", Toast.LENGTH_SHORT).show();
            Log.d("User","false");
        }

    }

    // Back arrow click event to go to the parent Activity
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        return true;
    }
}
