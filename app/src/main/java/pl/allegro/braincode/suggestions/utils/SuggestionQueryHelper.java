package pl.allegro.braincode.suggestions.utils;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

import pl.allegro.braincode.communication.ServiceProvider;
import pl.allegro.braincode.fragments.GetSuggestionsFragment;
import pl.allegro.braincode.messages.price.Suggestion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionQueryHelper {
    public static void query(final GetSuggestionsFragment fragment, String query, Integer days) {
        ServiceProvider.INSTANCE.getServerService().getPrices(fragment.getCategory(), query, days).enqueue(new Callback<Suggestion>() {
            @Override
            public void onResponse(Call<Suggestion> call, Response<Suggestion> response) {
                if(response.body() != null && response.body() != null) {
                    List<Entry> entries = DtoConverter.convert(response.body());
                    fragment.setChartValues(entries);
                    ChartSetup.setupChart(fragment.getChart(), ChartSetup.prepareDataForChart(entries));
                    fragment.setBestPrice(DtoConverter.convert(response.body().getBestPrice()));
                    fragment.setFastest(DtoConverter.convert(response.body().getFastest()));
                    if(fragment.getStrategy().equals("money")) {
                        fragment.getChart().highlightValue(fragment.getBestPrice().getX(), 0, true);
                    } else {
                        fragment.getChart().highlightValue(fragment.getFastest().getX(), 0, true);
                    }
                } else {
                    fragment.showError("No data found.");
                }
            }

            @Override
            public void onFailure(Call<Suggestion> call, Throwable t) {
                fragment.showError("Rest call failed.");
            }
        });
    }
}
