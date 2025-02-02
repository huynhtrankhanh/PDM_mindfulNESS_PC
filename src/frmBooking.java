import javax.swing.*;

public class frmBooking extends JFrame {
    private JButton logOutButton;
    private JButton submitBookingButton;
    private JButton goBackButton;
    private JPanel panel;
    private JTextField idField;
    private JButton refreshButton;
    private JTable resultTable;
    private JLabel titleLabel;
    private JLabel insLabel;
    private JLabel idLabel;
    private JLabel availableLabel;
    private JLabel copyrightLabel;

    public frmBooking() {
        setContentPane(panel);
        setTitle("mindfulNESS - Make a booking");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ConnectSQL.showAvailableBookingQuery(resultTable);
        logOutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                logOutButton.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        try {
                            setVisible(false);
                            JOptionPane.showMessageDialog(null, "Logged out! See you again", "Success!", JOptionPane.INFORMATION_MESSAGE);
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
        goBackButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to go back?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                frmPatientDashboard.getInstance().setVisible(true);
                setVisible(false);
            }
        });
        submitBookingButton.addActionListener(e -> {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "ID field is empty", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int option = JOptionPane.showConfirmDialog(null, "Confirm booking with ID " + idField.getText() + " ?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (option == JOptionPane.YES_OPTION) {
                submitBookingButton.setEnabled(false);
                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        if (ConnectSQL.submitBooking(frmIndex.getInstance().getID(), idField.getText())) {
                            JOptionPane.showMessageDialog(null, "Booked with ID: " + idField.getText() + "! Please come on time.", "Success!", JOptionPane.INFORMATION_MESSAGE);
                            idField.setText("");
                            ConnectSQL.showAvailableBookingQuery(resultTable);
                        } else {
                            JOptionPane.showMessageDialog(null, "Healing with ID: " + idField.getText() + " is already booked! Please try again", "Warning", JOptionPane.WARNING_MESSAGE);
                            idField.setText("");
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        submitBookingButton.setEnabled(true);
                    }
                };
                worker.execute();
            }
        });
        refreshButton.addActionListener(e -> {
            refreshButton.setEnabled(false);
            SwingWorker<Void, Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    ConnectSQL.showAvailableBookingQuery(resultTable);
                    JOptionPane.showMessageDialog(null, "Refreshed available booking successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
                    return null;
                }

                @Override
                protected void done() {
                    refreshButton.setEnabled(true);
                }
            };
            worker.execute();
        });
    }
}
