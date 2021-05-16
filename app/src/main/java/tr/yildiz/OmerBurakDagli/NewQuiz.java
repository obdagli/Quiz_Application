package tr.yildiz.OmerBurakDagli;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.yildiz.edu.OmerBurakDagli.R;

import tr.yildiz.OmerBurakDagli.adapter.RecycleAdapterCreateQuiz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class NewQuiz extends AppCompatActivity {
    EditText et_qtime,et_qpoint;
    public static RadioButton rb_2,rb_3,rb_4;
    Button bt_change;
    String diff;
    SharedPreferences sp;
    SharedPreferences.Editor speditor;
    private RecyclerView Recycler;
    private ArrayList<Question> questions = new ArrayList<Question>();
    public static ArrayList<Question> questions2 = new ArrayList<Question>();
    private RecycleAdapterCreateQuiz adapter;
    private database v1;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quiz);
        callInputs();
        getSharedInformation();
        username = sp.getString("username","not defined.");
        v1 = new database(this);
        spcaller();
        spcaller_rb(sp.getString("difficulty","not defined."));
        getInput(createlist());
        showdata(readdata());
        String filename = "quiz.txt";
        File file = new File(getApplicationContext().getFilesDir(), filename);

        bt_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_qtime.setText(et_qtime.getText().toString());
                et_qpoint.setText(et_qpoint.getText().toString());
                diff = checker();
                spcaller_rb(diff);

                String fileContents = qfile(et_qtime.getText().toString(),et_qpoint.getText().toString(),diff);
                try(FileOutputStream fos = getApplicationContext().openFileOutput(filename, Context.MODE_PRIVATE)){
                    fos.write(fileContents.getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        rb_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput(createlist());
                questions2.clear();
            }
        });
        rb_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput(createlist());
                questions2.clear();
            }
        });
        rb_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput(createlist());
                questions2.clear();
            }
        });
    }
    public String qfile(String time, String point, String difficulty){
        String input = "";
        input += "Time\tPoint\tDifficulty\n";
        input += time+"\t\t"+point+"\t\t"+difficulty+"\n\n";

        for(Question q: questions2){
            input += "Question\n--------\n"+q.getQuestion()+"\n--------\nAnswers(Correct Answer '"+q.getAnswer()+"')\n";
            input += "A-) "+q.getChoices().get(0)+"\n";
            input += "B-) "+q.getChoices().get(1)+"\n";
            if(q.getChoices().size() == 3){
                input += "C-) "+q.getChoices().get(2)+"\n\n";
            }else if(q.getChoices().size() == 4){
                input += "C-) "+q.getChoices().get(2)+"\n";
                input += "D-) "+q.getChoices().get(3)+"\n\n";
            }
        }
        return input;
    }
    public void getInput(ArrayList<Question> arrl){
        Recycler = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecycleAdapterCreateQuiz(arrl);
        Recycler.setAdapter(adapter);
        Recycler.setLayoutManager(new LinearLayoutManager(this));

    }
    public String checker(){
        if(rb_2.isChecked()){
            return "2";
        }else if(rb_3.isChecked()){
            return "3";
        }else if(rb_4.isChecked()){
            return "4";
        }else{
            return "5";
        }
    }
    public ArrayList<Question> createlist(){
        ArrayList<Question> temp = new ArrayList<Question>();
        for(Question q : questions){
            if(rb_2.isChecked()){
                if(q.getChoices().size() == 2){
                    temp.add(q);
                }
            }else if(rb_3.isChecked()){
                if(q.getChoices().size() == 3){
                    temp.add(q);
                }
            }else if(rb_4.isChecked()){
                if(q.getChoices().size() == 4){
                    temp.add(q);
                }
            }
        }
        return temp;
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
    public void callInputs(){
        et_qtime = findViewById(R.id.et_qtime);
        et_qpoint = findViewById(R.id.et_qpoint);
        rb_2 = findViewById(R.id.rb_2);
        rb_3 = findViewById(R.id.rb_3);
        rb_4 = findViewById(R.id.rb_4);
        bt_change = findViewById(R.id.bt_change);
    }
    public void spcaller(){
        et_qtime.setText(sp.getString("time","not defined."));
        et_qpoint.setText(sp.getString("point","not defined."));

    }
    public void spcaller_rb(String rb_checker){
        if(rb_checker.matches("2")){
            rb_2.setChecked(true);
        }else if(rb_checker.matches("3")){
            rb_3.setChecked(true);
        }else if(rb_checker.matches("4")){
            rb_4.setChecked(true);
        }
    }
    public void getSharedInformation(){
        sp = getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
}