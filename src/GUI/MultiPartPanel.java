//package GUI;
//
//import javax.swing.*;
//import java.awt.*;
//
///**
// * this class represents GUI.MultiPartPanel
// *
// * @author Amir Naziri
// */
//public class MultiPartPanel extends JPanel
//{
//    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel
//    private Request request;
//
//    /**
//     * create a new GUI.MultiPartPanel
//     * @param theme theme
//     */
//    public MultiPartPanel (Theme theme, Request request, int type)
//    {
//        super();
//        if (theme == null)
//            throw new NullPointerException ("inValid input");
//        setLayout (new BorderLayout ());
//        if (type == 1)
//            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,
//                    "MultiPart",request);
//        else
//            keyAndValuePanel = new KeyAndValuePanel ("name", "value",theme,
//                    "UrlEncoded",request);
//        add(ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel,theme),BorderLayout.CENTER);
//    }
//
//    /**
//     * add new keyAndValue in GUI.MultiPartPanel
//     */
//    public void addNewKeyAndValue ()
//    {
//        keyAndValuePanel.addDefaultNewKeyAndValue ();
//    }
//
//    /**
//     * @return GUI.MultiPartPanel
//     */
//    public KeyAndValuePanel getKeyAndValuePanel () {
//        return keyAndValuePanel;
//    }
//}
