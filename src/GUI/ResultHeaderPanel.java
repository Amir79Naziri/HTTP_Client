package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * this class represents result Header panel in thirdPanel
 *
 * @author Amir Naziri
 */
public class ResultHeaderPanel extends JPanel
{
    private JPanel basePanel; // base panel for result key and values
    private ArrayList<ResultKeyAndValue> resultKeyAndValues; // list of key and values
    private int whichColor; // 1 means no color         2 means gray
    private Theme theme;
    /**
     * creates new result header panel
     * @param theme theme
     */
    public ResultHeaderPanel (Theme theme)
    {
        super();
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        whichColor = 1;
        resultKeyAndValues = new ArrayList<> ();
        setLayout (new BorderLayout ());
        setBackground (theme.getBackGroundColorV4 ());
        addBasePanel ();

        //
        JButton copy = new JButton ("Copy On ClipBoard");
        copy.setBackground (theme.getBackGroundColorV4 ());
        copy.setForeground (theme.getForeGroundColorV2 ());
        JPanel copyButtonPanel = new JPanel (new FlowLayout (FlowLayout.RIGHT));
        copyButtonPanel.setBackground (theme.getBackGroundColorV4 ());
        copyButtonPanel.add (copy);
        add(copyButtonPanel,BorderLayout.SOUTH);
        copy.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                StringBuilder stringBuilder = new StringBuilder ();
                for (ResultKeyAndValue resultKeyAndValue : resultKeyAndValues)
                {
                    stringBuilder.append (resultKeyAndValue.getKey ().getText ());
                    stringBuilder.append (": ");
                    stringBuilder.append (resultKeyAndValue.getValue ().getText ());
                    stringBuilder.append ('\n');
                }
                StringSelection stringSelection = new StringSelection (stringBuilder.toString ());
                Toolkit.getDefaultToolkit ().getSystemClipboard ().setContents (stringSelection,
                        null);
            }
        });

    }

    /**
     * @return list of result key and values panel
     */
    public ArrayList<ResultKeyAndValue> getResultKeyAndValues () {
        return resultKeyAndValues;
    }

    /**
     * creates base panel for result key and values
     */
    private void addBasePanel ()
    {
        basePanel = new JPanel ();
        basePanel.setBackground (theme.getBackGroundColorV4 ());
        basePanel.setLayout (new BoxLayout (basePanel,BoxLayout.Y_AXIS));
        ResultKeyAndValue fixedResultKeyAndValue =
                new ResultKeyAndValue ("NAME","VALUE",theme.getBackGroundColorV4 (),
                        theme.getForeGroundColorV2 ());
        basePanel.add(fixedResultKeyAndValue);
        add(ScrollPaneAdder.fetchToJScrollPane (basePanel,theme));




    }

    /**
     * add a new result key and value
     * @param key key
     * @param value value
     */
    public void addResultKeyAndValue (String key, String value)
    {
        if (key == null || value == null)
            throw new NullPointerException ("inValid input");
        if (whichColor == 1)
        {
            ResultKeyAndValue resultKeyAndValue
                    = new ResultKeyAndValue (key,value,theme.getBackGroundColorV4 (),
                    theme.getForeGroundColorV2 ());
            resultKeyAndValues.add (resultKeyAndValue);
            basePanel.add (resultKeyAndValue);
            whichColor = 2;
        } else
        {
            ResultKeyAndValue resultKeyAndValue
                    = new ResultKeyAndValue (key,value,theme.getBackGroundColorV5 (),
                    theme.getForeGroundColorV2 ());
            resultKeyAndValues.add (resultKeyAndValue);
            basePanel.add (resultKeyAndValue);
            whichColor = 1;
        }
    }

    public void clear ()
    {
        for (ResultKeyAndValue keyAndValue : resultKeyAndValues)
            keyAndValue.setVisible (false);
        resultKeyAndValues.clear ();
    }
}
