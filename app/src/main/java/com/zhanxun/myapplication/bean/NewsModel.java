package com.zhanxun.myapplication.bean;

import java.util.List;

/**
 * Created by wilson on 2017/4/18.
 */

public class NewsModel {
    private long date;
    private List<Story> stories;

    public class Story{
        private List<String> images;
        private int type;
        private long id;
        private long ga_prefix;
        private String title;

        public List<String> getImages() {
            return images;
        }

        public String getTitle() {
            return title;
        }
    }

    public List<Story> getStories() {
        return stories;
    }


}
