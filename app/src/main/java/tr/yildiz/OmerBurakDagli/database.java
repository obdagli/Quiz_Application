package tr.yildiz.OmerBurakDagli;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    private static final String androidprogramming = "mobileapp";
    private static final int SURUM = 1;
    public database(Context c){
        super(c, androidprogramming,null,SURUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE User (name Text, surname Text, username Text, email Text, phonenumber Text, birthdate Text,userphoto Blob,password Text, password2 Text,salt Text);");
        db.execSQL("CREATE TABLE Question (question Text, choice1 Text, choice2 Text, choice3 Text, choice4 Text, answer Text, attachment Text,choicesize Text,userfk Text," +
                "FOREIGN KEY(userfk) REFERENCES User(username));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Question");
        onCreate(db);
    }
}
