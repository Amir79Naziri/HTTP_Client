import java.io.Serializable;

/**
 * this class build for keeping option data's for save
 *
 * @author Amir Naziri
 */
public class OptionData implements Serializable
{
    private Theme theme; // theme
    private boolean isHideInSystemTray; // isHideInSystemTray
    private boolean isFollowRedirect; // isFollowRedirect

    /**
     * creates a new OptionData
     */
    public OptionData ()
    {
        theme = Theme.DARK;
        isFollowRedirect = false;
        isHideInSystemTray = false;
    }

    /**
     * setFollow Redirect
     * @param followRedirect followRedirect
     */
    public void setFollowRedirect (boolean followRedirect) {
        isFollowRedirect = followRedirect;
    }

    /**
     * setHideInSystemTray
     * @param hideInSystemTray hideInSystemTray
     */
    public void setHideInSystemTray (boolean hideInSystemTray) {
        isHideInSystemTray = hideInSystemTray;
    }

    /**
     * setTheme
     * @param theme theme
     */
    public void setTheme (Theme theme) {
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
    }

    /**
     * @return theme
     */
    public Theme getTheme () {
        return theme;
    }

    /**
     * @return isHideInSystemTray
     */
    public boolean isHideInSystemTray () {
        return isHideInSystemTray;
    }

    /**
     * @return isFollowRedirect
     */
    public boolean isFollowRedirect () {
        return isFollowRedirect;
    }
}
