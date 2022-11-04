package com.example.baetube.dto;

public class VideoDTO
{
   private int video_id;
   private int channel_id;
   private int views;
   private int like;
   private int hate;
   private int reply_count;
   private int category_id;
   private String url;
   private String thumbnail;
   private String title;
   private String info;
   private String location;
   private String date;
   private boolean visible;
   private boolean age;

   // getter
   public int getVideo_id()
   {
      return video_id;
   }

   public int getChannel_id()
   {
      return channel_id;
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

   public int getReply_count()
   {
      return reply_count;
   }

   public int getCategory_id()
   {
      return category_id;
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
   public void setVideo_id(int video_id)
   {
      this.video_id = video_id;
   }

   public void setChannel_id(int channel_id)
   {
      this.channel_id = channel_id;
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

   public void setReply_count(int reply_count)
   {
      this.reply_count = reply_count;
   }

   public void setCategory_id(int category_id)
   {
      this.category_id = category_id;
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
