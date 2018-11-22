package app.weather.vicente.com.weatherapp.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import app.weather.vicente.com.weatherapp.R;
import app.weather.vicente.com.weatherapp.WeatherApp;
import app.weather.vicente.com.weatherapp.util.ForecastType;
import app.weather.vicente.com.weatherapp.view.ForecastDetailView;
import app.weather.vicente.com.weatherapp.view.model.ForecastViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Vicente on 09/02/2017.
 */

public class ImageAdapater extends RecyclerView.Adapter<ImageAdapater.MyViewHolder> {

    private List<ForecastViewModel> forecastViewModelList;
    private View itemView;
    private Context context;
    private ForecastDetailView forecastDetailView;

    public ImageAdapater(ForecastDetailView forecastDetailView) {
        this.forecastDetailView = forecastDetailView;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = WeatherApp.getContext();
        this.itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_slider_image, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position == (getItemCount() - 1)) {
            holder.ivForecast.setVisibility(View.GONE);
            holder.tvForecastType.setVisibility(View.GONE);
            holder.tvForecastName.setVisibility(View.GONE);
        } else {
            ForecastViewModel forecastViewModel = getForecastViewModelList().get(position);
            holder.ivForecast.setVisibility(View.VISIBLE);
            holder.tvForecastType.setVisibility(View.VISIBLE);
            holder.tvForecastName.setVisibility(View.VISIBLE);

            holder.ivForecast.setImageDrawable(getIcon(forecastViewModel.getIcon()));
            holder.tvForecastType.setText(forecastViewModel.getCondition());
            holder.tvForecastName.setText(forecastViewModel.getName());
        }
    }

    @Override
    public int getItemCount() {
        return getForecastViewModelList().size();
    }

    public void clearItems() {
        if (!getForecastViewModelList().isEmpty()) {
            getForecastViewModelList().clear();
            notifyDataSetChanged();
        }
    }

    public List<ForecastViewModel> getForecastViewModelList() {
        if (forecastViewModelList == null) {
            this.forecastViewModelList = new ArrayList<>();
        }
        return forecastViewModelList;
    }

    private Drawable getIcon(String icon) {
        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, ForecastType.findByValue(icon)));
        int color = ContextCompat.getColor(context, R.color.colorAccent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DrawableCompat.setTint(drawable, color);
        } else {
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        return drawable;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_forecast)
        ImageView ivForecast;
        @BindView(R.id.tv_forecast_name)
        TextView tvForecastName;
        @BindView(R.id.tv_forecast_type)
        TextView tvForecastType;
        @BindView(R.id.ll_forecast)
        LinearLayout lForecst;

        @OnClick(R.id.ll_forecast)
        public void forecastOnCLick() {
            if (!getForecastViewModelList().isEmpty()) {
                forecastDetailView.onSwitch(getLayoutPosition());
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
