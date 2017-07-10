package g.newsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Grzegorz on 2017-07-10.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private static Context mContext;
    ListView list;

    public NewsAdapter(Context context, List<News> newses) {
        super(context, 0, newses);
        mContext = context;
    }

    public static class BookView {
        TextView title;
        TextView author;
        TextView section;


        public BookView (View view) {
            title = (TextView) view.findViewById(R.id.news_title);
            author = (TextView) view.findViewById(R.id.news_author);
            section = (TextView) view.findViewById(R.id.news_section);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        BookView bookView;

        list = (ListView) list.findViewById(R.id.news_list);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list, parent, false);
            bookView = new BookView(convertView);
            convertView.setTag(bookView);
        } else { bookView = (BookView) convertView.getTag(); }

        News current = getItem(position);

        bookView.title.setText(current.getTitle());
        bookView.author.setText(current.getAuthor());
        bookView.section.setText(current.getAuthorr());

        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("Button", "clickie");
            }
        });

        return convertView;
    }
}

