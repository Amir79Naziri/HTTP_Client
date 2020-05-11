import java.io.Serializable;

public class OptionData implements Serializable
{
    private Theme theme;
    private boolean isHideInSystemTray;
    private boolean isFollowRedirect;

    public OptionData ()
    {
        theme = Theme.DARK;
        isFollowRedirect = false;
        isHideInSystemTray = false;
    }

    public void setFollowRedirect (boolean followRedirect) {
        isFollowRedirect = followRedirect;
    }

    public void setHideInSystemTray (boolean hideInSystemTray) {
        isHideInSystemTray = hideInSystemTray;
    }

    public void setTheme (Theme theme) {
        this.theme = theme;
    }

    public Theme getTheme () {
        return theme;
    }

    public boolean isHideInSystemTray () {
        return isHideInSystemTray;
    }

    public boolean isFollowRedirect () {
        return isFollowRedirect;
    }
}
