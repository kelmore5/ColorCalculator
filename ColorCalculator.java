package kelmore5.java.morrison.color_calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * <pre class="doc_header">
 * ColorCalculator is a simple class for displaying a color in a JFrame.
 * Sliders at the top allow the user to change the red green blue values of
 * the panel, which is then immediately displayed.
 * </pre>
 *
 * @author kelmore5
 * @custom.date Fall 2011
 */
public class ColorCalculator extends JFrame implements Runnable {
    /**
     * Integer array for the different RGB values
     */
    private int[] colors;
    /**
     * String array for the different slider names (Red, Green, Blue)
     */
    private String[] colorStrings;
    /**
     * Main panel for the JFrame
     */
    private JPanel mainPanel;

    /**
     * Instantiates a ColorCalculator
     */
    private ColorCalculator()
    {
        //Set title, initialize color and color string arrays, and set default values
        //Default values: White and FF
        //Then, set panel and background color of frame
        super("Color Calculator");
        colors = new int[3];
        colorStrings = new String[3];
        for(int k = 0; k < 3; k++)
        {
            colors[k] = 255;
            colorStrings[k] = "FF";
        }
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
    }

    public void run()
    {
        setLayout(new BorderLayout());
        setSize(500,500);
        getContentPane().add(getSliderPanel(), BorderLayout.NORTH);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     *  Creates the slider panel to control the color of the JFrame
     * @return The JPanel for the frame
     */
    private JPanel getSliderPanel()
    {
        //Create panel with grid layout
        final JPanel panel = new JPanel(new GridLayout(1,7));

        //Create sliders - One for each RGB
        final JSlider js1 = new JSlider(0, 255, 255);
        final JSlider js2 = new JSlider(0, 255, 255);
        final JSlider js3 = new JSlider(0, 255, 255);
        //Create text field to allow user to enter hex representation of color
        final JTextField text = new JTextField("0xFFFFFF");

        //Create listeners to change color of panel upon slider change
        js1.addChangeListener(e -> changeColor(js1, text, 0));
        js2.addChangeListener(e -> changeColor(js2, text, 1));
        js3.addChangeListener(e -> changeColor(js3, text, 2));

        //Create listener for text field to update color of panel upon change
        text.addActionListener(e -> {
            String hex = text.getText();
            hex = hex.toUpperCase();
            if(hex.startsWith("0x") || hex.startsWith("0X")) {
                hex = hex.substring(2);
            }
            while(hex.length() < 6) {
                hex = "0" + hex;
            }
            try {
                //Try parsing each hex substring presented in the textfield
                //One for each RGB value
                //Then set color and repaint panel
                colors[0] = Integer.parseInt(hex.substring(0, 2), 16);
                js1.setValue(colors[0]);
                colors[1] = Integer.parseInt(hex.substring(2, 4), 16);
                js2.setValue(colors[1]);
                colors[2] = Integer.parseInt(hex.substring(4), 16);
                js3.setValue(colors[2]);
                mainPanel.setBackground(new Color(colors[0], colors[1], colors[2]));
                mainPanel.repaint();
                text.setText("0x" + hex);
            }
            catch(Exception ex) {
                text.setText("Error");
            }
        });

        //Add the labels for each slider (Red, Green, Blue)
        panel.add(new JLabel("Red"));
        panel.add(js1);
        panel.add(new JLabel("Green"));
        panel.add(js2);
        panel.add(new JLabel("Blue"));
        panel.add(js3);
        panel.add(text);
        return panel;
    }

    /**
     * Function to change the color of the panel
     * @param js the slider for input
     * @param text the textfield to update
     * @param color the integer representation of the color being changed to
     */
    private void changeColor(JSlider js, JTextField text, int color) {
        //Set color array to value of slider
        colors[color] = js.getValue();

        //Set string array to value of hex representation
        colorStrings[color] = Integer.toHexString(colors[color]);
        if(colorStrings[color].length() == 1) {
            colorStrings[color] = "0" + colorStrings[color];
        }
        //Set background color and repaint
        mainPanel.setBackground(new Color(colors[0], colors[1], colors[2]));
        mainPanel.repaint();

        //Create textstring and update JTextField with hex representation
        String textString = colorStrings[0] + colorStrings[1] + colorStrings[2];
        textString = textString.toUpperCase();
        text.setText("0x" + textString);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args)
    {
        ColorCalculator cc = new ColorCalculator();
        javax.swing.SwingUtilities.invokeLater(cc);
    }
}
