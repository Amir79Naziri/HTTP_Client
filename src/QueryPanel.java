import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

public class QueryPanel extends JPanel
{
    private JTextField previewURLText;
    private KeyAndValuePanel keyAndValuePanel;
    private JButton importFromURL;

    public QueryPanel ()
    {
        super();
        setLayout (new BorderLayout ());
        setBackground (new Color (40, 38, 37, 255));
        addURLPreview ();
        keyAndValuePanel = new KeyAndValuePanel ("name", "value");
        add (fetchToJScrollPane (keyAndValuePanel),BorderLayout.CENTER);
        addImportFromPanel ();
    }

    private void addURLPreview ()
    {
        Color color = new Color (40, 38, 37, 255);
        JPanel panel = new JPanel (new FlowLayout (FlowLayout.LEFT));
        panel.setBackground (color);
        JLabel label = new JLabel ("URL PREVIEW");
        label.setFont (new Font ("Arial",Font.PLAIN,9));
        label.setForeground (Color.LIGHT_GRAY);
        label.setBackground (color);
        previewURLText = new JTextField ();
        previewURLText.setEditable (false);
        previewURLText.setPreferredSize (new Dimension (400,30));

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

        panel.add (label);
        panel.add(previewURLText);
        panel.add (copy);
        add(panel,BorderLayout.NORTH);
    }


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

    public JTextField getPreviewURLText () {
        return previewURLText;
    }

    public void addNewKeyAndValue ()
    {
        keyAndValuePanel.addNewKeyAndValue ();
    }


    private JScrollPane fetchToJScrollPane (JPanel panel)
    {
        if (panel == null)
            throw new InputMismatchException ("Input is not valid");
        JScrollPane scrollPane = new JScrollPane
                (panel,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder
                (new LineBorder (new Color (40, 37, 38, 255),1));
        scrollPane.getVerticalScrollBar ().setPreferredSize (new Dimension (10,8));
        return scrollPane;
    }

    public KeyAndValuePanel getKeyAndValuePanel () {
        return keyAndValuePanel;
    }
}
