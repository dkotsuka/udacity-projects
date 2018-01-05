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
public class RestaurantsFragment extends Fragment {
    private ImageView titleImg;
    public RestaurantsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories,container,false);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.restaurants_title_one,R.string.restaurants_text_one,R.string
                .restaurants_when_one,R.string.restaurants_where_one,R.drawable.restaurants_kidogao));

        locations.add(new Location(R.string.restaurants_title_two,R.string.restaurants_text_two,R.string
                .restaurants_when_two,R.string.restaurants_where_two,R.drawable.restaurants_mn_lamen));

        locations.add(new Location(R.string.restaurants_title_three,R.string.restaurants_text_three,R.string
                .restaurants_when_three,R.string.restaurants_where_three,R.drawable.restaurants_bife_esquisito));

        locations.add(new Location(R.string.restaurants_title_four,R.string.restaurants_text_four,R.string
                .restaurants_when_four,R.string.restaurants_where_four,R.drawable.restaurants_vege));

        locations.add(new Location(R.string.restaurants_title_five,R.string.restaurants_text_five,R.string
                .restaurants_when_five,R.string.restaurants_where_five,R.drawable.restaurants_caipirado));

        locations.add(new Location(R.string.restaurants_title_six,R.string.restaurants_text_six,R.string
                .restaurants_when_six,R.string.restaurants_where_six,R.drawable.restaurants_daruma));

        locations.add(new Location(R.string.restaurants_title_seven,R.string.restaurants_text_seven,R.string
                .restaurants_when_seven,R.string.restaurants_where_seven,R.drawable.restaurants_pastel_caseiro));

        locations.add(new Location(R.string.restaurants_title_eight,R.string.restaurants_text_eight,R.string
                .restaurants_when_eight,R.string.restaurants_where_eight,R.drawable.restaurants_estrela));

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
