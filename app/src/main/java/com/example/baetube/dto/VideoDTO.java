package com.example.baetube.dto;

public class VideoDTO
{
   private int videoId;
   private int channelId;
   private int views;
   private int like;
   private int hate;
   private int replyCount;
   private int categoryId;
   private String url;
   private String thumbnail;
   private String title;
   private String info;
   private String location;
   private String date;
   private boolean visible;
   private boolean age;

   // getter

   public int getVideoId()
   {
      return videoId;
   }

   public int getChannelId()
   {
      return channelId;
   }

   public int getViews()
   {
      return views;
   }

   public int getLike()
   {
      return like;
   }

   public int getHate()
   {
      return hate;
   }

   public int getReplyCount()
   {
      return replyCount;
   }

   public int getCategoryId()
   {
      return categoryId;
   }

   public String getUrl()
   {
      return url;
   }

   public String getThumbnail()
   {
      return thumbnail;
   }

   public String getTitle()
   {
      return title;
   }

   public String getInfo()
   {
      return info;
   }

   public String getLocation()
   {
      return location;
   }

   public String getDate()
   {
      return date;
   }

   public boolean isVisible()
   {
      return visible;
   }

   public boolean isAge()
   {
      return age;
   }

   // setter


   public void setVideoId(int videoId)
   {
      this.videoId = videoId;
   }

   public void setChannelId(int channelId)
   {
      this.channelId = channelId;
   }

   public void setViews(int views)
   {
      this.views = views;
   }

   public void setLike(int like)
   {
      this.like = like;
   }

   public void setHate(int hate)
   {
      this.hate = hate;
   }

   public void setReplyCount(int replyCount)
   {
      this.replyCount = replyCount;
   }

   public void setCategoryId(int categoryId)
   {
      this.categoryId = categoryId;
   }

   public void setUrl(String url)
   {
      this.url = url;
   }

   public void setThumbnail(String thumbnail)
   {
      this.thumbnail = thumbnail;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public void setInfo(String info)
   {
      this.info = info;
   }

   public void setLocation(String location)
   {
      this.location = location;
   }

   public void setDate(String date)
   {
      this.date = date;
   }

   public void setVisible(boolean visible)
   {
      this.visible = visible;
   }

   public void setAge(boolean age)
   {
      this.age = age;
   }
}
