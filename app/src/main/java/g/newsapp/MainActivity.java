package g.newsapp;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String NEWS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=technology&&order-by=newest&order-date=published&show-section=true&show-fields=headline&show-references=author&page=1&page-size=50&api-key=test";
    private NewsAdapter newsAdapter;
    private static final int NEWS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        ListView newsListView = (ListView) findViewById(R.id.news_list);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsListView.setAdapter(newsAdapter);


        //newsView.setOnItemClickListener(new AdapterView.OnItemClickListener()

        /*{
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = newsAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews != null ? "http://google.com" : null);
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, currentNews);
                startActivity(websiteIntent);
            }
        });*/

    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.d("Now searching for: ", NEWS_REQUEST_URL);
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
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

