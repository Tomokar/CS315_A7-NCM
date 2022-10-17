package com.example.cs315_a7_ncm;

import android.content.ClipData;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.cs315_a7_ncm.databinding.FragmentItemDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private ModelBoy mItem;
    private CollapsingToolbarLayout mToolbarLayout;
    private Toolbar mToolbar;
    private TextView mTextView;

    private final View.OnDragListener dragListener = (v, event) ->
    {
        if (event.getAction() == DragEvent.ACTION_DROP)
        {
            ClipData.Item clipDataItem = event.getClipData().getItemAt(0);
            mItem = ModelsJunk.ITEM_MAP.get(clipDataItem.getText().toString());
            updateContent();
        }
        return true;
    };
    private FragmentItemDetailBinding binding;

    private FloatingActionButton testingFab;

    Resources appRes = App.getContext().getResources();

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


//        ((ItemDetailHostActivity) requireActivity()).setActionBarTitle(mItem.gameCompanyName);

        assert getArguments() != null;
        if (getArguments().containsKey(ARG_ITEM_ID))
        {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = ModelsJunk.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mToolbar = binding.detailToolbar;
        mToolbarLayout = rootView.findViewById(R.id.toolbar_layout);
        mTextView = binding.itemDetail;

        mTextView.setTextAppearance(R.style.ncm_Theme);

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
//            getActivity().getActionBar().setTitle(mItem.gameCompanyName);

//            mTextView.setText(mItem.wordsDefinition + "\n\n" + "Etymology" + "\n - \n" + mItem.wordsEtymology);
            mTextView.setText(mItem.wordsDefinition);
//            mToolbar.setTitle(getString(R.string.dev_name));


            if (mToolbarLayout != null)
            {
                mToolbarLayout.setCollapsedTitleTextAppearance(R.style.ncm_Theme);
                mToolbarLayout.setExpandedTitleTextAppearance(R.style.ncm_Theme);
                mToolbarLayout.setTitle(mItem.wordsWord);
            }
        }

        if (testingFab != null)
        {
            testingFab.setOnClickListener(view ->
            {
//                testAllThatJazz();
//                jsonParse();
            });
        }
    }

    private void testAllThatJazz()
    {
        String url = getString(R.string.tanner_url);  // THAT should be in a strings.xml file!
//        url = "https://api.jsonbin.io/v3/b/6345a6940e6a79321e2506ae"; //my url

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Display the response string in our convenient existing text view
                    response = appRes.getString(R.string.tanner_response) + response;
                    mTextView.setText(response);
                    // NEXT, we need to use GSON to turn that JSON into a model
                },
                error -> {
                    // you should drop a breakpoint RIGHT HERE if you need to see the error coming back
                    mTextView.setText(R.string.tanner_error);
                });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}