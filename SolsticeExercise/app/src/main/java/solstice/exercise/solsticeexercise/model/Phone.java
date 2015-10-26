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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if ( work != null && !work.isEmpty() ) builder.append( work + " \t" + "WORK" + '\n' );
        if ( home != null && !home.isEmpty() ) builder.append( home + " \t" + "HOME" + '\n');
        if ( mobile != null && !mobile.isEmpty() ) builder.append( mobile+ " \t" + "MOBILE" +  '\n' );
        // TODO GET LOCALIZED RESOURCES FOR WORK,HOME,MOBILE Strings

        return builder.toString();

    }
}
