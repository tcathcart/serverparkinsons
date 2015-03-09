package edu.dartmouth.cs.server;

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

import edu.dartmouth.cs.server.db.EntryDatastore;
import edu.dartmouth.cs.server.db.ExerciseItem;
import edu.dartmouth.cs.server.db.RegDatastore;
import edu.dartmouth.cs.server.gcm.Sender;

public class PostDataServlet extends HttpServlet {
	
	private static final String DEVICE_REG_ID_PROPERTY = "regId";
    public static final String FIELD_ID = "id_col";
    public static final String FIELD_DATE = "day_of_month";
    public static final String FIELD_MONTH = "month_of_year";
    public static final String FIELD_YEAR = "year_col";
    public static final String FIELD_EXERCISE_TIME = "exercise_time";
    public static final String FIELD_SPEECH_ATTEMPTS = "speech_attempts";
    public static final String FIELD_SPEECH_CORRECT = "speech_correct";
	
	
	protected static final Logger logger = Logger.getLogger(PostDataServlet.class
			.getName());
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
//		TODO: parent for all entries is particular device
		
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
				ExerciseItem entry = new ExerciseItem();
				entry.id = entryJson.getLong(FIELD_ID);
				entry.setDate(
					entryJson.getInt(FIELD_YEAR),
					entryJson.getInt(FIELD_MONTH),
					entryJson.getInt(FIELD_DATE)
				);
				entry.speechDoneCount = entryJson.getInt(FIELD_SPEECH_ATTEMPTS);
				entry.speechCorrectCount = entryJson.getInt(FIELD_SPEECH_CORRECT);
				entry.exerciseTime = entryJson.getLong(FIELD_EXERCISE_TIME);
				
				// add entry
				EntryDatastore.add(entry, regId);	
			}
			// send response
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("text/plain");
			String message = "finished processing";
			
			resp.setContentLength(message.length());
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
