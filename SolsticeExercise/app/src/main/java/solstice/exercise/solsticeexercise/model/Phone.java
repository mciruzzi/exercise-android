package solstice.exercise.solsticeexercise.model;

import java.io.Serializable;

/**
 * Created by romac-ubuntu on 25/10/15.
 */
public class Phone implements Serializable{
    private String work;
    private String home;
    private String mobile;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
