/**
 * 
 */
package com.shbd.zhsh.fragment;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.shbd.zhsh.R;
import com.shbd.zhsh.activity.MainActivity;
import com.shbd.zhsh.bean.NewsCenterData.NewsData;
import com.shbd.zhsh.pager.NewsCenterPager;

/**
 * @author yh
 *侧边栏fragment
 */
public class SlidingMenuFragment extends BaseFragment {
    private ListView mLvMenu;
	private LeftMenuAdapter mAdapter;
	private int mCurrentPos;
	ArrayList<NewsData> mData;

	@Override
	public View initView() {  
		View leftMenuView = View.inflate(mActivity, R.layout.fragment_sliding_menu, null);
		mLvMenu=(ListView) leftMenuView.findViewById(R.id.lv_sliding_menu);
		mLvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mCurrentPos=position;     
				changeCurrentDetailPager(position);
				//控制侧边栏的打开与关闭
				toggle();
				mAdapter.notifyDataSetChanged();
			}

			
		});
		return leftMenuView;
	}
	
	public void setData(ArrayList<NewsData> data) {
		mData=data;
		mAdapter = new LeftMenuAdapter();
		mLvMenu.setAdapter(mAdapter);
		//重置当前页面
		mCurrentPos=0;
	}
    class LeftMenuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public NewsData getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.itemlist_sliding_menu, null);
			TextView textView=(TextView) view.findViewById(R.id.tv_itemlist_content);
			textView.setText(getItem(position).title);
			
			//设置enabled，保证点击时改变条目颜色
			if (mCurrentPos==position) {
				textView.setEnabled(true);
			}else {
				textView.setEnabled(false);
			}
			return view;
		}
    	
    }
    
    /**
     * 切换详情页面
     * @param position
     */
    private void changeCurrentDetailPager(int position) {
    	MainActivity activity=(MainActivity) mActivity;	
    	ContentFragment fragment=(ContentFragment) activity.getFragmentByTag("content_fragment");
    	NewsCenterPager newsCenterPager = fragment.getNewsCenterPager();
    	newsCenterPager.changeCurrentDetailPager(position);
	}
    
    /**
     * 控制侧边栏的开关
     */
    private void toggle(){
    	MainActivity activity=(MainActivity)mActivity;
    	SlidingMenu slidingMenu = activity.getSlidingMenu();
    	//控制侧边栏的打开与关闭 ，若侧边栏为打开状态，调用此方法则关闭侧边栏，反之，则打开
    	slidingMenu.toggle();
    }
}
