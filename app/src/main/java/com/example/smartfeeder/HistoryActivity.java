package com.example.smartfeeder;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ListView historyList = findViewById(R.id.historyList);

        fetchFeedHistory(historyList);
    }

    private void fetchFeedHistory(final ListView listView) {
        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<List<FeedHistory>> call = apiInterface.getFeedHistory();

        call.enqueue(new Callback<List<FeedHistory>>() {
            @Override
            public void onResponse(Call<List<FeedHistory>> call, Response<List<FeedHistory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<FeedHistory> feedHistories = response.body();
                    ArrayAdapter<FeedHistory> adapter = new ArrayAdapter<>(HistoryActivity.this, android.R.layout.simple_list_item_1, feedHistories);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<FeedHistory>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
