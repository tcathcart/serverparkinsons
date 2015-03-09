package edu.dartmouth.cs.server.db;
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
		return KeyFactory.createKey(ExerciseItem.ENTITY_KIND_ENTRY,
				ExerciseItem.ENTRY_ENTITY_NAME);
	}

	private static void createParentEntity() {
		Entity entity = new Entity(getParentKey());

		mDatastore.put(entity);
	}

	public static boolean add(ExerciseItem entry, String regId) {
		Key parentKey = getParentKey();
		try {
			mDatastore.get(parentKey);
		} catch (Exception ex) {
			createParentEntity();
		}

		Entity entity = new Entity(ExerciseItem.ENTITY_KIND_ENTRY,
				entry.id, parentKey);
		entity.setProperty(ExerciseItem.FIELD_ID, entry.id);
		entity.setProperty(ExerciseItem.FIELD_DATE, entry.getDate());
		entity.setProperty(ExerciseItem.FIELD_MONTH, entry.getMonth());
		entity.setProperty(ExerciseItem.FIELD_YEAR, entry.getYear());
		entity.setProperty(ExerciseItem.FIELD_EXERCISE_TIME, entry.exerciseTime);
		entity.setProperty(ExerciseItem.FIELD_SPEECH_ATTEMPTS, entry.speechDoneCount);
		entity.setProperty(ExerciseItem.FIELD_SPEECH_CORRECT, entry.speechCorrectCount);
		
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
		Key key = KeyFactory.createKey(parentKey, ExerciseItem.ENTITY_KIND_ENTRY, id);
		mDatastore.delete(key);
	}
	
	public static void removeAllEntries() {
		ArrayList<ExerciseItem> entryList = query();
		for (ExerciseItem entry : entryList) {
			remove(entry.id);
		}
	}
	
	public static ArrayList<ExerciseItem> query() {
		ArrayList<ExerciseItem> resultList = new ArrayList<ExerciseItem>();

		Query query = new Query(ExerciseItem.ENTITY_KIND_ENTRY);
		query.setFilter(null);
		query.setAncestor(getParentKey());
		query.addSort(ExerciseItem.FIELD_ID, SortDirection.DESCENDING);
		PreparedQuery pq = mDatastore.prepare(query);

		for (Entity entity : pq.asIterable()) {
			ExerciseItem entry = new ExerciseItem();
			
			entry.id = (Long) entity.getProperty(ExerciseItem.FIELD_ID);
			entry.setDate(
				((Long) entity.getProperty(ExerciseItem.FIELD_YEAR)).intValue(),
				((Long) entity.getProperty(ExerciseItem.FIELD_MONTH)).intValue(),
				((Long) entity.getProperty(ExerciseItem.FIELD_DATE)).intValue()
			);
			entry.speechDoneCount = ((Long) entity.getProperty(ExerciseItem.FIELD_SPEECH_ATTEMPTS));
			entry.speechCorrectCount = ((Long) entity.getProperty(ExerciseItem.FIELD_SPEECH_CORRECT));
			entry.exerciseTime = (Long) entity.getProperty(ExerciseItem.FIELD_EXERCISE_TIME);
			resultList.add(entry);
		}
		return resultList;
	}

}
