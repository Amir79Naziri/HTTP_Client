import java.io.Serializable;

public class OptionData implements Serializable
{
    private int theme;
    private boolean isHideInSystemTray;
    private boolean isFollowRedirect;

    public OptionData ()
    {
        theme = 0;
        isFollowRedirect = false;
        isHideInSystemTray = false;
    }

    public void setFollowRedirect (boolean followRedirect) {
        isFollowRedirect = followRedirect;
    }

    public void setHideInSystemTray (boolean hideInSystemTray) {
        isHideInSystemTray = hideInSystemTray;
    }

    public void setTheme (int theme) {
        this.theme = theme;
    }

    public int getTheme () {
        return theme;
    }

    public boolean isHideInSystemTray () {
        return isHideInSystemTray;
    }

    public boolean isFollowRedirect () {
        return isFollowRedirect;
    }
}
