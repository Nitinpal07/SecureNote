package nitin.luckyproject.securenote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private FloatingActionButton mFab;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mMyRef;
    private Button mBtn;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseRecyclerAdapter<model, MyHolder> mFirebaseRecyclerAdapter;
    private SwipeRefreshLayout mMSwiperefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMSwiperefreshLayout = findViewById(R.id.swipe_refresh);
        mMSwiperefreshLayout.setOnRefreshListener(() -> doYourUpdate(mMSwiperefreshLayout));

        mBtn = findViewById(R.id.sign_out);
        mDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        if(mFirebaseAuth.getCurrentUser() !=null){
            mMyRef = mDatabase.getReference().child("Notes").child(mFirebaseAuth.getCurrentUser().getUid());
        }


        mFab = findViewById(R.id.add);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "New Note", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,EditorActivity.class);
                startActivity(intent);


            }
        });

        mBtn.setOnClickListener(view -> {
            AuthUI.getInstance()
                    .signOut(MainActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            signOut();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, ""+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

       fetch();

    }
    private void signOut() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
         mFirebaseRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseRecyclerAdapter.stopListening();
    }

    public void fetch(){
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Notes").child(mFirebaseAuth.getCurrentUser().getUid());
        FirebaseRecyclerOptions<model> options = new FirebaseRecyclerOptions.Builder<model>()
                .setQuery(query, snapshot -> new model(snapshot.child("title").getValue().toString(),
                        snapshot.child("note").getValue().toString(),
                        snapshot.child("color").getValue().toString(),
                        snapshot.child("id").getValue().toString(),
                        snapshot.child("timestamp").getValue().toString()))
                .build();


        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<model, MyHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyHolder holder, int i, @NonNull model model) {
                holder.setNoteTitle(model.getTitle());
                holder.setNote(model.getNote());
                holder.setNoteTimeStamp(model.getTimestamp());

                holder.mview.setBackgroundColor(Integer.parseInt(model.getColor()));
            }

            @NonNull
            @Override
            public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.note_item, parent, false);

                return new MyHolder(view);
            }
        };



        mRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
    }





    private void doYourUpdate(SwipeRefreshLayout sr) {
        sr.setRefreshing(false); // Disables the refresh icon
    }

}
