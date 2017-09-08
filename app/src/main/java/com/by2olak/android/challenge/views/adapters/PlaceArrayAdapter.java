package com.by2olak.android.challenge.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.by2olak.android.challenge.R;
import com.by2olak.android.challenge.models.RemoteDataSource.PlaceApi;
import com.by2olak.android.challenge.models.data.Place;
import com.by2olak.android.challenge.views.IMainActivityView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

/**
 * Created by Hager.Magdy on 9/7/2017.
 */

public class PlaceArrayAdapter extends RecyclerView.Adapter<PlaceArrayAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "PlaceArrayAdapter";
    private ArrayList<Place> mFilteredList;
    private GoogleApiClient mGoogleApiClient;
    private AutocompleteFilter mPlaceFilter;
    Context mContext;
    private ArrayList<Place> mArrayList;
    private static final int LIST_ITEM = 0;
    private static final int HEADER_ITEM = 1;
    IMainActivityView MainView;

    public PlaceArrayAdapter(LatLngBounds bounds, ArrayList<Place> arrayList, IMainActivityView MainView, Context mContext, GoogleApiClient mGoogleApiClient) {
        mArrayList = arrayList;
        mFilteredList = arrayList;
        this.MainView = MainView;
        this.mContext = mContext;
        this.mGoogleApiClient = mGoogleApiClient;
    }



    @Override
    public Filter getFilter() {
        Log.e("filter is called", "here");
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    // mFilteredList = getAutocomplete(constraint);
                    PlaceApi mApi = new PlaceApi(mContext, mGoogleApiClient);

                    mFilteredList = mApi.getAutocomplete(constraint, mPlaceFilter);
                    if (mFilteredList != null) {
                        // The API successfully returned results.
                        results.values = mFilteredList;
                        results.count = mFilteredList.size();
                    }
//                    Log.e("RESULT",mFilteredList.get(0).getName()+"ddd");
                } else {
                    mFilteredList = mArrayList;
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mFilteredList = (ArrayList<Place>) results.values;
                    // The API returned at least one result, update the data.
                    MainView.updateGooglekList(mFilteredList);

                    notifyDataSetChanged();
                } else {

                    // The API did not return any results, invalidate the data set.
                    //notifyDataSetInvalidated();

                }
            }
        };
        return filter;
    }


    @Override
    public PlaceArrayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView;
        if (viewType == LIST_ITEM) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_row, parent, false);
        } else {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);


        }

        ViewHolder mPredictionHolder = new ViewHolder(convertView);

        return mPredictionHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_name.setText(mFilteredList.get(position).getName());
        holder.tv_address.setText(mFilteredList.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        if (mFilteredList != null)
            return mFilteredList.size();
        else
            return 0;
    }


    @Override
    public int getItemViewType(int position) {
        Log.e("GET ITEM TYPE", "FOUND");
        if (mFilteredList.get(position).getName().equalsIgnoreCase("saved")) {
            return HEADER_ITEM;
        } else
            return LIST_ITEM;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tv_name;
        private TextView tv_address;

        ViewHolder(View itemView) {

            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);

        }


    }

}
