package com.framgia.music_44.screen.genres;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music_44.R;
import com.framgia.music_44.data.model.Genre;
import com.framgia.music_44.screen.genres.adapter.GenresAdapter;
import com.framgia.music_44.screen.home.HomeFragment;
import com.framgia.music_44.util.Constant;
import com.framgia.music_44.util.Genres;
import com.framgia.music_44.util.GenresId;
import com.framgia.music_44.util.Navigator;
import com.framgia.music_44.util.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class GenresFragment extends Fragment implements OnItemClickListener {
    private Navigator mNavigator;

    public static GenresFragment newInstance() {
        return new GenresFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genres, container, false);
        GenresAdapter genresAdapter = new GenresAdapter(getContext(), getList(), this);
        RecyclerView recyclerView = view.findViewById(R.id.recylerViewGenres);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(genresAdapter);
        mNavigator = new Navigator();
        return view;
    }

    private List<Genre> getList() {
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(Genres.ALL_MUSIC));
        genreList.add(new Genre(Genres.ALL_AUDIO));
        genreList.add(new Genre(Genres.ALTERNATIVE_ROCK));
        genreList.add(new Genre(Genres.AMBIENT));
        genreList.add(new Genre(Genres.CLASSICAL));
        genreList.add(new Genre(Genres.COUNTRY));
        return genreList;
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case GenresId.ALL_MUSIC_INT:
                mNavigator.addFragment(HomeFragment.newInstance(Constant.REMOTE, Genres.ALL_MUSIC),
                        getFragmentManager());
                break;
            case GenresId.ALL_AUDIO_INT:
                mNavigator.addFragment(HomeFragment.newInstance(Constant.REMOTE, Genres.ALL_AUDIO),
                        getFragmentManager());
                break;
            case GenresId.ALTERNATIVE_ROCK_INT:
                mNavigator.addFragment(HomeFragment.newInstance(Constant.REMOTE, Genres.ALTERNATIVE_ROCK),
                        getFragmentManager());
                break;
            case GenresId.AMBIENT_INT:
                mNavigator.addFragment(HomeFragment.newInstance(Constant.REMOTE, Genres.AMBIENT),
                        getFragmentManager());
                break;
            case GenresId.CLASSICAL_INT:
                mNavigator.addFragment(HomeFragment.newInstance(Constant.REMOTE, Genres.CLASSICAL),
                        getFragmentManager());
                break;
            case GenresId.COUNTRY_INT:
                mNavigator.addFragment(HomeFragment.newInstance(Constant.REMOTE, Genres.COUNTRY),
                        getFragmentManager());
                break;
        }
    }
}
