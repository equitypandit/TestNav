package com.test.testnav.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.test.testnav.R;
import com.test.testnav.activity.AskAnalystActivity;
import com.test.testnav.activity.FreeTrialActivity;
import com.test.testnav.activity.PricingActivity;
import com.test.testnav.activity.TechnicalAnalysisActivity;
import com.test.testnav.other.CustomVolleyRequest;
import com.test.testnav.other.ImageAdapter;
import com.test.testnav.other.SliderUtils;
import com.test.testnav.other.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_HOME = "home";

    String request_url = "http://equitypandit.com/portal/app2_api/load_app_data.php?req_data=slides";
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private NavigationView navigationView;
    private ConnectivityManager cm;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("EquityPandit");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity()));

        rq = CustomVolleyRequest.getInstance(getContext()).getRequestQueue();

        sliderImg = new ArrayList<>();

        viewPager = (ViewPager) view.findViewById(R.id.pager);

        sliderDotspanel = (LinearLayout) view.findViewById(R.id.SliderDots);

        sendRequest();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle_inactive));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
//                Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
                cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected()) {

                    switch (position) {
                        case 0:
                            // home
                            MarketPredictionFragment marketPredictionFragment = new MarketPredictionFragment();
                            FragmentTransaction ftmp = getFragmentManager().beginTransaction();
                            ftmp.replace(R.id.frame, marketPredictionFragment);
                            ftmp.addToBackStack(null);
                            ftmp.commit();
                            break;
                        case 1:
                            // technical analysis
                            Intent tech_ana = new Intent(getActivity(), TechnicalAnalysisActivity.class);
                            startActivity(tech_ana);
                            break;
                        case 2:
                            LatestnewsFragment latestnewsFragment = new LatestnewsFragment();
                            FragmentTransaction ftln = getFragmentManager().beginTransaction();
                            ftln.replace(R.id.frame, latestnewsFragment);
                            ftln.addToBackStack(null);
                            ftln.commit();
                            break;
                        case 3:
                            // pricing
                            Intent pricingintent = new Intent(getActivity(), PricingActivity.class);
                            startActivity(pricingintent);
                            break;
                        case 4:
                            // free trial
                            Intent ftintent = new Intent(getActivity(), FreeTrialActivity.class);
                            startActivity(ftintent);
                            break;
                        case 9:
                            // ask the analyst
                            Intent ata = new Intent(getActivity(), AskAnalystActivity.class);
                            startActivity(ata);
                            break;
                        default:
                            HomeFragment homeFragment = new HomeFragment();
                            FragmentTransaction fth = getFragmentManager().beginTransaction();
                            fth.replace(R.id.frame, homeFragment);
                            fth.addToBackStack(null);
                            fth.commit();
                            break;
                    }

                } else {
//                    // check your internet connection
                    Toast.makeText(getActivity(), "please check internet connection!", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void sendRequest() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, request_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    SliderUtils sliderUtils = new SliderUtils();
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        sliderUtils.setSliderImageUrl(jsonObject.getString("slides"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sliderImg.add(sliderUtils);
                }

                viewPagerAdapter = new ViewPagerAdapter(sliderImg, getContext());
                viewPager.setAdapter(viewPagerAdapter);

                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {
                    dots[i] = new ImageView(getContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
                    params.setMargins(8, 0, 8, 0);
                    sliderDotspanel.addView(dots[i], params);
                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.bg_circle_inactive));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        CustomVolleyRequest.getInstance(getContext()).addToRequestQueue(jsonArrayRequest);

    }


}
