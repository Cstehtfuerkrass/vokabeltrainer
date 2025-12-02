package vokabeltrainer.ui;

import vokabeltrainer.controller.VocabController;
import vokabeltrainer.model.Vocab;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MainWindow extends JFrame {
    private final VocabController controller;
    private final DefaultListModel<Vocab> listModel = new DefaultListModel<>();
    private final JList<Vocab> listView = new JList<>(listModel);

    public MainWindow(VocabController controller) {
        super("Vokabeltrainer");
        this.controller = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        initUI();
        loadData();
    }

    private void initUI() {
        JPanel top = new JPanel(new BorderLayout());
        listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane sp = new JScrollPane(listView);
        top.add(sp, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");
        JButton examBtn = new JButton("Exam");
        buttons.add(addBtn);
        buttons.add(deleteBtn);
        buttons.add(examBtn);

        addBtn.addActionListener(e -> onAdd());
        deleteBtn.addActionListener(e -> onDelete());
        examBtn.addActionListener(e -> onExam());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    private void loadData() {
        listModel.clear();
        List<Vocab> all = controller.list();
        for (Vocab v : all) listModel.addElement(v);
    }

    private void onAdd() {
        AddDialog dlg = new AddDialog(this);
        dlg.setVisible(true);
        if (dlg.isOk()) {
            Vocab v = new Vocab(dlg.getSource(), dlg.getTarget());
            controller.add(v);
            listModel.addElement(v);
        }
    }

    private void onDelete() {
        Vocab sel = listView.getSelectedValue();
        if (sel == null) {
            JOptionPane.showMessageDialog(this, "Select an entry to delete.");
            return;
        }
        int resp = JOptionPane.showConfirmDialog(this, "Delete '" + sel.getSource() + " - " + sel.getTarget() + "'?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            controller.delete(sel.getId());
            listModel.removeElement(sel);
        }
    }

    private void onExam() {
        ExamPanel exam = new ExamPanel(this, controller.list());
        exam.startExam();
    }
}
