package cn.hurrican.model;

import java.util.List;

public class Riddles {
    private List<Riddle> riddles;
    private Integer aid;
    private Integer platformId;
    
    
    

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public List<Riddle> getRiddles() {
        return riddles;
    }

    public void setRiddles(List<Riddle> riddles) {
        this.riddles = riddles;
    }
    
}
