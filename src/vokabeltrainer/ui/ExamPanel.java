package vokabeltrainer.ui;

import vokabeltrainer.model.Vocab;

import javax.swing.*;
import java.awt.Window;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class ExamPanel {
    private final Window parent;
    private final List<Vocab> pool;

    public ExamPanel(Window parent, List<Vocab> pool) {
        this.parent = parent;
        this.pool = new ArrayList<>(pool);
    }

    public void startExam() {
        if (pool.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No vocab entries available.");
            return;
        }
        Collections.shuffle(pool);
        int total = Math.min(10, pool.size());
        int correct = 0;
        for (int i = 0; i < total; i++) {
            Vocab v = pool.get(i);
            String ans = JOptionPane.showInputDialog(parent, "Translate: " + v.getSource());
            if (ans == null) { // cancel
                break;
            }
            if (ans.trim().equalsIgnoreCase(v.getTarget().trim())) correct++;
        }
        JOptionPane.showMessageDialog(parent, "Score: " + correct + " / " + total);
    }
}
