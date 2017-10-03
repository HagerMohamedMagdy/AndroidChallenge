package com.by2olak.android.challenge.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.by2olak.android.challenge.R;
import com.by2olak.android.challenge.dagger.PresenterModule;
import com.by2olak.android.challenge.models.data.MyApplication;
import com.by2olak.android.challenge.models.utility.Network;
import com.by2olak.android.challenge.views.adapters.PlaceArrayAdapter;
import com.by2olak.android.challenge.models.RemoteDataSource.PlaceApi;
import com.by2olak.android.challenge.models.RemoteDataSource.ServerConfig;
import com.by2olak.android.challenge.models.data.Place;
import com.by2olak.android.challenge.presenters.IMainActivityPresenter;
import com.by2olak.android.challenge.presenters.MainActivityPresenterImp;
import com.by2olak.android.challenge.views.IMainActivityView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainActivityView, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {
    PlaceApi mPlaceApi;
    Context mcontext;
    @BindView(R.id.card_recycler_view)

    RecyclerView mRecyclerView;
    private MenuItem mSearchMenuItem;
    @BindView(R.id.activity_progressBar)
    ProgressBar progressBar;
    ArrayList<Place> mTotalList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    private PlaceArrayAdapter mPlaceArrayAdapter;
    private GoogleApiClient mGoogleApiClient;
    @Inject
    IMainActivityPresenter presenterModule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;

        ((MyApplication)getApplication()).getAppComponent().inject(this);
        ButterKnife.bind(this);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, 0, this)
                .addConnectionCallbacks(this)
                .build();
        initViews();
        mPlaceApi = new PlaceApi(this, mGoogleApiClient);

        presenterModule.setView(this);
        if (Network.isNetworkOnline(this)) {
            presenterModule.searchBy2olakApi("0", "25");
        } else {
            Toast.makeText(this, getResources().getString(R.string.network_err),
                    Toast.LENGTH_LONG).show();
        }

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mPlaceArrayAdapter);

    }

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView.setHasFixedSize(true);
        mLinearLayoutManager = new LinearLayoutManager(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        mSearchMenuItem = menu.findItem(R.id.action_search);

        mSearchMenuItem.setEnabled(true);

        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setIconified(true);
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        searchView.setFocusable(true);
        search(searchView);
        return true;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                Log.e("Search", "is Called");
                Log.e("Search", "is Called");
                if (newText == null || newText.equals("")) {
                    if (Network.isNetworkOnline(mcontext)) {
                        hideLoading();
                        presenterModule.searchBy2olakApi("0", "25");
                    } else {
                        Toast.makeText(mcontext, getResources().getString(R.string.network_err),
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (mPlaceArrayAdapter != null) mPlaceArrayAdapter.getFilter().filter(newText);
                }

                return true;
            }
        });
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateBy2olakList(ArrayList<Place> mPlaceList) {
        Log.e("UpdateBy2olak", "found");
        Log.e("mPlaceList", mPlaceList.size() + "ss");
        Place mHeader = new Place();
        mHeader.setLang("");
        mHeader.setLat("");
        mHeader.setName("Saved");
        mHeader.setAddress(" ");
        mPlaceList.add(0, mHeader);
        Log.e("mPlaceList", mPlaceList.size() + "");
        mTotalList = mPlaceList;
        mPlaceArrayAdapter = new PlaceArrayAdapter(
                ServerConfig.BOUNDS_MOUNTAIN_VIEW, mTotalList, this, this, mGoogleApiClient);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mPlaceArrayAdapter);
        mPlaceArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateGooglekList(ArrayList<Place> mPlaceList) {
        Log.e("mfilteredlist", mPlaceList.size() + "");
        mTotalList.addAll(0, mPlaceList);
        Log.e("mNewList", mTotalList.size() + "");
        mPlaceArrayAdapter = new PlaceArrayAdapter(
                ServerConfig.BOUNDS_MOUNTAIN_VIEW, mTotalList, this, this, mGoogleApiClient);
        // mPlaceAdapter= new PlaceAdapter(mPlaceList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mPlaceArrayAdapter);
        mPlaceArrayAdapter.notifyDataSetChanged();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mPlaceApi.setGoogleApiClient(mGoogleApiClient);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceApi.setGoogleApiClient(null);
        Log.e("LOG_TAG", "Google Places API connection suspended.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("LOG_TAG", "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(this,
                "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mGoogleApiClient.isConnected() && !mGoogleApiClient.isConnecting()) {
            Log.v("Google API", "Connecting");
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            Log.v("Google API", "Dis-Connecting");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
