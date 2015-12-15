package fr.dtrx.librairie.fragments;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.dtrx.librairie.R;
import fr.dtrx.librairie.model.Book;
import fr.dtrx.librairie.model.BookCatalog;
import fr.dtrx.librairie.functions.ImageFunctions;

public class BookFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    public void update(int position_book) {
        View view = getView();

        ImageView book_image = (ImageView) view.findViewById(R.id.book_image);
        TextView book_title = (TextView) view.findViewById(R.id.book_title);
        TextView book_author = (TextView) view.findViewById(R.id.book_author);
        TextView book_year = (TextView) view.findViewById(R.id.book_year);
        TextView book_edition = (TextView) view.findViewById(R.id.book_edition);
        TextView book_description = (TextView) view.findViewById(R.id.book_description);

        Book book = BookCatalog.list.get(position_book);

        book_image.setImageBitmap(BitmapFactory.decodeFile(book.getImage()));
        book_title.setText(book.getTitle());
        book_author.setText(book.getAuthor());
        book_year.setText(book.getYear());
        book_edition.setText(book.getEdition());
        book_description.setText(book.getDescription());
    }

}
