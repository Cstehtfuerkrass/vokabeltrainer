package vokabeltrainer.ui;

import javax.swing.*;
import java.awt.*;

public class AddDialog extends JDialog {
    private boolean ok = false;
    private final JTextField sourceField = new JTextField(20);
    private final JTextField targetField = new JTextField(20);

    public AddDialog(Window parent) {
        super(parent, "Add Vocab", ModalityType.APPLICATION_MODAL);
        init();
        pack();
        setLocationRelativeTo(parent);
    }

    private void init() {
        JPanel p = new JPanel(new GridLayout(3, 2, 6, 6));
        p.add(new JLabel("Source:"));
        p.add(sourceField);
        p.add(new JLabel("Target:"));
        p.add(targetField);

        JButton okBtn = new JButton("OK");
        JButton cancelBtn = new JButton("Cancel");
        okBtn.addActionListener(e -> {
            if (sourceField.getText().trim().isEmpty() || targetField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill both fields.");
                return;
            }
            ok = true;
            setVisible(false);
            dispose();
        });
        cancelBtn.addActionListener(e -> {
            ok = false;
            setVisible(false);
            dispose();
        });

        JPanel btns = new JPanel();
        btns.add(okBtn);
        btns.add(cancelBtn);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(btns, BorderLayout.SOUTH);
    }

    public boolean isOk() {
        return ok;
    }

    public String getSource() {
        return sourceField.getText().trim();
    }

    public String getTarget() {
        return targetField.getText().trim();
    }
}
