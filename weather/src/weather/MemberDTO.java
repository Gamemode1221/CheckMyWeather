package weather;

import java.util.Date;

public class MemberDTO {
    private String id;
    private String pwd;
    private Date mDate;

    public MemberDTO(String id, String pwd, Date mDate) {
        this.id = id;
        this.pwd = pwd;
        this.mDate = mDate;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getmDate() {
        return mDate;
    }
    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }
}
