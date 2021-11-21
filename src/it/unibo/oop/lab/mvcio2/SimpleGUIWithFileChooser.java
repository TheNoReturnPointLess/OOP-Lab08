package it.unibo.oop.lab.mvcio2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.unibo.oop.lab.mvcio.Controller;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame(); 
    /*
     * TODO: Starting from the application in mvcio:
     * 
     * 1) Add a JTextField and a button "Browse..." on the upper part of the
     * graphical interface.
     * Suggestion: use a second JPanel with a second BorderLayout, put the panel
     * in the North of the main panel, put the text field in the center of the
     * new panel and put the button in the line_end of the new panel.
     * 
     * 2) The JTextField should be non modifiable. And, should display the
     * current selected file.
     * 
     * 3) On press, the button should open a JFileChooser. The program should
     * use the method showSaveDialog() to display the file chooser, and if the
     * result is equal to JFileChooser.APPROVE_OPTION the program should set as
     * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
     * then the program should do nothing. Otherwise, a message dialog should be
     * shown telling the user that an error has occurred (use
     * JOptionPane.showMessageDialog()).
     * 
     * 4) When in the controller a new File is set, also the graphical interface
     * must reflect such change. Suggestion: do not force the controller to
     * update the UI: in this example the UI knows when should be updated, so
     * try to keep things separated.
     */
    public SimpleGUIWithFileChooser(final Controller controller) {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        panel.add(textArea, BorderLayout.CENTER);
        final JButton button = new JButton("Save");
        panel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    controller.save(textArea.getText());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel.add(panel2, BorderLayout.NORTH);
        final JTextField textFieldPath = new JTextField(controller.getCurrentFilePath());
        textFieldPath.setEditable(false);
        panel2.add(textFieldPath);
        final JButton browse = new JButton("Browse...");
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser f = new JFileChooser("Where do you want to save?");
                switch (f.showSaveDialog(frame)) {
                case JFileChooser.APPROVE_OPTION:
                    controller.setFileAsCurrent(f.getSelectedFile());
                    textFieldPath.setText(controller.getCurrentFilePath());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, JOptionPane.ERROR_MESSAGE);
                    /* Added to remove PDM Problem -> (A switch statement does not contain a break) */
                    break;
                }
            }
        });
        panel2.add(browse, BorderLayout.LINE_END);
        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
    }

    private void display() {
        frame.setVisible(true);
    }

    public static void main(final String...args) {
        final Controller c = new Controller();
        final SimpleGUIWithFileChooser s = new SimpleGUIWithFileChooser(c);
        s.display();
    }

}
