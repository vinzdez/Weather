package app.weather.vicente.com.weatherapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.view.SearchView;
import app.weather.vicente.com.weatherapp.view.model.ResultViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vicente on 2/7/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private List<ResultViewModel> resultViewModelList;
    private View itemView;
    private Context context;
    private SearchView searchView;

    public ListAdapter(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_content, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ResultViewModel searchResultViewModel = getSearchResultViewModels().get(position);
        String value = searchResultViewModel.getName();
        if (searchResultViewModel.isState()) {
            value += ", ".concat(searchResultViewModel.getStateName());
        } else if (!searchResultViewModel.isState() && searchResultViewModel.isCategory()) {
            value += ", ".concat(searchResultViewModel.getCountry());
        } else {
            value += ", ".concat(value);
        }
        holder.tvResult.setText(value);
    }

    @Override
    public int getItemCount() {
        return getSearchResultViewModels().size();
    }

    public void clearItems() {
        if (!getSearchResultViewModels().isEmpty()) {
            getSearchResultViewModels().clear();
            notifyDataSetChanged();
        }
    }

    public List<ResultViewModel> getSearchResultViewModels() {
        if (resultViewModelList == null) {
            this.resultViewModelList = new ArrayList<>();
        }
        return resultViewModelList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_result)
        TextView tvResult;

        @OnClick(R.id.tv_result)
        public void onResultItemClick() {
            if (!getSearchResultViewModels().isEmpty()) {
                searchView.getSelectedItem(getSearchResultViewModels().get(getLayoutPosition()));
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
