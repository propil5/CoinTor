package com.example.piotr.cointor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Piotr on 07.12.2017.
 */


public class CurrencyAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Currency> currencyList;
    private CurrencyItemClickListener currencyItemClickListener;
    private static DecimalFormat df3 = new DecimalFormat("0.###");

    public CurrencyAdapter(Context context, List<Currency> currencyList, CurrencyItemClickListener currencyItemClickListener) {
        this.context = context;
        this.currencyList = currencyList;
        this.currencyItemClickListener = currencyItemClickListener;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return currencyList.size();
    }

    @Override
    public Object getItem(int i) {
        return currencyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View currencyItemView = layoutInflater.inflate(R.layout.currency_item, null);
        TextView tvName = (TextView) currencyItemView.findViewById(R.id.tvName);
        TextView tvRate = (TextView) currencyItemView.findViewById(R.id.tvRate);
        TextView tvBRate = (TextView) currencyItemView.findViewById(R.id.tvBasicRate);

        final Currency c = currencyList.get(i);
        tvName.setText(c.getName());



        double rate= c.getRate();
        double bRate= (1.00/c.getRate());
        tvRate.setText(df3.format(rate));
        tvBRate.setText(df3.format(bRate));

        currencyItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currencyItemClickListener.onCurrencyItemClick(c);
            }
        });
        return currencyItemView;
    }


}
