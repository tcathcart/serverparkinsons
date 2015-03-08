package tdc.myruns.server;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

import tdc.myruns.server.db.EntryDatastore;
import tdc.myruns.server.db.ExerciseEntry;
import tdc.myruns.server.db.Coordinates;
import tdc.myruns.server.db.RegDatastore;
import tdc.myruns.server.gcm.Sender;

public class PostDataServlet extends HttpServlet {
	
	private static final String DEVICE_REG_ID_PROPERTY = "regId";
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
	public static final String LAT = "latitude";
	public static final String LONG = "longitude";
	protected static final Logger logger = Logger.getLogger(PostDataServlet.class
			.getName());
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
//		TODO: parent for all entries is particular device
//		TODO: remove all entries before re-uploading
		
		
		try {
			EntryDatastore.removeAllEntries();
			String regId = req.getParameter(DEVICE_REG_ID_PROPERTY);
			JSONArray root = new JSONArray(req.getParameter("entry_list"));
			logger.info("Received Post request");
			int len = root.length();
			logger.info("Received " + len + " entries");
			for (int i=0; i < len; i++) {
				logger.info("Grabbing entry number " + i);
				JSONObject entryJson = root.getJSONObject(i);
				ExerciseEntry entry = new ExerciseEntry();
				entry.mInputType = entryJson.getInt(FIELD_IN_TYPE);
				entry.mActivityType = (int) entryJson.getInt(FIELD_ACT_TYPE);
				entry.mDateTime = entryJson.getLong(FIELD_TIME);
				entry.mDuration = entryJson.getInt(FIELD_DUR);
				entry.mDistance = entryJson.getDouble(FIELD_DIST);
				entry.mAvgPace = entryJson.getDouble(FIELD_PACE);
				entry.mAvgSpeed = entryJson.getDouble(FIELD_SPEED);
				entry.mCalorie = entryJson.getInt(FIELD_CAL);
				entry.mClimb = entryJson.getDouble(FIELD_CLIMB);
				entry.mHeartRate = entryJson.getInt(FIELD_HR);
				entry.mComment = entryJson.getString(FIELD_COM);
				entry.mId = entryJson.getInt(FIELD_ID);
				Date date = new Date(entry.mDateTime);
				DateFormat formatter = new SimpleDateFormat("EEE, MMM d 'at' h:mm a");
				String dateFormatted = formatter.format(date);
				entry.mDateString = dateFormatted;
				
				// get list of lat/longs
				JSONArray locationListJson = entryJson.getJSONArray(FIELD_LOC_LIST);
				ArrayList<Coordinates> locationList = new ArrayList<>();
				int numLocs;
				if (locationListJson != null)
					numLocs = locationListJson.length();
				else
					numLocs = -1;
				
				// unpack list
				for (int j=0; j < numLocs; j++) {
					logger.info("Grabbing location number " + j);
					JSONObject coords = locationListJson.getJSONObject(j);
					double latit = coords.getDouble(LAT);
					double longit = coords.getDouble(LONG);
					locationList.add(new Coordinates(latit, longit));
				}

				// assign reference
				entry.mLocationList = locationList;
				
				// add entry
				EntryDatastore.add(entry, regId);
				
				// send response
				resp.setStatus(HttpServletResponse.SC_OK);
				resp.setContentType("text/plain");
				String message = "finished processing";
				
				resp.setContentLength(message.length());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

//		// notify
//		String from = req.getParameter("from");
//		if (from == null || !from.equals("phone")) {
//			resp.sendRedirect("/sendmsg.do");
//		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		doPost(req, resp);
	}
}
