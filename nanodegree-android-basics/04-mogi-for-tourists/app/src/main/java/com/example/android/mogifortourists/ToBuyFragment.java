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
public class ToBuyFragment extends Fragment {
    private ImageView titleImg;
    public ToBuyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_categories,container,false);

        final ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.to_buy_title_one,R.string.to_buy_text_one,R.string
                .to_buy_when_one,R.string.to_buy_where_one,R.drawable.to_buy_mercadao));

        locations.add(new Location(R.string.to_buy_title_two,R.string.to_buy_text_two,R.string
                .to_buy_when_two,R.string.to_buy_where_two,R.drawable.to_buy_calcadao));

        locations.add(new Location(R.string.to_buy_title_three,R.string.to_buy_text_three,R.string
                .to_buy_when_three,R.string.to_buy_where_three,R.drawable.to_buy_shopping));

        locations.add(new Location(R.string.to_buy_title_four,R.string.to_buy_text_four,R.string
                .to_buy_when_four,R.string.to_buy_where_four,R.drawable.to_buy_mercado_do_produtor));

        locations.add(new Location(R.string.to_buy_title_five,R.string.to_buy_text_five,R.string
                .to_buy_when_five,R.string.to_buy_where_five,R.drawable.to_buy_feira_noturna));

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
