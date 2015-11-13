package fr.dtrx.librairie.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.activities.BookCatalogActivity;

public class BookCatalogFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_catalog, container, false);
    }

}
