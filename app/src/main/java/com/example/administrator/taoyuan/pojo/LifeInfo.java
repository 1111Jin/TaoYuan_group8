package com.example.administrator.taoyuan.pojo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public class LifeInfo {

    private int userId;
    private String userName;
    private String content;
    private Date time ;
    private String style;
    private String headphoto;
    private String content_photo;
    private int dontaiId;
    private List<Remark> remarklist;



    public List<Remark> getRemarklist() {
        return remarklist;
    }

    public void setRemarklist(List<Remark> remarklist) {
        List<Remark> list = new ArrayList<Remark>();

        while (true) {
            if (remarklist.size() == 0) {
                break;
            } else {
                Remark remark = remarklist.get(0);
                if (remark.getFatherRemarkId() == null) {
                    list.add(remark);
                    remarklist.remove(remark);

                    getRemarkByFather(list, remarklist, remark);
                }
            }

            this.remarklist = list;
        }
    }

    public void getRemarkByFather(List<Remark> list, List<Remark> remarklist,
                                  Remark remark) {
        System.out.println(remarklist+"11111111111");
        while (true) {
            if (remarklist.size() == 0) {
                break;
            } else {
                Remark r = remarklist.get(0);
                if (r.getFatherRemarkId() == remark.getRemarkId()) {
                    list.add(r);
                    remarklist.remove(r);

                    getRemarkByFather(list, remarklist, r);
                }
            }
        }
    }


    public LifeInfo(){}



    public LifeInfo(int userId, String userName, String content,
                    String headphoto, String content_photo, int dontaiId,
                    List<Remark> remarklist) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.headphoto = headphoto;
        this.content_photo = content_photo;
        this.dontaiId = dontaiId;
        this.remarklist = remarklist;
    }

    public LifeInfo(int userId, String userName, String content, Date time,
                    String style, String headphoto, String content_photo, int dontaiId) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.style = style;
        this.headphoto = headphoto;
        this.content_photo = content_photo;
        this.dontaiId = dontaiId;
    }

    public LifeInfo(int userId, String userName, String content, Date time,
                    String style, String headphoto, String content_photo, int dontaiId,
                    List<Remark> remarklist) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.style = style;
        this.headphoto = headphoto;
        this.content_photo = content_photo;
        this.dontaiId = dontaiId;
//		setRemarklist(remarklist);
        this.remarklist = remarklist;
    }



    public int getDontaiId() {
        return dontaiId;
    }




    public void setDontaiId(int dontaiId) {
        this.dontaiId = dontaiId;
    }




    public LifeInfo(String content) {
        super();
        this.content = content;
    }




    public LifeInfo(int userId, String userName, String content, Date time,
                    String style, String headphoto, String contentPhoto) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.style = style;
        this.headphoto = headphoto;
        content_photo = contentPhoto;
    }




    public int getUserId() {
        return userId;
    }






    public void setUserId(int userId) {
        this.userId = userId;
    }






    public String getUserName() {
        return userName;
    }






    public void setUserName(String userName) {
        this.userName = userName;
    }






    public String getContent() {
        return content;
    }






    public void setContent(String content) {
        this.content = content;
    }






    public Date getTime() {
        return time;
    }






    public void setTime(Date time) {
        this.time = time;
    }






    public String getStyle() {
        return style;
    }






    public void setStyle(String style) {
        this.style = style;
    }






    public String getHeadphoto() {
        return headphoto;
    }






    public void setHeadphoto(String headphoto) {
        this.headphoto = headphoto;
    }






    public String getContent_photo() {
        return content_photo;
    }






    public void setContent_photo(String contentPhoto) {
        content_photo = contentPhoto;
    }

    @Override
    public String toString() {
        return "LifeInfo [userId=" + userId + ", userName=" + userName
                + ", content=" + content + ", time=" + time + ", style="
                + style + ", headphoto=" + headphoto + ", content_photo="
                + content_photo + ", dontaiId=" + dontaiId + ", remarklist="
                + remarklist + "]";
    }
}
