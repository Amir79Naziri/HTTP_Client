package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * this class represents a key and value panel
 *
 * @author Amir Naziri
 */
public class KeyAndValuePanel extends JPanel
{
    private ArrayList<KeyAndValue> keyAndValues; // list of key and values
    private KeyAndValue fixedKeyAndValue; // fixed key and values
    private JPopupMenu popupMenu; // pop up menu for setting
    private JMenuItem toggleDescription; // toggle description
    private JMenuItem deleteAll; // delete all
    private String key; // key
    private String value; // value
    private Theme theme; // theme
    private String type;
    private RequestGui requestGui;
    private JPanel owner;



    /**
     * creates a new keyAndValue panel
     * @param key key
     * @param value value
     * @param theme theme
     * @param owner owner panel
     * @param requestGui requestGui
     * @param type type of : header or Query or Bodies
     */
    protected KeyAndValuePanel (String key, String value, Theme theme,
                             String type, RequestGui requestGui,
                             JPanel owner)
    {
        super();
        if (key == null || value == null || theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        keyAndValues = new ArrayList<> ();
        this.key = key;
        this.value = value;
        this.type = type;
        this.owner = owner;
        this.requestGui = requestGui;
        setLayout (new BoxLayout (this,BoxLayout.Y_AXIS));
        setBackground (theme.getBackGroundColorV4 ());
        fixedKeyAndValue = new KeyAndValue ("New " + key, "New " + value,
                false,false,theme,this);
        add(fixedKeyAndValue);
        ComponentHandler componentHandler = new ComponentHandler ();
        fixedKeyAndValue.getKey ().addMouseListener (componentHandler);
        fixedKeyAndValue.getValue ().addMouseListener (componentHandler);
        fixedKeyAndValue.getSettings ().addMouseListener (componentHandler);
        fixedKeyAndValue.getDescribe ().addMouseListener (componentHandler);

        deleteAll = new JMenuItem ("Delete All Items");
        deleteAll.addActionListener (componentHandler);
        toggleDescription = new JMenuItem ("Toggle Description");
        toggleDescription.addActionListener (componentHandler);
        popupMenu = new JPopupMenu ();
        popupMenu.add (deleteAll);
        popupMenu.add (toggleDescription);
    }

    /**
     * @return owner panel
     */
    protected JPanel getOwner () {
        return owner;
    }

    /**
     * @return keyAndValue list
     */
    protected ArrayList<KeyAndValue> getKeyAndValues () {
        return keyAndValues;
    }

    /**
     * add a new key and value
     */
    private void addDefaultNewKeyAndValue ()
    {
        configureNewKeyAndValue (key, value);
    }

    /**
     * add a new key and value
     */
    private void addNewKeyAndValue (String key, String value)
    {
        configureNewKeyAndValue (key, value);
    }

    /**
     * configures first setup for a new Key and value
     * @param key key
     * @param value value
     */
    private void configureNewKeyAndValue (String key, String value) {
        KeyAndValue keyAndValue = new KeyAndValue (key, value,true,
                fixedKeyAndValue.isPanelDescVisible (),theme,this);
        getKeyAndValues ().add (keyAndValue);
        add (keyAndValue,keyAndValues.size () - 1);
        keyAndValue.setVisible (false);
        keyAndValue.setVisible (true);
    }

    /**
     * toggle description for keyAndValues
     */
    private void toggleAllDescription ()
    {
        for (KeyAndValue keyAndValue : keyAndValues)
            keyAndValue.toggleDescription ();
        fixedKeyAndValue.toggleDescription ();
    }

    /**
     * delete all keyAndValues
     */
    protected void deleteAll ()
    {
        for (KeyAndValue keyAndValue : keyAndValues)
        {
            keyAndValue.setVisible (false);
            keyAndValue.delete ();
        }
        keyAndValues.clear ();
    }

    /**
     * load requestGui data on GUI
     * @param data requestGui data
     */
    protected void properBack (HashMap<String,String> data)
    {
        if (data == null)
            return;
        for (String key : data.keySet ())
            addNewKeyAndValue (key,data.get (key));
    }

    /**
     * load GUI on requestGui
     */
    protected void properData ()
    {
        switch (type)
        {
            case "Query" :
                updateList ();
                requestGui.getClientRequest ().clearQuery ();
                for (KeyAndValue keyAndValue : getKeyAndValues ())
                    if (keyAndValue.isActive ())
                    {
                        requestGui.getClientRequest ().addQuery (keyAndValue.getKey ().getText (),
                                keyAndValue.getValue ().getText ());
                    }

                break;
            case "Header" :
                updateList ();
                requestGui.getClientRequest ().clearCustomHeaders ();
                for (KeyAndValue keyAndValue : getKeyAndValues ())
                    if (keyAndValue.isActive ())
                    {
                        requestGui.getClientRequest ().addCustomHeader (keyAndValue.getKey ().getText (),
                                keyAndValue.getValue ().getText ());
                    }
                break;
            case "MultiPart" :
                updateList ();
                requestGui.getClientRequest ().clearBody ();
                for (KeyAndValue keyAndValue : getKeyAndValues ())
                    if (keyAndValue.isActive ())
                    {
                        requestGui.getClientRequest ().addFormUrlData (keyAndValue.getKey ().getText (),
                                keyAndValue.getValue ().getText ());
                    }
                break;
            case "UrlEncoded" :
                updateList ();
                requestGui.getClientRequest ().clearBody ();
                for (KeyAndValue keyAndValue : getKeyAndValues ())
                    if (keyAndValue.isActive ())
                    {
                        requestGui.getClientRequest ().addFormUrlDataEncoded
                                (keyAndValue.getKey ().getText (),
                                        keyAndValue.getValue ().getText ());
                    }
                break;
        }
    }

    /**
     * update list : if a requestGui deleted it will remove it from list
     */
    private void updateList ()
    {
        keyAndValues.removeIf (keyAndValue -> keyAndValue.isDeleted ());
    }

    /**
     * @return requestGui
     */
    protected RequestGui getRequestGui () {
        return requestGui;
    }

    /**
     * this class handles components in this panel
     */
    private class ComponentHandler extends MouseAdapter
            implements ActionListener
    {
        @Override
        public void actionPerformed (ActionEvent e) {
            if (e.getSource () == deleteAll)
            {
                deleteAll ();
                properData ();
                if (getOwner () instanceof QueryPanel)
                {
                    QueryPanel queryPanel = (QueryPanel) getOwner ();
                    queryPanel.getPreviewURLText ().
                            setText (getRequestGui ().getClientRequest ().getUrl ()
                                    + getRequestGui ().getClientRequest ().getQueryDataString ());
                }
            }
            else if (e.getSource () == toggleDescription)
                toggleAllDescription ();
        }

        @Override
        public void mousePressed (MouseEvent e) {
            if (e.getComponent () == fixedKeyAndValue.getSettings())
                popupMenu.show (e.getComponent (),e.getX (),e.getY ());
            else if (e.getComponent () == fixedKeyAndValue.getValue () ||
            e.getComponent () == fixedKeyAndValue.getKey () || e.getComponent () ==
            fixedKeyAndValue.getDescribe ())
            {
                addDefaultNewKeyAndValue ();
                properData ();
                if (getOwner () instanceof QueryPanel)
                {
                    QueryPanel queryPanel = (QueryPanel) getOwner ();
                    queryPanel.getPreviewURLText ().
                            setText (getRequestGui ().getClientRequest ().getUrl ()
                                    + getRequestGui ().getClientRequest ().getQueryDataString ());
                }
            }
        }
    }
}
