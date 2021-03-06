package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * this class build for representing Header, bodies, ... panel in second Panel
 *
 * @author Amir Naziri
 */
public class KeyAndValueContainerPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new Header Panel
     * @param theme theme
     * @param type type
     * @param requestGui requestGui
     */
    protected KeyAndValueContainerPanel (Theme theme, RequestGui requestGui, int type)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        if (type == 1)
            keyAndValuePanel = new KeyAndValuePanel ("header", "value",theme,"Header",
                    requestGui,this);
        else if (type == 2)
            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,
                    "MultiPart", requestGui,this);
        else if (type == 3)
            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,
                    "UrlEncoded", requestGui,this);
        else if (type == 4)
            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,"Query",
                    requestGui,this);

        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel,theme),BorderLayout.CENTER);
    }


    /**
     * @return keyAndValue panel
     */
    protected KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }

}
