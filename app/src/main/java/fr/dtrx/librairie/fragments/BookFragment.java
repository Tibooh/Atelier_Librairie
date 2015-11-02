package fr.dtrx.librairie.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookLibrary;

public class BookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    public void update(int position_book) {
        View view = getView();

        TextView text_view_title = (TextView) view.findViewById(R.id.book_title);
        TextView text_view_author = (TextView) view.findViewById(R.id.book_author);
        TextView text_view_year = (TextView) view.findViewById(R.id.book_year);
        TextView text_view_edition = (TextView) view.findViewById(R.id.book_edition);
        TextView text_view_description = (TextView) view.findViewById(R.id.book_description);

        Book book = BookLibrary.list.get(position_book);

        text_view_title.setText(book.getTitle());
        text_view_author.setText(book.getAuthor());
        text_view_year.setText(book.getYear());
        text_view_edition.setText(book.getEdition());
        text_view_description.setText(book.getDescription());
    }

}
