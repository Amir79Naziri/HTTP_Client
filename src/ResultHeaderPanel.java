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
    private Color colorThemeForBackGround; // color theme for backGround
    private Color colorThemeForForeGround; // color theme for foreGround

    /**
     * creates new result header panel
     */
    public ResultHeaderPanel ()
    {
        super();
        whichColor = 1;
        resultKeyAndValues = new ArrayList<> ();
        colorThemeForBackGround = new Color (40, 38, 37, 255);
        colorThemeForForeGround = Color.WHITE;
        setLayout (new BorderLayout ());
        setBackground (colorThemeForBackGround);
        addBasePanel ();

        //
        JButton copy = new JButton ("Copy On ClipBoard");
        copy.setBackground (colorThemeForBackGround);
        copy.setForeground (colorThemeForForeGround);
        JPanel copyButtonPanel = new JPanel (new FlowLayout (FlowLayout.RIGHT));
        copyButtonPanel.setBackground (colorThemeForBackGround);
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
        basePanel.setBackground (colorThemeForBackGround);
        basePanel.setLayout (new BoxLayout (basePanel,BoxLayout.Y_AXIS));
        ResultKeyAndValue fixedResultKeyAndValue =
                new ResultKeyAndValue ("NAME","VALUE",colorThemeForBackGround,
                        Color.GRAY);
        basePanel.add(fixedResultKeyAndValue);
        add(ScrollPaneAdder.fetchToJScrollPane (basePanel));
        for (int i = 0; i < 14; i++)
            addResultKeyAndValue ("sdfsadfasdf","dsfas");
        for (int i = 0; i < 14; i++)
            addResultKeyAndValue ("sAssSSS","ds234fa");

    }

    /**
     * add a new result key and value
     * @param key key
     * @param value value
     */
    public void addResultKeyAndValue (String key, String value)
    {
        if (whichColor == 1)
        {
            ResultKeyAndValue resultKeyAndValue
                    = new ResultKeyAndValue (key,value,colorThemeForBackGround,
                    colorThemeForForeGround);
            resultKeyAndValues.add (resultKeyAndValue);
            basePanel.add (resultKeyAndValue);
            whichColor = 2;
        } else
        {
            ResultKeyAndValue resultKeyAndValue
                    = new ResultKeyAndValue (key,value,Color.LIGHT_GRAY,
                    colorThemeForForeGround);
            resultKeyAndValues.add (resultKeyAndValue);
            basePanel.add (resultKeyAndValue);
            whichColor = 1;
        }
    }
}
