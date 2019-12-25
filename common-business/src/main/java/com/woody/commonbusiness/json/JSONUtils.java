package com.woody.commonbusiness.json;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class JSONUtils {

    public static String getString(String key, JSONObject jsonObject) throws Exception {
        String res = "";
        if (jsonObject.has(key)) {
            if (key == null) {
                return "";
            }
            res = jsonObject.getString(key);
        }
        return res;
    }

    public static HashMap<String, Object> fromJson(JSONObject json) throws JSONException {
        HashMap<String, Object> map = new HashMap<>();
        @SuppressWarnings("unchecked")
        Iterator<String> iKey = json.keys();
        while (iKey.hasNext()) {
            String key = iKey.next();
            Object value = json.opt(key);
            if (JSONObject.NULL.equals(value)) {
                value = null;
            }
            if (value != null) {
                if (value instanceof JSONObject) {
                    value = fromJson((JSONObject) value);
                } else if (value instanceof JSONArray) {
                    value = fromJson((JSONArray) value);
                }
                map.put(key, value);
            }
        }
        return map;
    }

    private static ArrayList<Object> fromJson(JSONArray array) throws JSONException {
        ArrayList<Object> list = new ArrayList<>();
        for (int i = 0, size = array.length(); i < size; i++) {
            Object value = array.opt(i);
            if (value instanceof JSONObject) {
                value = fromJson((JSONObject) value);
            } else if (value instanceof JSONArray) {
                value = fromJson((JSONArray) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * 将指定的HashMap<String, Object>对象转成json数据
     */
    public static <T> String fromList(String key, ArrayList<T> items) {
        StringBuilder builder = new StringBuilder();
        if (null != items && !items.isEmpty()) {
            builder.append("[");
            int size = items.size();
            for (int i = 0; i < size; i++) {
                builder.append("{\"" + key + "\":\"" + items.get(i).toString() + "\"}" + ((i != size - 1) ? "," : ""));
            }
            builder.append("]");
        }
        return builder.toString();
    }

    private static JSONObject getJSONObject(Map<String, Object> map) throws JSONException {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof HashMap<?, ?>) {
                value = getJSONObject((Map<String, Object>) value);
            } else if (value instanceof ArrayList<?>) {
                value = getJSONArray((ArrayList<Object>) value);
            }
            json.put(entry.getKey(), value);
        }
        return json;
    }

    public static String getJsonString(Map map) {
        String result = null;
        try {
            JSONObject jsonObject = getJSONObject(map);
            if (null != jsonObject) {
                result = jsonObject.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private static JSONArray getJSONArray(ArrayList<Object> list) throws JSONException {
        JSONArray array = new JSONArray();
        for (Object value : list) {
            if (value instanceof HashMap<?, ?>) {
                value = getJSONObject((HashMap<String, Object>) value);
            } else if (value instanceof ArrayList<?>) {
                value = getJSONArray((ArrayList<Object>) value);
            }
            array.put(value);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    private static String format(String sepStr, HashMap<String, Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        String mySepStr = sepStr + "\t";
        int i = 0;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(mySepStr).append('\"').append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof HashMap<?, ?>) {
                sb.append(format(mySepStr, (HashMap<String, Object>) value));
            } else if (value instanceof ArrayList<?>) {
                sb.append(format(mySepStr, (ArrayList<Object>) value));
            } else if (value instanceof String) {
                sb.append('\"').append(value).append('\"');
            } else {
                sb.append(value);
            }
            i++;
        }
        sb.append('\n').append(sepStr).append('}');
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private static String format(String sepStr, ArrayList<Object> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        String mySepStr = sepStr + "\t";
        int i = 0;
        for (Object value : list) {
            if (i > 0) {
                sb.append(",\n");
            }
            sb.append(mySepStr);
            if (value instanceof HashMap<?, ?>) {
                sb.append(format(mySepStr, (HashMap<String, Object>) value));
            } else if (value instanceof ArrayList<?>) {
                sb.append(format(mySepStr, (ArrayList<Object>) value));
            } else if (value instanceof String) {
                sb.append('\"').append(value).append('\"');
            } else {
                sb.append(value);
            }
            i++;
        }
        sb.append('\n').append(sepStr).append(']');
        return sb.toString();
    }



    public static JSONObject toJSONObject(String object) {
        try {
            return new JSONObject(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 从字符串中获得map键值
     *
     * @param result
     * @return
     */
    public static Map<String, String> fromJsonToMap(String result) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject json = new JSONObject(result);
                Iterator<String> iKey = json.keys();
                while (iKey.hasNext()) {
                    String key = iKey.next();
                    Object value = json.opt(key);
                    if (JSONObject.NULL.equals(value)) {
                        value = null;
                    }
                    if (null != value && !TextUtils.isEmpty(value.toString())) {
                        params.put(key, value.toString());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return params;
    }

}
