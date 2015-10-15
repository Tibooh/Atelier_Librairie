package fr.dtrx.librairie.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.dtrx.librairie.R;

/**
 * Created by Quentin on 15/10/2015.
 */
public class BookFilterCatalogFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_book_filter_catalog, container, false);
    }
}
