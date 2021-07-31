package io.github.geniot.elex;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Observable;
import java.util.Observer;

public class MainPanel implements Observer {
    public static final ImageIcon CONNECT_ICON = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/connect.png"));
    public static final ImageIcon DISCONNECT_ICON = new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("images/disconnect.png"));

    public JPanel contentPanel;
    public JButton openButton;
    public JTextArea textArea;
    private JToggleButton pinButton;
    public JToggleButton connectButton;
    private JButton clearButton;
    private JButton helpButton;

    private ElexApplication frame;
    private ElexServer server;

    private static final String UNPIN_MSG = "Unpin this window from top";
    private static final String PIN_MSG = "Pin this window on top";
    private static final String OPEN_MSG = "Open Elex in the default browser";
    private static final String STOP_MSG = "Stop the server";
    private static final String START_MSG = "Start the server";

    @Override
    public void update(Observable o, Object arg) {
        if (server.status.equals(ElexServer.Prop.STARTED)) {
            connectButton.setSelected(true);
            connectButton.setIcon(CONNECT_ICON);
            connectButton.setToolTipText(STOP_MSG);
        } else {
            connectButton.setSelected(false);
            connectButton.setIcon(DISCONNECT_ICON);
            connectButton.setToolTipText(START_MSG);
        }
    }

    enum Prop {
        HOST, PORT, PIN
    }

    public MainPanel(ElexApplication f, ElexServer s) {
        this.frame = f;
        this.server = s;

        int margin = 5;
        textArea.setMargin(new Insets(margin, margin, margin, margin));

        openButton.setToolTipText(OPEN_MSG);
        openButton.addActionListener(e -> {
            try {
                String host = ElexPreferences.get(Prop.HOST.name(), "localhost");
                int port = ElexPreferences.getInt(Prop.PORT.name(), 8000);

                Desktop.getDesktop().browse(URI.create("http://" + host + ":" + port));
            } catch (IOException ioException) {
                Logger.getInstance().log(ioException);
            }
        });

        pinButton.setSelected(ElexPreferences.getBoolean(Prop.PIN.name(), false));
        frame.setAlwaysOnTop(pinButton.isSelected());


        pinButton.setToolTipText(pinButton.isSelected() ? UNPIN_MSG : PIN_MSG);
        pinButton.addActionListener(e -> {
            ElexPreferences.pubBoolean(Prop.PIN.name(), pinButton.isSelected());
            frame.setAlwaysOnTop(pinButton.isSelected());
            pinButton.setToolTipText(pinButton.isSelected() ? UNPIN_MSG : PIN_MSG);
        });

        connectButton.addActionListener(e -> {
            if (connectButton.isSelected()) {
                server.start();
            } else {
                server.stop();
            }
        });

        clearButton.addActionListener(e -> {
            textArea.setText("");
        });

        helpButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(URI.create("https://github.com/geniot/elex"));
            } catch (IOException ioException) {
                Logger.getInstance().log(ioException);
            }
        });

    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        contentPanel.add(panel1, BorderLayout.NORTH);
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        openButton = new JButton();
        openButton.setFocusPainted(false);
        openButton.setFocusable(false);
        openButton.setIcon(new ImageIcon(getClass().getResource("/images/world_go.png")));
        openButton.setMaximumSize(new Dimension(40, 40));
        openButton.setMinimumSize(new Dimension(40, 40));
        openButton.setPreferredSize(new Dimension(40, 40));
        openButton.setText("");
        panel1.add(openButton);
        pinButton = new JToggleButton();
        pinButton.setFocusPainted(false);
        pinButton.setFocusable(false);
        pinButton.setIcon(new ImageIcon(getClass().getResource("/images/location_pin.png")));
        pinButton.setMaximumSize(new Dimension(40, 40));
        pinButton.setMinimumSize(new Dimension(40, 40));
        pinButton.setPreferredSize(new Dimension(40, 40));
        pinButton.setText("");
        panel1.add(pinButton);
        connectButton = new JToggleButton();
        connectButton.setFocusPainted(false);
        connectButton.setFocusable(false);
        connectButton.setIcon(new ImageIcon(getClass().getResource("/images/disconnect.png")));
        connectButton.setMaximumSize(new Dimension(40, 40));
        connectButton.setMinimumSize(new Dimension(40, 40));
        connectButton.setPreferredSize(new Dimension(40, 40));
        connectButton.setText("");
        panel1.add(connectButton);
        clearButton = new JButton();
        clearButton.setFocusPainted(false);
        clearButton.setFocusable(false);
        clearButton.setIcon(new ImageIcon(getClass().getResource("/images/document_empty.png")));
        clearButton.setMaximumSize(new Dimension(40, 40));
        clearButton.setMinimumSize(new Dimension(40, 40));
        clearButton.setPreferredSize(new Dimension(40, 40));
        clearButton.setText("");
        panel1.add(clearButton);
        helpButton = new JButton();
        helpButton.setFocusPainted(false);
        helpButton.setFocusable(false);
        helpButton.setIcon(new ImageIcon(getClass().getResource("/images/help.png")));
        helpButton.setMaximumSize(new Dimension(40, 40));
        helpButton.setMinimumSize(new Dimension(40, 40));
        helpButton.setPreferredSize(new Dimension(40, 40));
        helpButton.setText("");
        panel1.add(helpButton);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout(0, 0));
        contentPanel.add(panel2, BorderLayout.CENTER);
        panel2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel2.add(scrollPane1, BorderLayout.CENTER);
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane1.setViewportView(textArea);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPanel;
    }

}
