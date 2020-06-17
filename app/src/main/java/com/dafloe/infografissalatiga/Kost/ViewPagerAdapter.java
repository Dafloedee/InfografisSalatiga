package com.dafloe.infografissalatiga.Kost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.dafloe.infografissalatiga.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<SetGetKost> listGambar;
    private Integer [] images = {R.drawable.kostan,R.drawable.kost2};

    public ViewPagerAdapter(Context context, List<SetGetKost> listGambar) {
        this.context = context;
        this.listGambar = listGambar;
        layoutInflater = layoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listGambar.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.view_pager_image, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_detail);

        Picasso.get().load(listGambar.get(position).getmGambarDetailKost()).into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}