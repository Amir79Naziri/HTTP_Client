package GUI;

import javax.swing.*;
import java.awt.*;

/**
 * this class build for representing Header panel in second Panel
 *
 * @author Amir Naziri
 */
public class KeyAndValueContainerPanel extends JPanel
{
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel

    /**
     * create a new Header Panel
     * @param theme theme
     */
    public KeyAndValueContainerPanel (Theme theme, Request request, int type)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        setLayout (new BorderLayout ());
        if (type == 1)
            keyAndValuePanel = new KeyAndValuePanel ("header", "value",theme,"Header",
                request,this);
        else if (type == 2)
            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,
                    "MultiPart",request,this);
        else if (type == 3)
            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,
                    "UrlEncoded",request,this);
        else if (type == 4)
            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,"Query",
                    request,this);

        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel,theme),BorderLayout.CENTER);
    }


    /**
     * @return keyAndValue panel
     */
    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }

}
