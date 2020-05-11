import java.awt.*;
import java.io.Serializable;

public enum Theme implements Serializable
{
    DARK (Color.WHITE, Color.LIGHT_GRAY,Color.DARK_GRAY,
            new Color (122, 103,218),
            new Color (45, 46, 42, 255),
            new Color (60, 62, 63, 255),
            new Color (40, 38, 37, 255),
            new Color (109, 109, 109))
    , WHITE (Color.DARK_GRAY,Color.DARK_GRAY,Color.LIGHT_GRAY,
            new Color (221, 221, 221),
            new Color (221, 221, 221),
            new Color (192, 194, 195, 255),
            Color.WHITE,
            Color.GRAY);

    private Color foreGroundColorV1;
    private Color foreGroundColorV2;
    private Color foreGroundColorV3;
    private Color backGroundColorV1; // baseHeader
    private Color backGroundColorV2; // firstPanel panels backGround
    private Color backGroundColorV3; // request Hover
    private Color backGroundColorV4; // secondPanel and third panels backGround
    private Color backGroundColorV5; // background for resultHeaderKeyAndValue

    Theme (Color foreGroundColorV1, Color foreGroundColorV2, Color foreGroundColorV3,
           Color backGroundColorV1, Color backGroundColorV2, Color backGroundColorV3,
           Color backGroundColorV4, Color backGroundColorV5)
    {
        this.foreGroundColorV1 = foreGroundColorV1;
        this.foreGroundColorV2 = foreGroundColorV2;
        this.foreGroundColorV3 = foreGroundColorV3;
        this.backGroundColorV1 = backGroundColorV1;
        this.backGroundColorV2 = backGroundColorV2;
        this.backGroundColorV3 = backGroundColorV3;
        this.backGroundColorV4 = backGroundColorV4;
        this.backGroundColorV5 = backGroundColorV5;
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

    public Color getBackGroundColorV5 () {
        return backGroundColorV5;
    }
}
