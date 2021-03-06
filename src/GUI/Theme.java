package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * this class has theme init
 *
 * @author Amir naziri
 */
public enum Theme implements Serializable
{
    DARK (Color.WHITE, Color.LIGHT_GRAY,Color.DARK_GRAY,
            new Color (122, 103,218),
            new Color (45, 46, 42, 255),
            new Color (60, 62, 63, 255),
            new Color (40, 38, 37, 255),
            new Color (109, 109, 109),
            new ImageIcon ("./images/trashR1.png"),
            new ImageIcon ("./images/trashR2.png"),
            new ImageIcon ("./images/data.png"),
            new ImageIcon ("./images/addR1.png"),
            new ImageIcon ("./images/addR2.png"),
            new ImageIcon ("./images/gear.png")
            )
    , WHITE (Color.DARK_GRAY,Color.DARK_GRAY,Color.LIGHT_GRAY,
            new Color (221, 221, 221),
            new Color (221, 221, 221),
            new Color (192, 194, 195, 255),
            Color.WHITE,
            Color.GRAY,
        new ImageIcon ("./images/trashwR1.png"),
        new ImageIcon ("./images/trashwR2.png"),
        new ImageIcon ("./images/dataw.png"),
        new ImageIcon ("./images/addwR1.png"),
        new ImageIcon ("./images/addwR2.png"),
        new ImageIcon ("./images/gearw.png"));

    private Color foreGroundColorV1;
    private Color foreGroundColorV2;
    private Color foreGroundColorV3;
    private Color backGroundColorV1; // baseHeader
    private Color backGroundColorV2; // firstPanel panels backGround
    private Color backGroundColorV3; // request Hover
    private Color backGroundColorV4; // secondPanel and third panels backGround
    private Color backGroundColorV5; // background for resultHeaderKeyAndValue
    private ImageIcon trashR1;
    private ImageIcon trashR2;
    private ImageIcon addR1;
    private ImageIcon addR2;
    private ImageIcon data;
    private ImageIcon gear;

    /**
     * create new GUI.Theme
     * @param foreGroundColorV1 foreGroundColorVersion1
     * @param foreGroundColorV2 foreGroundColorVersion2
     * @param foreGroundColorV3 foreGroundColorVersion3
     * @param backGroundColorV1 backGroundColorVersion1
     * @param backGroundColorV2 backGroundColorVersion2
     * @param backGroundColorV3 backGroundColorVersion3
     * @param backGroundColorV4 backGroundColorVersion4
     * @param backGroundColorV5 backGroundColorVersion5
     * @param trashR1 trash icon
     * @param trashR2 rollover trash icon
     * @param data data icon
     * @param addR1 add new request icon
     * @param addR2 rollover add new request icon
     * @param gear gear icon
     */
    Theme (Color foreGroundColorV1, Color foreGroundColorV2, Color foreGroundColorV3,
           Color backGroundColorV1, Color backGroundColorV2, Color backGroundColorV3,
           Color backGroundColorV4, Color backGroundColorV5, ImageIcon trashR1,
           ImageIcon trashR2, ImageIcon data, ImageIcon addR1, ImageIcon addR2,
           ImageIcon gear)
    {
        this.foreGroundColorV1 = foreGroundColorV1;
        this.foreGroundColorV2 = foreGroundColorV2;
        this.foreGroundColorV3 = foreGroundColorV3;
        this.backGroundColorV1 = backGroundColorV1;
        this.backGroundColorV2 = backGroundColorV2;
        this.backGroundColorV3 = backGroundColorV3;
        this.backGroundColorV4 = backGroundColorV4;
        this.backGroundColorV5 = backGroundColorV5;
        this.addR1 = addR1;
        this.addR2 = addR2;
        this.data = data;
        this.trashR1 = trashR1;
        this.trashR2 = trashR2;
        this.gear = gear;
    }

    /**
     * @return backGroundColorV1
     */
    protected Color getBackGroundColorV1 () {
        return backGroundColorV1;
    }

    /**
     * @return backGroundColorV2
     */
    protected Color getBackGroundColorV2 () {
        return backGroundColorV2;
    }

    /**
     * @return backGroundColorV3
     */
    protected Color getBackGroundColorV3 () {
        return backGroundColorV3;
    }

    /**
     * @return backGroundColorV4
     */
    protected Color getBackGroundColorV4 () {
        return backGroundColorV4;
    }

    /**
     * @return foreGroundColorV1
     */
    protected Color getForeGroundColorV1 () {
        return foreGroundColorV1;
    }

    /**
     * @return foreGroundColorV2
     */
    protected Color getForeGroundColorV2 () {
        return foreGroundColorV2;
    }

    /**
     * @return foreGroundColorV3
     */
    protected Color getForeGroundColorV3 () {
        return foreGroundColorV3;
    }

    /**
     * @return backGroundColorV5
     */
    protected Color getBackGroundColorV5 () {
        return backGroundColorV5;
    }

    /**
     * @return addR1
     */
    protected ImageIcon getAddR1 () {
        return addR1;
    }

    /**
     * @return addR2
     */
    protected ImageIcon getAddR2 () {
        return addR2;
    }

    /**
     * @return data
     */
    protected ImageIcon getData () {
        return data;
    }

    /**
     * @return trashR1
     */
    protected ImageIcon getTrashR1 () {
        return trashR1;
    }

    /**
     * @return trashR2
     */
    protected ImageIcon getTrashR2 () {
        return trashR2;
    }

    /**
     * @return gear
     */
    protected ImageIcon getGear () {
        return gear;
    }
}
