package com.ztkj.victe.ui.banners;

public class Banners {
    private int bannersid;
    private String pos;
    private String pic;
    private int status; // 0为关闭，1为启用

    public int getBannersid() {
        return bannersid;
    }

    public void setBannersid(int bannersid) {
        this.bannersid = bannersid;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
