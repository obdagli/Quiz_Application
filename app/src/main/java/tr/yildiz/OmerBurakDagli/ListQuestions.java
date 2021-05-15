package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.yildiz.edu.OmerBurakDagli.R;

import tr.yildiz.OmerBurakDagli.adapter.RecycleAdapter;

import java.util.ArrayList;

public class ListQuestions extends AppCompatActivity {

    private RecyclerView Recycler;
    private RecycleAdapter adapter;
    private ArrayList<Question> questions = new ArrayList<Question>();
    SharedPreferences.Editor speditor;
    SharedPreferences sp;
    private database v1;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_questions);
        getInput();
        getSharedInformation();
        username = sp.getString("username","not defined.");
        adapter.notifyDataSetChanged();
        v1 = new database(this);
        showdata(readdata());

    }

    private String[] columns={"question","choice1","choice2","choice3","choice4","answer","choicesize","userfk"};
    private Cursor readdata(){
        SQLiteDatabase db = v1.getReadableDatabase();
        Cursor read = db.query("Question",columns,null,null,null,null,null);
        return read;
    }
    private void showdata(Cursor show){

        while( show.moveToNext()) {

            if(username.equals(show.getString(show.getColumnIndex("userfk")))){

                String question = show.getString(show.getColumnIndex("question"));
                ArrayList<String> choices = new ArrayList<String>();
                if(show.getString(show.getColumnIndex("choicesize")).equals("2")){
                    choices.add(show.getString(show.getColumnIndex("choice1")));
                    choices.add(show.getString(show.getColumnIndex("choice2")));

                }else if(show.getString(show.getColumnIndex("choicesize")).equals("3")){
                    choices.add(show.getString(show.getColumnIndex("choice1")));
                    choices.add(show.getString(show.getColumnIndex("choice2")));
                    choices.add(show.getString(show.getColumnIndex("choice3")));
                }else if(show.getString(show.getColumnIndex("choicesize")).equals("4")){
                    choices.add(show.getString(show.getColumnIndex("choice1")));
                    choices.add(show.getString(show.getColumnIndex("choice2")));
                    choices.add(show.getString(show.getColumnIndex("choice3")));
                    choices.add(show.getString(show.getColumnIndex("choice4")));
                }
                String answer = show.getString(show.getColumnIndex("answer"));
                Question q = new Question(question,answer,choices);
                questions.add(q);
            }
        }
    }
    public void getSharedInformation(){
        sp = getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
    public void getInput(){
        Recycler = (RecyclerView) findViewById(R.id.recyclerview);

        adapter = new RecycleAdapter(questions, ListQuestions.this);
        Recycler.setAdapter(adapter);
        Recycler.setLayoutManager(new LinearLayoutManager(this));

    }

    public void deleteQuestion(){

    }
}