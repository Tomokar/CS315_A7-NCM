package com.example.cs315_a7_ncm;

import android.app.Activity;
import android.content.res.Resources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelsJunk
{
    public static final Map<String, ModelBoy> ITEM_MAP = new HashMap<>();
    public static final List<ModelBoy> ITEMS = new ArrayList<>();

    Resources appRes = App.getContext().getResources();

    public void JSONParse(Activity activity)
    {
        RequestQueue mQueue = Volley.newRequestQueue(activity);

        String url = appRes.getString(R.string.my_url);

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try
                    {
                        JSONObject object = response.getJSONObject("record");
                        JSONArray jsonArray = object.getJSONArray("Words");

                        ITEMS.clear();
                        ITEM_MAP.clear();

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject wordsBundle = jsonArray.getJSONObject(i);

                            String wordsWord = wordsBundle.getString("word");
                            String wordsType = wordsBundle.getString("type");
                            String wordsDefinition = wordsBundle.getString("definition") + "\n\n" + "Etymology" + "\n - \n" + wordsBundle.getString("etymology");
//                            String wordsEtymology = wordsBundle.getString("etymology");

                            ModelBoy modelB = new ModelBoy(wordsWord, wordsType, wordsDefinition/*, wordsEtymology*/);
                            ITEMS.add(modelB);
                            ITEM_MAP.put(wordsWord, modelB);
                        }

//                        activity.recreate();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        mQueue.add(mRequest);
    }
}
