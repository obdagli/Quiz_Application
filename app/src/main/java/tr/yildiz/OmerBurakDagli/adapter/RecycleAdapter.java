package tr.yildiz.OmerBurakDagli.adapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; import androidx.annotation.NonNull; import androidx.recyclerview.widget.RecyclerView;

import tr.yildiz.OmerBurakDagli.Question;
import tr.yildiz.OmerBurakDagli.QuestionUpdate;
import com.yildiz.edu.OmerBurakDagli.R;
import tr.yildiz.OmerBurakDagli.database;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyViewHolder> {

    private ArrayList<Question> questions;
    Context context;
    SharedPreferences.Editor speditor;
    SharedPreferences sp;
    private database v1;
    String username;

    public RecycleAdapter(ArrayList<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlequestion,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        getSharedInformation();
        username = sp.getString("username","not defined.");
        v1 = new database(context);

        if(questions.get(position).getChoices().size() == 2){
            String answer = questions.get(position).getAnswer();
            holder.question.setText("Soru : " + questions.get(position).getQuestion());
            holder.text_answera.setText("A) " + questions.get(position).getChoices().get(0));
            holder.text_answerb.setText("B) " + questions.get(position).getChoices().get(1));
            holder.tv_correctanswer.setText("Correct Answer is '" + questions.get(position).getAnswer() + "'");
        }else if(questions.get(position).getChoices().size() == 3){
            String answer = questions.get(position).getAnswer();
            holder.question.setText("Soru : " + questions.get(position).getQuestion());
            holder.text_answera.setText("A) " + questions.get(position).getChoices().get(0));
            holder.text_answerb.setText("B) " + questions.get(position).getChoices().get(1));
            holder.text_answerc.setText("C) " + questions.get(position).getChoices().get(2));
            holder.tv_correctanswer.setText("Correct Answer is '" + questions.get(position).getAnswer() + "'");
        }else if(questions.get(position).getChoices().size() == 4){
            String answer = questions.get(position).getAnswer();
            holder.question.setText("Soru : " + questions.get(position).getQuestion());
            holder.text_answera.setText("A) " + questions.get(position).getChoices().get(0));
            holder.text_answerb.setText("B) " + questions.get(position).getChoices().get(1));
            holder.text_answerc.setText("C) " + questions.get(position).getChoices().get(2));
            holder.text_answerd.setText("D) " + questions.get(position).getChoices().get(3));
            holder.tv_correctanswer.setText("Correct Answer is '" + questions.get(position).getAnswer() + "'");
        }
        holder.bt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete")
                .setMessage("Do you want to delete?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(questions.get(position).getQuestion());
                        questions.remove(position);
                        RecycleAdapter.super.notifyDataSetChanged();
                    }
                })
                .show();
            }
        });
        holder.bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionUpdate.class);
                intent.putExtra("questionname",questions.get(position).getQuestion());
                intent.putExtra("position",position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
    public void getSharedInformation(){
        sp = context.getSharedPreferences("LogInInformation",MODE_PRIVATE);
        speditor = sp.edit();
    }
    private void delete(String questionname)
    {
        SQLiteDatabase db=v1.getReadableDatabase();
        db.delete("Question","question"+"=?",new String[]{questionname});
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView question,text_answera,text_answerb,text_answerc,text_answerd,bt_update,bt_remove,tv_correctanswer;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            question = itemView.findViewById(R.id.text_question);
            text_answera = itemView.findViewById(R.id.text_answera);
            text_answerb = itemView.findViewById(R.id.text_answerb);
            text_answerc = itemView.findViewById(R.id.text_answerc);
            text_answerd = itemView.findViewById(R.id.text_answerd);
            tv_correctanswer = itemView.findViewById(R.id.tv_correctanswer);
            bt_update = itemView.findViewById(R.id.bt_update);
            bt_remove = itemView.findViewById(R.id.bt_remove);
        }
    }
}
