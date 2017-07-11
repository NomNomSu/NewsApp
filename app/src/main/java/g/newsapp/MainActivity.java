package g.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private TextView mEmptyStateTextView;
    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=technology&&order-by=newest&order-date=published&show-section=true&show-fields=headline&show-references=author&page=1&page-size=50&api-key=test";
    private NewsAdapter newsAdapter;
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        getSystemService(Context.CONNECTIVITY_SERVICE);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        ListView newsListView = (ListView) findViewById(R.id.news_list);
        newsListView.setEmptyView(mEmptyStateTextView);
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            newsAdapter = new NewsAdapter(this, new ArrayList<News>());
            newsListView.setAdapter(newsAdapter);

            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    News currentNews = newsAdapter.getItem(position);
                    Uri newsUri = Uri.parse(currentNews != null ? currentNews.getUrl() : null);
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                    startActivity(websiteIntent);
                }
            });
        } else {
            mEmptyStateTextView.setText(R.string.no_news_or_conn);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.d("Now searching for: ", NEWS_REQUEST_URL);
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        mEmptyStateTextView.setText(R.string.no_news_or_conn);
        newsAdapter.clear();

        if (newses != null && !newses.isEmpty()) {
            newsAdapter.addAll(newses);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        newsAdapter.clear();
    }


}

