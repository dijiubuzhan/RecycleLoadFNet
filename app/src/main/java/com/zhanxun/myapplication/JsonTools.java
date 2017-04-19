package com.zhanxun.myapplication;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author liu
 * 
 *         2014-3-10
 */
public class JsonTools {
	private static final String TAG ="FirstDemo" ;
	private static Gson gson;

	private JsonTools() {
	}

	private static Gson getGson() {
		if (gson == null) {
			gson = new Gson();
		}
		return gson;
	}


	public static <T> T jsonObj(String jsonData, Class<T> cls) {
		if (TextUtils.isEmpty(jsonData)) {
			return null;
		}

		T t = null;
		try {
			t = getGson().fromJson(jsonData, cls);
		} catch (JsonSyntaxException e) {
			Log.d(TAG, "jsonObj,JsonSyntaxException e=" + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (JsonParseException e) {
			Log.d(TAG, "jsonObj,JsonParseException e=" + e.getMessage());
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			Log.d(TAG, "jsonObj,Exception e=" + e.getMessage());
			e.printStackTrace();
			return null;
		}

		return t;
	}


	private static <T> List<T> jsonObjArray(String jsonArray, Class<T> clazz) {
		if (TextUtils.isEmpty(jsonArray)) {
			return null;
		}

		List<T> lists = new ArrayList<T>();

		try {
			JsonParser parser = new JsonParser();
			JsonArray array = parser.parse(jsonArray).getAsJsonArray();
			for (JsonElement obj : array) {
				T t = getGson().fromJson(obj, clazz);
				lists.add(t);
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		}

		return lists;
	}


	public static <T> List<T> jsonObjArray(JSONArray jsonArray, Class<T> clazz) {
		return jsonObjArray(jsonArray.toString(), clazz);

	}

	/**
	 * 保存json的List对象
	 * 
	 * @param lists
	 */
	public static <T> String listToJson(List<T> lists) {
		return getGson().toJson(lists);
	}

	public static <T> String toJson(T object) {
		return getGson().toJson(object);
	}

}
