import javax.swing.*;

public class frmSpecialistDashboard extends JFrame {
    private static frmSpecialistDashboard instance;
    private JButton logOutButton;
    private JTextArea recentArea;
    private JButton postButton;
    private JLabel titleLabel;
    private JPanel panel;
    private JLabel insLabel;
    private JLabel upcomingLabel;
    private JScrollPane recentPane;
    private JTextField placeField;
    private JTextField feeField;
    private JTextField dateField;
    private JTextField descField;
    private JTextField extraField;
    private JLabel fillLabel;
    private JLabel placeLabel;
    private JLabel dateLabel;
    private JLabel feeLabel;
    private JLabel descLabel;
    private JLabel noteLabel;
    private JLabel copyrightLabel;
    private JButton resetPwdButton;

    private frmSpecialistDashboard() {
        setContentPane(panel);
        setTitle("mindfulNESS - Dashboard");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        logOutButton.addActionListener(
                e -> {
                    int option =
                            JOptionPane.showConfirmDialog(
                                    null,
                                    "Are you sure you want to log out?",
                                    "Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        logOutButton.setEnabled(false);
                        SwingWorker<Void, Void> worker =
                                new SwingWorker<>() {
                                    @Override
                                    protected Void doInBackground() {
                                        try {
                                            setVisible(false);
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Logged out! See you again",
                                                    "Success!",
                                                    JOptionPane.WARNING_MESSAGE);
                                            Thread.sleep(1000);
                                            System.exit(0);
                                        } catch (InterruptedException ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        logOutButton.setEnabled(true);
                                    }
                                };
                        worker.execute();
                    }
                });
        postButton.addActionListener(
                e -> {
                    postButton.setEnabled(false);
                    if (placeField.getText().isEmpty()
                            || dateField.getText().isEmpty()
                            || feeField.getText().isEmpty()
                            || descField.getText().isEmpty()
                            || extraField.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(
                                null, "Field(s) are empty!", "Warning", JOptionPane.WARNING_MESSAGE);
                        postButton.setEnabled(true);
                        return;
                    }
                    int option =
                            JOptionPane.showConfirmDialog(
                                    null,
                                    "Confirm posting healing information?",
                                    "Confirmation",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.QUESTION_MESSAGE);
                    if (option == JOptionPane.YES_OPTION) {
                        SwingWorker<Void, Void> worker =
                                new SwingWorker<>() {
                                    @Override
                                    protected Void doInBackground() {
                                        if (ConnectSQL.submitHealing(
                                                frmIndex.getInstance().getID(),
                                                placeField.getText(),
                                                dateField.getText(),
                                                feeField.getText(),
                                                descField.getText(),
                                                extraField.getText())) {
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Healing information posted! Please check the nearby box for confirmation",
                                                    "Warning",
                                                    JOptionPane.WARNING_MESSAGE);
                                            recentArea.selectAll();
                                            recentArea.replaceSelection("");
                                            recentArea.setText(ConnectSQL.showSpecialistBooking(frmIndex.getInstance().getID()));
                                        } else {
                                            JOptionPane.showMessageDialog(
                                                    null,
                                                    "Something went wrong! Please try again",
                                                    "Warning",
                                                    JOptionPane.WARNING_MESSAGE);
                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void done() {
                                        postButton.setEnabled(true);
                                    }
                                };
                        worker.execute();
                    }
                });
        resetPwdButton.addActionListener(
                e -> {
                });
    }

    public static synchronized frmSpecialistDashboard getInstance() {
        if (instance == null) {
            instance = new frmSpecialistDashboard();
        }
        return instance;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        recentArea.selectAll();
        recentArea.replaceSelection("");
        recentArea.setText(ConnectSQL.showSpecialistBooking(frmIndex.getInstance().getID()));
        recentArea.setEditable(false);
    }
}
