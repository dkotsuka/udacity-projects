package com.example.android.mogifortourists;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FestivalsFragment extends Fragment {
    private ImageView titleImg;
    public FestivalsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories,container,false);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.festivals_title_one,R.string.festivals_text_one,R.string
                .festivals_when_one,R.string.festivals_where_one,R.drawable.festivals_furosatomatsuri));

        locations.add(new Location(R.string.festivals_title_two,R.string.festivals_text_two,R.string
                .festivals_when_two,R.string.festivals_where_two,R.drawable.festivals_akimatsuri));

        locations.add(new Location(R.string.festivals_title_three,R.string.festivals_text_three,R.string
                .festivals_when_three,R.string.festivals_where_three,R.drawable.festivals_divino));

        ItemAdapter adapter = new ItemAdapter(getActivity(), locations);
        ListView listView = (ListView) rootView.findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), DetailsActivity.class);
                intent.putExtra("TITLE", getString(locations.get(i).getTitleStringId()));
                intent.putExtra("TEXT", getString(locations.get(i).getTextStringId()));
                intent.putExtra("WHEN", getString(locations.get(i).getTimeStringId()));
                intent.putExtra("WHERE", getString(locations.get(i).getAdressStringId()));
                intent.putExtra("IMAGE_ID",locations.get(i).getImageResourceId());
                startActivity(intent);
            }
        });

        return rootView;
    }

}
