/**
 *
 */
package com.shbd.zhsh.pager;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shbd.zhsh.R;
import com.shbd.zhsh.bean.NewsCenterData.NewsTabData;
import com.shbd.zhsh.bean.NewsData;
import com.shbd.zhsh.bean.NewsData.NewsList;
import com.shbd.zhsh.bean.NewsData.TopNews;
import com.shbd.zhsh.global.Constants;
import com.shbd.zhsh.slidingdetail.BaseDetailPager;
import com.shbd.zhsh.utils.ToastUtils;
import com.shbd.zhsh.view.MyViewPager;
import com.shbd.zhsh.view.NewsListView;
import com.shbd.zhsh.view.NewsListView.OnRefreshListener;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.List;

/**
 * @author yh
 */
public class TabDetailPager extends BaseDetailPager {
    private NewsTabData mData;
    private NewsListView mListView;
    private CirclePageIndicator mIndicator;
    private TextView mTvTitle;
    private MyViewPager mViewPager;
    private String mUrl;
    private String mLoadMoreUrl;
    private List<TopNews> mTopNews;
    private List<NewsList> mNewsList;
    private ListViewAdapter mListViewAdapter;


    public TabDetailPager(Activity activity, NewsTabData newsTabData) {
        super(activity);
        this.mData = newsTabData;
        this.mUrl = Constants.URL.BASE_URL + newsTabData.url;
        initData();
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.pager_table_detail, null);
        mListView = (NewsListView) view.findViewById(R.id.lv_pager_table_detail);

        //给listView添加头部,以便让图片轮播一起滑动
        View listItemView = View.inflate(mActivity, R.layout.listview_header, null);
        mTvTitle = (TextView) listItemView
                .findViewById(R.id.tv_pager_table_detail_title);
        mViewPager = (MyViewPager) listItemView
                .findViewById(R.id.vp_pager_table_detail);
        mIndicator = (CirclePageIndicator) listItemView
                .findViewById(R.id.indicator_pager_table_detail);
        mListView.addHeaderView(listItemView);

        mListView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void refresh() {
                getDataFromServer();
            }

            @Override
            public void loadMore() {
                getMoreDataFromServer();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("pos:"+position);
            }
        });
        return view;
    }


    @Override
    public void initData() {
        getDataFromServer();
    }

    /**
     * 加载更多
     */
    private void getMoreDataFromServer() {
        System.out.println("加载更多");
        if (!TextUtils.isEmpty(mLoadMoreUrl)){
            HttpUtils utils = new HttpUtils();
            utils.send(HttpMethod.GET, mLoadMoreUrl, new RequestCallBack<String>() {
                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    System.out.println("加载更多成功");
                    processResult(responseInfo.result, true);
                    mListView.onRefreshComplete(false,true);
                }

                @Override
                public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
                    ToastUtils.showToast(mActivity, s);
                    mListView.onRefreshComplete(false,true);

                }
            });
        }
        else {
            ToastUtils.showToast(mActivity,"没有更多数据了");
            mListView.onRefreshComplete(false,true);
        }

    }


    private void getDataFromServer() {
        System.out.println("从服务器获取数据");
        HttpUtils utils = new HttpUtils();
        utils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                mListView.onRefreshComplete(false,false);

                ToastUtils.showToast(mActivity, arg1);
                arg0.printStackTrace();
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                mListView.onRefreshComplete(true,false);
                processResult(arg0.result, false);
            }

        });
    }

    private void processResult(String result, boolean isLoadMore) {
        Gson gson = new Gson();
        NewsData newsData = gson.fromJson(result, NewsData.class);
        if (isLoadMore) {
            //加载更多
            List<NewsList> moreNewsList = newsData.data.news;
            if (mNewsList != null) {
                mNewsList.addAll(moreNewsList);
                mListViewAdapter.notifyDataSetChanged();
            }
        } else {
            //初始化数据
            mTopNews = newsData.data.topnews;
            mNewsList = newsData.data.news;
            mViewPager.setAdapter(new ViewPagerAdapter());
            mIndicator.setViewPager(mViewPager);
            mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int arg0) {
                    mTvTitle.setText(mTopNews.get(arg0).title);
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {

                }
            });
            mIndicator.onPageSelected(0);

            // 新闻列表展现
            mListViewAdapter = new ListViewAdapter();
            mListView.setAdapter(mListViewAdapter);
        }

        //加载更多的url
        if (TextUtils.isEmpty(newsData.data.more)) {
            mLoadMoreUrl = null;
        } else {
            mLoadMoreUrl = Constants.URL.BASE_URL + newsData.data.more;
        }
    }

    class ViewPagerAdapter extends PagerAdapter {
        private BitmapUtils bitmapUtils;

        public ViewPagerAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
        }

        @Override
        public int getCount() {
            return mTopNews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setScaleType(ScaleType.FIT_XY);
            bitmapUtils.display(imageView, mTopNews.get(position).topimage);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class ListViewAdapter extends BaseAdapter {
        private BitmapUtils bitmapUtils;

        public ListViewAdapter() {
            bitmapUtils = new BitmapUtils(mActivity);
            bitmapUtils.configDefaultLoadingImage(R.mipmap.news_pic_default);
        }

        @Override
        public int getCount() {
            return mNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return mNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mActivity, R.layout.itemlist_news_detail, null);
                //初始化View组件
                holder.imageView = (ImageView) convertView.findViewById(R.id.iv_news_detail);
                holder.content = (TextView) convertView.findViewById(R.id.tv_news_detail_content);
                holder.date = (TextView) convertView.findViewById(R.id.tv_news_detail_date);

                //将holder存入缓存中，以减少对象的创建
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //给View组件赋值
            bitmapUtils.display(holder.imageView, mNewsList.get(position).listimage);
            holder.content.setText(mNewsList.get(position).title);
            holder.date.setText(mNewsList.get(position).pubdate);

            return convertView;
        }

    }

    static class ViewHolder {
        ImageView imageView;
        TextView content;
        TextView date;

    }

}
