package tdc.myruns.server.db;

import java.util.ArrayList;

public class ExerciseEntry {
	
	public static final String ENTRY_ENTITY_NAME = "ExerciseEntry";
	public static final String ENTRY_KEY_NAME = ENTRY_ENTITY_NAME;
	public static final String ENTITY_KIND_ENTRY = ENTRY_ENTITY_NAME;
	
	public static final String FIELD_ID = "id_col";
	public static final String FIELD_IN_TYPE = "in_type";
	public static final String FIELD_ACT_TYPE = "act_type";
	public static final String FIELD_TIME = "timestamp";
	public static final String FIELD_DUR = "durat";
	public static final String FIELD_DIST = "distce";
	public static final String FIELD_PACE = "pace";
	public static final String FIELD_SPEED = "speed";
	public static final String FIELD_CAL = "cals";
	public static final String FIELD_CLIMB = "climb";
	public static final String FIELD_HR = "heartrate";
	public static final String FIELD_COM = "comment";
	public static final String FIELD_LOC_LIST = "locations";
	public static final String FIELD_TIMESTRING = "timestring";

    public final static int MANUAL = 0;
    public final static int AUTOMATIC = 1;
    public final static int GPS = 2;
//    public final static double MPH_PER_MPS = 2.237;
//    public final static double KMPH_PER_MPS = 3.6;
//    public final static double KM_PER_MILE = 1.60934;
//    public final static double M_PER_FT = 0.3048;
//    public final static double M_PER_KM = 1000.0;
//    public final static double FT_PER_MILE = 5000;
//    public final static long MS_PER_S = 1000;

    public long mId;
    public int mInputType;        // Manual, GPS or automatic
    public int mActivityType;     // Running, cycling etc.
    public long mDateTime;    // When does this entry happen
    public int mDuration;         // Exercise duration in seconds
    public double mDistance;      // Distance traveled in meters
    public double mAvgPace;       // Average pace
    public double mAvgSpeed;      // Average speed
    public int mCalorie;          // Calories burnt
    public double mClimb;         // Climb. Either in meters or feet.
    public int mHeartRate;        // Heart rate
    public String mComment;       // Comments
    public ArrayList<Coordinates> mLocationList; // Location list
    public int mHours;
    public int mMinutes;
    public int mSeconds;
    public String mDateString;
    
    public ExerciseEntry() {
    	
    }
    
}
