package com.shbd.zhsh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shbd.zhsh.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yh 自定义ListView,给其添加头部局
 */
public class NewsListView extends ListView implements AdapterView.OnItemClickListener{
    private float startY = -1;
    private int mMeasuredHeight;
    private TextView mTvRefreshTitle;
    private TextView mTvRefreshDate;
    private ImageView mIvArrow;
    private ProgressBar mPbLodding;

    //脚布局
    private int mFooterMeasuredHeight;
    private boolean isLoadingMore = false;
    private static final int STATUS_PULL = 1;// 下拉刷新
    private static final int STATUS_REFRESHING = 2;// 正在刷新
    private static final int STATUS_UP = 3; // 松开刷新

    private int mCurrentRefreshStatus = STATUS_PULL;
    private View topView;
    private View mFooterView;
    private RotateAnimation amiUp;
    private RotateAnimation amiDown;


    public NewsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initTopView();
        initFooterView();
    }

    public NewsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTopView();
        initFooterView();
    }

    public NewsListView(Context context) {
        super(context);
        initTopView();
        initFooterView();
    }

    private void initTopView() {
        topView = View.inflate(getContext(), R.layout.listview_refresh_header,
                null);
        mTvRefreshTitle = (TextView) topView
                .findViewById(R.id.tv_refresh_header_title);
        mIvArrow = (ImageView) topView.findViewById(R.id.iv_refresh_header);
        mPbLodding = (ProgressBar) topView.findViewById(R.id.pb_refresh_header);
        mTvRefreshDate = (TextView) topView.findViewById(R.id.tv_refresh_header_date);
        addHeaderView(topView);

        // 手动测量当前view
        topView.measure(0, 0);
        // 获取当前view测量的高度(若想在此处获取它的高度，则必须以下方法，使用getHeight()无法获取)
        mMeasuredHeight = topView.getMeasuredHeight();
        // 隐藏下拉刷新控件
        topView.setPadding(0, -mMeasuredHeight, 0, 0);

        initAnimation();

    }


    /**
     * 初始化脚部局
     */
    private void initFooterView() {
        mFooterView = View.inflate(getContext(), R.layout.listview_refresh_footer, null);
        addFooterView(mFooterView);
        mFooterView.measure(0, 0);
        mFooterMeasuredHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterMeasuredHeight, 0, 0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                // 当状态为正在刷新时，不进行其他操作
                if (mCurrentRefreshStatus == STATUS_REFRESHING) {
                    break;
                }
            /*
			 * 当用户按住头条新闻往下移动时，由于在子控件中设置了requestDisallowInterceptTouchEvent（true）
			 * 导致当前控件不会响应当前ACTION_DOWN事件，因此必须在这里重新获取startY
			 */
                if (startY == -1) {
                    startY = ev.getRawY();
                }

                float endY = ev.getRawY();
                float dy = endY - startY;

                float dyTop = dy - mMeasuredHeight;
                if (getFirstVisiblePosition() == 0) {
                    if (dy >= 0) {// 向下滑动
                        if (dyTop >= 0 && mCurrentRefreshStatus != STATUS_UP) {
                            mCurrentRefreshStatus = STATUS_UP;
                            setCurrentRefreshStatus();
                        } else if (dyTop < 0
                                && mCurrentRefreshStatus != STATUS_PULL) {
                            mCurrentRefreshStatus = STATUS_PULL;
                            setCurrentRefreshStatus();
                        }

                        topView.setPadding(0, (int) dyTop, 0, 0);

                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                startY = -1;// 当触发抬起事件时，重新将起始位置置为-1
                if (mCurrentRefreshStatus == STATUS_REFRESHING) {
                    topView.setPadding(0, 0, 0, 0);
                } else if (mCurrentRefreshStatus == STATUS_PULL) {
                    topView.setPadding(0, -mMeasuredHeight, 0, 0);
                } else if (mCurrentRefreshStatus == STATUS_UP) {
                    topView.setPadding(0, 0, 0, 0);
                    mCurrentRefreshStatus = STATUS_REFRESHING;
                }
                setCurrentRefreshStatus();


                //判断是否显示脚布局
                if (getLastVisiblePosition() >= getCount() - 1) {
                    //若当前的listView的可见条目为最后一条，则显示脚布局
                    mFooterView.setPadding(0, 0, 0, 0);
                    //刷新页面
                    if (mListener != null && !isLoadingMore) {
                        //若当前正在加载更多，则用户往上拉页面不刷新
                        isLoadingMore = true;
                        mListener.loadMore();
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * y为刷新箭头设置动画效果
     */
    private void initAnimation() {
        amiUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        amiUp.setDuration(500);
        amiUp.setFillAfter(true);// 保持动画完成后的样子

        amiDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        amiDown.setDuration(500);
        amiDown.setFillAfter(true);// 保持动画完成后的样子

    }

    /**
     * 根据刷新状态更新刷新界面信息
     */
    private void setCurrentRefreshStatus() {
        switch (mCurrentRefreshStatus) {
            case STATUS_PULL:
                mTvRefreshTitle.setText("下拉刷新");
                mIvArrow.setAnimation(amiDown);

                mPbLodding.setVisibility(ProgressBar.INVISIBLE);
                mIvArrow.setVisibility(ImageView.VISIBLE);

                break;
            case STATUS_REFRESHING:
                mTvRefreshTitle.setText("正在刷新");

                mPbLodding.setVisibility(ProgressBar.VISIBLE);
                mIvArrow.clearAnimation();// 必须先清理动画，否则以下代码会失效
                mIvArrow.setVisibility(ImageView.INVISIBLE);
                // 刷新界面
                mListener.refresh();
                break;
            case STATUS_UP:
                mTvRefreshTitle.setText("松开刷新");
                mIvArrow.setAnimation(amiUp);

                mPbLodding.setVisibility(ProgressBar.INVISIBLE);
                mIvArrow.setVisibility(ImageView.VISIBLE);
                break;

        }
    }

    private OnRefreshListener mListener;

    public interface OnRefreshListener {
        void refresh();

        void loadMore();
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mListener = listener;
    }

    /**
     * 设置上次刷新时间
     */
    public void setRefreshTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String freshDate = dateFormat.format(new Date());
        mTvRefreshDate.setText(freshDate);
    }

    /**
     * 刷新完成，重新初始化数据
     *
     * @param success
     */
    public void onRefreshComplete(boolean success, boolean isloadMore) {

        if (isloadMore) {
            //加载完脚布局后，重新设置加载更多状态为false,并隐藏脚布局
            isLoadingMore = false;
            mFooterView.setPadding(0, -mFooterMeasuredHeight, 0, 0);
        } else {
            mTvRefreshTitle.setText("下拉刷新");
            mIvArrow.setVisibility(ImageView.VISIBLE);
            mPbLodding.setVisibility(ProgressBar.INVISIBLE);
            // 隐藏下拉刷新控件
            topView.setPadding(0, -mMeasuredHeight, 0, 0);

            //将状态重新设置为下拉刷新
            mCurrentRefreshStatus = STATUS_PULL;

            if (success) {
                setRefreshTime();
            }
        }


    }

    private OnItemClickListener mOnItemClickListener;
    @Override
    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener=listener;
        super.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mOnItemClickListener.onItemClick(parent,view,position-getHeaderViewsCount(),id);
    }

}
