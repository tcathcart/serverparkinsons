package edu.dartmouth.cs.server.db;

import java.util.ArrayList;
import java.util.Calendar;

public class ExerciseItem {
	
	public static final String ENTRY_ENTITY_NAME = "ExerciseEntry";
	public static final String ENTRY_KEY_NAME = ENTRY_ENTITY_NAME;
	public static final String ENTITY_KIND_ENTRY = ENTRY_ENTITY_NAME;
	
    public static final String FIELD_ID = "id_col";
    public static final String FIELD_DATE = "day_of_month";
    public static final String FIELD_MONTH = "month_of_year";
    public static final String FIELD_YEAR = "year_col";
    public static final String FIELD_EXERCISE_TIME = "exercise_time";
    public static final String FIELD_SPEECH_ATTEMPTS = "speech_attempts";
    public static final String FIELD_SPEECH_CORRECT = "speech_correct";

    public long id;
    public Calendar date;
    public long speechDoneCount;
    public long speechCorrectCount;
    public long exerciseTime;
    
    public ExerciseItem() {
    	
    }
    
    public void setDate(int year, int month, int day) {
    	date = Calendar.getInstance();
    	date.set(Calendar.YEAR, year);
    	date.set(Calendar.MONTH, month);
    	date.set(Calendar.DAY_OF_MONTH, day);
    }
    
    public int getDate() {
    	return date.get(Calendar.DAY_OF_MONTH);
    }
    public int getMonth() {
    	return date.get(Calendar.MONTH);
    }
    public int getYear() {
    	return date.get(Calendar.YEAR);
    }
    
}
