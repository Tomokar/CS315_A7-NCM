package com.example.cs315_a7_ncm;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.cs315_a7_ncm.placeholder.PlaceholderContent;
import com.example.cs315_a7_ncm.databinding.FragmentItemDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class ItemDetailFragment extends Fragment
{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private PlaceholderContent.PlaceholderItem mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private TextView mTextView;

    private final View.OnDragListener dragListener = (v, event) ->
    {
        if (event.getAction() == DragEvent.ACTION_DROP)
        {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = PlaceholderContent.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    private FloatingActionButton testingFab;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;
        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = PlaceholderContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.itemDetail;

        testingFab = rootView.findViewById(R.id.fab);

        // Show the placeholder content as text in a TextView & in the toolbar if available.
        updateContent();
        rootView.setOnDragListener(dragListener);
        return rootView;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent()
    {
        if (mItem != null)
        {
            mTextView.setText(mItem.details);
            if (mToolbarLayout != null)
            {
                mToolbarLayout.setTitle(mItem.content);
            }
        }

        if (testingFab != null)
        {
            testingFab.setOnClickListener(view ->
            {
                testAllThatJazz();
//                    jsonParse();
            });
        }
    }

    private void testAllThatJazz()
    {
        String url = getString(R.string.tanner_url);  // THAT should be in a strings.xml file!

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // Display the response string in our convenient existing text view
                        response = R.string.tanner_response + response;
                        mTextView.setText(response);
                        // NEXT, we need to use GSON to turn that JSON into a model
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // you should drop a breakpoint RIGHT HERE if you need to see the error coming back
                        mTextView.setText(R.string.tanner_error);
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void jsonParse()
    {
        String url = getString(R.string.my_url);

        RequestQueue mQueue = Volley.newRequestQueue(requireActivity());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Words");

                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject wordEntry = jsonArray.getJSONObject(i);

                                String word = wordEntry.getString("word");
                                String type = wordEntry.getString("type");
                                String definition = wordEntry.getString("definition");
                                String etymology = wordEntry.getString("etymology");

                                mTextView.append(word + ", " + type + ", " + definition + ", " + etymology + "\n\n");
                            }
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
        mQueue.add(request);
    }
}