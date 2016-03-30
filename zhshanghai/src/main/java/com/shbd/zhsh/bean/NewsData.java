/**
 * 
 */
package com.shbd.zhsh.bean;

import java.util.List;

/**
 * @author yh 新闻中心内部数据实体类
 */
public class NewsData {
	public NewDetailData data;
	public int retcode;

	public class NewDetailData {
		public String countcommenturl;
		public String more;
		public List<NewsList> news;
		public String title;
		public List<Topic> topic;
		public List<TopNews> topnews;

		@Override
		public String toString() {
			return "NewDetailData [countcommenturl=" + countcommenturl
					+ ", more=" + more + ", news=" + news + ", title=" + title
					+ ", topic=" + topic + ", topnews=" + topnews + "]";
		}

	}

	public class NewsList {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "NewsList [comment=" + comment + ", commentlist="
					+ commentlist + ", commenturl=" + commenturl + ", id=" + id
					+ ", listimage=" + listimage + ", title=" + title
					+ ", type=" + type + ", url=" + url + "]";
		}

	}

	class Topic {
		public String description;
		public int id;
		public String listimage;
		public int sort;
		public String title;
		public String url;

		@Override
		public String toString() {
			return "Topic [description=" + description + ", id=" + id
					+ ", listimage=" + listimage + ", sort=" + sort
					+ ", title=" + title + ", url=" + url + "]";
		}

	}

	public class TopNews {
		public boolean comment;
		public String commentlist;
		public String commenturl;
		public int id;
		public String title;
		public String topimage;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TopNews [comment=" + comment + ", commentlist="
					+ commentlist + ", commenturl=" + commenturl + ", id=" + id
					+ ", topimage=" + topimage + ", title=" + title + ", type="
					+ type + ", url=" + url + "]";
		}

	}

	@Override
	public String toString() {
		return "NewsData [retcode=" + retcode + ", data=" + data + "]";
	}

}
