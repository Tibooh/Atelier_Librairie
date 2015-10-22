package fr.dtrx.librairie.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.dtrx.librairie.R;

public class BookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        return view;
    }

    /*
    public void afficherDetail(Uri objet) {

    }
    */

}
