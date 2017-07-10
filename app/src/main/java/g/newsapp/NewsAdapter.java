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

    public static class NewsView {
        TextView title;
        TextView author;
        TextView section;


        public NewsView(View view) {
            title = (TextView) view.findViewById(R.id.news_title);
            author = (TextView) view.findViewById(R.id.news_author);
            section = (TextView) view.findViewById(R.id.news_section);
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        NewsView newsView;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list, parent, false);
            newsView = new NewsView(convertView);
            convertView.setTag(newsView);
        } else { newsView = (NewsView) convertView.getTag(); }

        News current = getItem(position);

        newsView.title.setText(current.getTitle());
        newsView.author.setText(current.getAuthor());
        newsView.section.setText(current.getAuthorr());
        return convertView;
    }
}

