package com.example.cs315_a7_ncm;

import android.app.Activity;
import android.content.res.Resources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

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

        String url = appRes.getString(R.string.tanner_url);

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, null,
            new Response.Listener<JSONObject>()
            {
                @Override
                public void onResponse(JSONObject response)
                {
                    try
                    {
                        JSONObject object = response.getJSONObject("record");
                        JSONArray jsonArray = object.getJSONArray("gameCompanies");

                        ITEMS.clear();
                        ITEM_MAP.clear();

                        for (int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject gameCompany = jsonArray.getJSONObject(i);
                            String json = String.valueOf(gameCompany);

//                            String companyName = gameCompany.getString("name");
//                            String companyYear = String.valueOf(gameCompany.getInt("year"));
//                            String companyConsole = gameCompany.getString("recentConsole");

//                            ModelBoy modelB = new ModelBoy(companyName, companyYear, companyConsole);
                            Gson gson = new Gson();

                            ModelBoy modelB = gson.fromJson(json, ModelBoy.class);

                            ITEMS.add(modelB);
                            ITEM_MAP.put(modelB.gameCompanyName, modelB);
//                            ITEM_MAP.put(companyName, modelB);
                        }

//                        activity.recreate();
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    error.printStackTrace();
                }
            });

        mQueue.add(mRequest);
    }
}
