package tdc.myruns.server.db;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

public class EntryDatastore {
	private static final DatastoreService mDatastore = DatastoreServiceFactory
			.getDatastoreService();

	
	// TODO: make this method take regId parameter
	private static Key getParentKey() {
		return KeyFactory.createKey(ExerciseEntry.ENTITY_KIND_ENTRY,
				ExerciseEntry.ENTRY_ENTITY_NAME);
	}

	private static void createParentEntity() {
		Entity entity = new Entity(getParentKey());

		mDatastore.put(entity);
	}

	public static boolean add(ExerciseEntry entry, String regId) {
		Key parentKey = getParentKey();
		try {
			mDatastore.get(parentKey);
		} catch (Exception ex) {
			createParentEntity();
		}

		Entity entity = new Entity(ExerciseEntry.ENTITY_KIND_ENTRY,
				entry.mId, parentKey);
		entity.setProperty(ExerciseEntry.FIELD_ID, entry.mId);
		entity.setProperty(ExerciseEntry.FIELD_ACT_TYPE, entry.mActivityType);
		entity.setProperty(ExerciseEntry.FIELD_CAL, entry.mCalorie);
		entity.setProperty(ExerciseEntry.FIELD_CLIMB, entry.mClimb);
		entity.setProperty(ExerciseEntry.FIELD_COM, entry.mComment);
		entity.setProperty(ExerciseEntry.FIELD_DIST, entry.mDistance);
		entity.setProperty(ExerciseEntry.FIELD_DUR, entry.mDuration);
		entity.setProperty(ExerciseEntry.FIELD_HR, entry.mHeartRate);
		entity.setProperty(ExerciseEntry.FIELD_IN_TYPE, entry.mInputType);
//		entity.setProperty(ExerciseEntry.FIELD_LOC_LIST, entry.mLocationList);
		entity.setProperty(ExerciseEntry.FIELD_PACE, entry.mAvgPace);
		entity.setProperty(ExerciseEntry.FIELD_SPEED, entry.mAvgSpeed);
		entity.setProperty(ExerciseEntry.FIELD_TIME, entry.mDateTime);
		entity.setProperty(ExerciseEntry.FIELD_TIMESTRING, entry.mDateString);

		mDatastore.put(entity);

		return true;
	}

	public static void remove(long id) {
		Key parentKey = getParentKey();
		try {
			mDatastore.get(parentKey);
		} catch (Exception ex) {
			createParentEntity();
		}
		Key key = KeyFactory.createKey(parentKey, ExerciseEntry.ENTITY_KIND_ENTRY, id);
		mDatastore.delete(key);
	}
	
	public static void removeAllEntries() {
		ArrayList<ExerciseEntry> entryList = query();
		for (ExerciseEntry entry : entryList) {
			remove(entry.mId);
		}
	}
	
	public static ArrayList<ExerciseEntry> query() {
		ArrayList<ExerciseEntry> resultList = new ArrayList<ExerciseEntry>();

		Query query = new Query(ExerciseEntry.ENTITY_KIND_ENTRY);
		query.setFilter(null);
		query.setAncestor(getParentKey());
		query.addSort(ExerciseEntry.FIELD_TIME, SortDirection.ASCENDING);
		PreparedQuery pq = mDatastore.prepare(query);

		for (Entity entity : pq.asIterable()) {
			ExerciseEntry entry = new ExerciseEntry();
			entry.mId = (Long) entity.getProperty(ExerciseEntry.FIELD_ID);
			entry.mActivityType = ((Long) entity.getProperty(ExerciseEntry.FIELD_ACT_TYPE)).intValue();
			entry.mCalorie = ((Long) entity.getProperty(ExerciseEntry.FIELD_CAL)).intValue();
			entry.mClimb = (double) entity.getProperty(ExerciseEntry.FIELD_CLIMB);
			entry.mComment = (String) entity.getProperty(ExerciseEntry.FIELD_COM);
			entry.mDistance = (double) entity.getProperty(ExerciseEntry.FIELD_DIST);
			entry.mDuration = ((Long) entity.getProperty(ExerciseEntry.FIELD_DUR)).intValue();
			entry.mHeartRate = ((Long) entity.getProperty(ExerciseEntry.FIELD_HR)).intValue();
			entry.mInputType = ((Long) entity.getProperty(ExerciseEntry.FIELD_IN_TYPE)).intValue();
//			entry.mLocationList = (ArrayList<Coordinates>) entity.getProperty(ExerciseEntry.FIELD_LOC_LIST);
			entry.mAvgPace = (double) entity.getProperty(ExerciseEntry.FIELD_PACE);
			entry.mAvgSpeed = (double) entity.getProperty(ExerciseEntry.FIELD_SPEED);
			entry.mDateTime = (Long) entity.getProperty(ExerciseEntry.FIELD_TIME);
			entry.mDateString = (String) entity.getProperty(ExerciseEntry.FIELD_TIMESTRING);
			resultList.add(entry);
		}
		return resultList;
	}

}
