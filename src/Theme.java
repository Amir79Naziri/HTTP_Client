import java.awt.*;

public enum Theme
{
    DARK (Color.WHITE,
            new Color (122, 103,218),
            new Color (45, 46, 42, 255),
            new Color (60, 62, 63, 255),
            new Color (40, 38, 37, 255))
    , WHITE (Color.BLACK,
            new Color (221, 221, 221),
            new Color (221, 221, 221),
            new Color (73, 75, 76, 255),
            Color.WHITE);

    private Color foreGroundColorV1;
    private final Color foreGroundColorV2 = Color.LIGHT_GRAY;
    private final Color foreGroundColorV3 = Color.GRAY;
    private final Color foreGroundColorV4 = Color.DARK_GRAY;

    private Color backGroundColorV1; // baseHeader
    private Color backGroundColorV2; // firstPanel panels backGround
    private Color backGroundColorV3; // request Hover
    private Color backGroundColorV4; // secondPanel panels backGround

    Theme (Color foreGroundColorV1, Color backGroundColorV1, Color backGroundColorV2,
           Color backGroundColorV3, Color backGroundColorV4)
    {
        this.foreGroundColorV1 = foreGroundColorV1;
        this.backGroundColorV1 = backGroundColorV1;
        this.backGroundColorV2 = backGroundColorV2;
        this.backGroundColorV3 = backGroundColorV3;
        this.backGroundColorV4 = backGroundColorV4;
    }

    public Color getBackGroundColorV1 () {
        return backGroundColorV1;
    }

    public Color getBackGroundColorV2 () {
        return backGroundColorV2;
    }

    public Color getBackGroundColorV3 () {
        return backGroundColorV3;
    }

    public Color getBackGroundColorV4 () {
        return backGroundColorV4;
    }

    public Color getForeGroundColorV1 () {
        return foreGroundColorV1;
    }

    public Color getForeGroundColorV2 () {
        return foreGroundColorV2;
    }

    public Color getForeGroundColorV3 () {
        return foreGroundColorV3;
    }

    public Color getForeGroundColorV4 () {
        return foreGroundColorV4;
    }
}
