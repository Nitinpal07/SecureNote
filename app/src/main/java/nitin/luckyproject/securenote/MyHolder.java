package nitin.luckyproject.securenote;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {
    View mview;
    TextView mtitle,mnote,mtimestamp;

    public MyHolder(@NonNull View itemView) {
        super(itemView);

        mview =itemView;
        mtitle = mview.findViewById(R.id.title_item);
        mnote=mview.findViewById(R.id.note_item);
        mtimestamp =mview.findViewById(R.id.date_item);
    }

    public void setNoteTitle(String title){
     mtitle.setText(title);
    }
    public void setNote(String note){
        mnote.setText(note);

    }
    public void setNoteTimeStamp(String timestamp){
       mtimestamp.setText(timestamp);
    }
}
