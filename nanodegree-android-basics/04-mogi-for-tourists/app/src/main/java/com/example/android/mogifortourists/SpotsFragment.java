package com.example.android.mogifortourists;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpotsFragment extends Fragment {

    public SpotsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_categories,container,false);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.spot_title_one,R.string.spot_text_one,R.string
                .spot_when_one,R.string.spot_where_one,R.drawable.spots_pico_do_urubu));

        locations.add(new Location(R.string.spot_title_two,R.string.spot_text_two,R.string
                .spot_when_two,R.string.spot_where_two,R.drawable.spots_praca_matriz));

        locations.add(new Location(R.string.spot_title_three,R.string.spot_text_three,R.string
                .spot_when_three,R.string.spot_where_three,R.drawable.spots_praca_carmo));

        locations.add(new Location(R.string.spot_title_four,R.string.spot_text_four,R.string
                .spot_when_four,R.string.spot_where_four,R.drawable.spots_parque_centenario));

        locations.add(new Location(R.string.spot_title_five,R.string.spot_text_five,R.string
                .spot_when_five,R.string.spot_where_five,R.drawable.spots_teatro_municipal));

        locations.add(new Location(R.string.spot_title_six,R.string.spot_text_six,R.string
                .spot_when_six,R.string.spot_where_six,R.drawable.spots_casarao_do_cha));

        locations.add(new Location(R.string.spot_title_seven,R.string.spot_text_seven,R.string
                .spot_when_seven,R.string.spot_where_seven,R.drawable.spots_orquidario));

        locations.add(new Location(R.string.spot_title_eight,R.string.spot_text_eight,R.string
                .spot_when_eight,R.string.spot_where_eight,R.drawable.spots_parque_neblinas));

        locations.add(new Location(R.string.spot_title_nine,R.string.spot_text_nine,R.string
                .spot_when_nine,R.string.spot_where_nine,R.drawable.spots_parque_municipal_francisco));

        locations.add(new Location(R.string.spot_title_ten,R.string.spot_text_ten,R.string
                .spot_when_ten,R.string.spot_where_ten,R.drawable.spots_estadio_nogueirao));

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
