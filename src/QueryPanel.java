import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class represents QueryPanel in second panel
 *
 * @author Amir Naziri
 */
public class QueryPanel extends JPanel
{
    private JTextField previewURLText; // preview textField
    private KeyAndValuePanel keyAndValuePanel; // keyAndValue panel
    private JButton importFromURL; // import form button

    /**
     * creates a QueryPanel
     */
    public QueryPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        addURLPreview ();
        keyAndValuePanel = new KeyAndValuePanel ("name", "value");
        add (ScrollPaneAdder.fetchToJScrollPane (keyAndValuePanel),BorderLayout.CENTER);
        addImportFromPanel ();
    }

    /**
     * add url preview panel
     */
    private void addURLPreview ()
    {
        Color color = new Color (40, 38, 37, 255);
        GridBagConstraints constraints = new GridBagConstraints ();
        GridBagLayout layout = new GridBagLayout ();
        JPanel panel = new JPanel (layout);
        panel.setBackground (color);
        JLabel label = new JLabel ("URL PREVIEW ");
        label.setFont (new Font ("Arial",Font.PLAIN,9));
        label.setForeground (Color.LIGHT_GRAY);
        label.setBackground (color);
        previewURLText = new JTextField ();
        previewURLText.setEditable (false);


        previewURLText.setForeground (Color.LIGHT_GRAY);
        previewURLText.setBackground (new Color (50, 48, 47, 255));
        previewURLText.setBorder (BorderFactory.createCompoundBorder (
                new LineBorder (Color.GRAY.darker (),1,true),
                new EmptyBorder (1,5,1,5)));

        JButton copy = new JButton ("Copy");
        copy.setBackground (color);
        copy.setForeground (Color.LIGHT_GRAY);
        copy.addActionListener (new ActionListener () {
            @Override
            public void actionPerformed (ActionEvent e) {
                StringSelection stringSelection =
                        new StringSelection (previewURLText.getText ());
                Toolkit.getDefaultToolkit ().getSystemClipboard ().setContents (stringSelection,
                        null);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets (5,5,5,5);
        layout.setConstraints (label,constraints);
        panel.add (label);

        constraints.gridx = 1;
        constraints.gridwidth = 6;
        constraints.weightx = 0.5;
        constraints.ipady = 15;
        layout.setConstraints (previewURLText,constraints);
        panel.add(previewURLText);

        constraints.gridx = 7;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        constraints.ipady = 0;
        layout.setConstraints (copy,constraints);
        panel.add (copy);


        add(panel,BorderLayout.NORTH);
    }

    /**
     * add importFormPanel button
     */
    private void addImportFromPanel ()
    {
        Color color = new Color (40, 38, 37, 255);
        JPanel panel = new JPanel (new FlowLayout (FlowLayout.RIGHT));
        panel.setBackground (color);

        importFromURL = new JButton ("Import From URL");
        importFromURL.setForeground (Color.LIGHT_GRAY);
        importFromURL.setBackground (color);
        panel.add (importFromURL);
        add (panel,BorderLayout.SOUTH);
    }

    /**
     * @return preview URL text
     */
    public JTextField getPreviewURLText () {
        return previewURLText;
    }

    /**
     * add new key And Value to Query
     */
    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ();
    }

    /**
     * @return keyAndValuePanel
     */
    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }
}
