package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class represents GUI.QueryPanel in second panel
 *
 * @author Amir Naziri
 */
public class QueryPanel extends KeyAndValueContainerPanel
{
    private JTextField previewURLText; // preview textField
    private JButton importFromURL; // import form button
    private Theme theme;


    /**
     * creates a GUI.QueryPanel
     * @param theme theme
     */
    public QueryPanel (Theme theme,Request request)
    {
        super(theme,request,4);
        if (theme == null)
            throw new NullPointerException ("inValid input");
        this.theme = theme;
        addURLPreview ();
        addImportFromPanel ();
    }

    /**
     * add url preview panel
     */
    private void addURLPreview ()
    {
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();
        JPanel panel = new JPanel (layout);
        panel.setBackground (theme.getBackGroundColorV4 ());
        JLabel label = new JLabel ("URL PREVIEW ");
        label.setFont (new Font ("Arial",Font.PLAIN,9));
        label.setForeground (theme.getForeGroundColorV2 ());
        label.setBackground (theme.getBackGroundColorV4 ());
        previewURLText = new JTextField ();
        previewURLText.setEditable (false);


        previewURLText.setForeground (theme.getForeGroundColorV2 ());
        previewURLText.setBackground (Color.GRAY);
        previewURLText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY,1,true),
                new EmptyBorder (1,5,1,5)));

        JButton copy = new JButton ("Copy");
        copy.setBackground (theme.getBackGroundColorV4 ());
        copy.setForeground (theme.getForeGroundColorV2 ());
        copy.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                StringSelection stringSelection =
                        new StringSelection (previewURLText.getText ());
                Toolkit.getDefaultToolkit ().getSystemClipboard ().setContents (stringSelection,
                        null);
            }
        });


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets (5,5,5,5);
        GridBagAdder.addComponent (label,0,0,1,layout,constraints,panel);

        constraints.weightx = 0.5;
        constraints.ipady = 15;
        GridBagAdder.addComponent (previewURLText,0,1,6,layout,constraints,panel);


        constraints.weightx = 0.0;
        constraints.ipady = 0;
        GridBagAdder.addComponent (copy,0,7,1,layout,constraints,panel);

        add(panel,BorderLayout.NORTH);
    }

    /**
     * add importFormPanel button
     */
    private void addImportFromPanel ()
    {

        JPanel panel = new JPanel (new FlowLayout (FlowLayout.RIGHT));
        panel.setBackground (theme.getBackGroundColorV4 ());

        importFromURL = new JButton ("Import From URL");
        importFromURL.setForeground (theme.getForeGroundColorV2 ());
        importFromURL.setBackground (theme.getBackGroundColorV4 ());
        panel.add (importFromURL);
        add (panel,BorderLayout.SOUTH);
    }

    /**
     * @return preview URL text
     */
    public JTextField getPreviewURLText () {
        return previewURLText;
    }
}
