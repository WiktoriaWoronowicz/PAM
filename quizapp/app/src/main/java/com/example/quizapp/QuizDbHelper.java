package com.example.quizapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quizapp.QuizContract.*;

import java.util.ArrayList;
public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QuizAppPAM2.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT" +
                ")";
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }
    private void fillQuestionsTable() {
        Question q1 = new Question("Ile nóg ma pająk?",
                "8", "10", "0", 1, Question.CATEGORY_SCIENCE);
        addQuestion(q1);
        Question q2 = new Question("Gdzie możemy obserwować zorzę polarną?",
                "Na biegunie północnym", "Na obu biegunach", "Na biegunie południowym", 2, Question.CATEGORY_SCIENCE);
        addQuestion(q2);
        Question q3 = new Question("Do jakiego kraju należy Gibraltar?",
                "Maroko", "Hiszpania", "Wielka Brytania", 3, Question.CATEGORY_SCIENCE);
        addQuestion(q3);
        Question q4 = new Question("Co je bocian?",
                "Żabki", "Jajka", "Nic nie je", 1, Question.CATEGORY_SCIENCE);
        addQuestion(q4);
        Question q5 = new Question("Z którym państwem graniczy Polska",
                "Nepal", "Białoruś", "Wietnam", 2, Question.CATEGORY_SCIENCE);
        addQuestion(q5);
        Question q6 = new Question("Ile to jest 2+2?",
                "22", "4", "5 he he", 2, Question.CATEGORY_MATH);
        addQuestion(q6);
        Question q7 = new Question("Ile to jest 22*2?",
                "222", "24", "44", 3, Question.CATEGORY_MATH);
        addQuestion(q7);
        Question q8 = new Question("3 __ 4 = 12?",
                "*", "+", "/", 1, Question.CATEGORY_MATH);
        addQuestion(q8);
        Question q9 = new Question("Iloczyn jest wynikiem ...",
                "dodawania", "dzielenia", "mnożenia", 3, Question.CATEGORY_MATH);
        addQuestion(q9);
        Question q10 = new Question("Suma jest wynikiem ...",
                "dodawania", "dzielenia", "mnożenia", 1, Question.CATEGORY_MATH);
        addQuestion(q10);
        Question q11 = new Question("Jaki jest wzór na prędkość v",
                "v=s/t", "v=x*y*z+ab", "s=v*t", 1, Question.CATEGORY_FIZ);
        addQuestion(q11);
        Question q12 = new Question("Kto sformuował trzy zasady dynamiki",
                "Wiktoria Woronowicz", "Einstein", "Newton", 3, Question.CATEGORY_FIZ);
        addQuestion(q12);
    }
    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getCategory());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
    public ArrayList<Question> getQuestions(String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{difficulty};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ?", selectionArgs);
        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCategory(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                questionList.add(question);
            } while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}

