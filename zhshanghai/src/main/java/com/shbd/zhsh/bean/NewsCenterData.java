/**
 * 
 */
package com.shbd.zhsh.bean;

import java.util.ArrayList;

/**
 * @author yh 新闻中心数据实体类
 */
public class NewsCenterData {
	public int retcode;
	public ArrayList<String> extend;
	public ArrayList<NewsData> data;

	public class NewsData {
		public int id;
		public String title;
		public int type;
		public ArrayList<NewsTabData> children;

		@Override
		public String toString() {
			return "NewsData [id=" + id + ", title=" + title + ", type=" + type
					+ ", children=" + children + "]";
		}

	}

	public class NewsTabData {
		public int id;
		public String title;
		public int type;
		public String url;

		@Override
		public String toString() {
			return "NewsTabData [id=" + id + ", title=" + title + ", type="
					+ type + ", url=" + url + "]";
		}

	}

	@Override
	public String toString() {
		return "NewsCenterData [retcode=" + retcode + ", extend=" + extend
				+ ", data=" + data + "]";
	}

}
