import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Frontend {

    static class Vocab {
        String de;
        String en;

        Vocab(String de, String en) {
            this.de = de;
            this.en = en;
        }

        @Override
        public String toString() {
            return de + " — " + en;
        }
    }

    private final List<Vocab> vocabList = new ArrayList<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final Random rng = new Random();

    private JFrame frame;
    private JTextField deField;
    private JTextField enField;
    private JList<String> vocabJList;
    private JLabel quizLabel;
    private JTextField answerField;
    private Vocab currentQuiz;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Frontend app = new Frontend();
                app.buildAndShow();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Fehler beim Start: " + e.getMessage());
            }
        });
    }

    private void buildAndShow() {
        frame = new JFrame("Vokabeltrainer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 480);
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        root.add(buildEntryPanel(), BorderLayout.NORTH);
        root.add(buildCenterPanel(), BorderLayout.CENTER);
        root.add(buildBottomPanel(), BorderLayout.SOUTH);

        frame.setContentPane(root);
        frame.setVisible(true);
    }

    private JPanel buildEntryPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);

        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Deutsch:"), c);
        c.gridx = 1; c.gridy = 0; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        deField = new JTextField();
        p.add(deField, c);

        c.gridx = 0; c.gridy = 1; c.fill = GridBagConstraints.NONE; c.weightx = 0; c.anchor = GridBagConstraints.EAST;
        p.add(new JLabel("Fremdsprache:"), c);
        c.gridx = 1; c.gridy = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        enField = new JTextField();
        p.add(enField, c);

        JButton addBtn = new JButton("Hinzufügen");
        addBtn.addActionListener(e -> addVocab());
        c.gridx = 2; c.gridy = 0; c.gridheight = 2; c.fill = GridBagConstraints.VERTICAL; c.weightx = 0;
        p.add(addBtn, c);

        return p;
    }

    private JPanel buildCenterPanel() {
        JPanel p = new JPanel(new GridLayout(1, 2, 10, 10));

        // Left: vocab list
        JPanel left = new JPanel(new BorderLayout(4,4));
        left.setBorder(BorderFactory.createTitledBorder("Vokabeln"));
        vocabJList = new JList<>(listModel);
        vocabJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        left.add(new JScrollPane(vocabJList), BorderLayout.CENTER);

        JPanel leftBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton removeBtn = new JButton("Entfernen");
        removeBtn.addActionListener(e -> removeSelected());
        leftBtns.add(removeBtn);
        JButton loadBtn = new JButton("Laden");
        loadBtn.addActionListener(e -> loadFromFile());
        leftBtns.add(loadBtn);
        JButton saveBtn = new JButton("Speichern");
        saveBtn.addActionListener(e -> saveToFile());
        leftBtns.add(saveBtn);
        left.add(leftBtns, BorderLayout.SOUTH);

        // Right: quiz
        JPanel right = new JPanel(new BorderLayout(4,4));
        right.setBorder(BorderFactory.createTitledBorder("Übung"));
        quizLabel = new JLabel("Klicke 'Start Quiz' um zu beginnen.", SwingConstants.CENTER);
        quizLabel.setFont(quizLabel.getFont().deriveFont(16f));
        right.add(quizLabel, BorderLayout.NORTH);

        answerField = new JTextField();
        right.add(answerField, BorderLayout.CENTER);

        JPanel quizBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startQuiz = new JButton("Start Quiz");
        startQuiz.addActionListener(e -> startQuiz());
        quizBtns.add(startQuiz);
        JButton checkBtn = new JButton("Überprüfen");
        checkBtn.addActionListener(e -> checkAnswer());
        quizBtns.add(checkBtn);
        right.add(quizBtns, BorderLayout.SOUTH);

        p.add(left);
        p.add(right);
        return p;
    }

    private JPanel buildBottomPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel info = new JLabel("Datei: ressources/vocab.json | Keine externen Abhängigkeiten | Swing");
        p.add(info);
        return p;
    }

    private void addVocab() {
        String de = deField.getText().trim();
        String en = enField.getText().trim();
        if (de.isEmpty() || en.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Beide Felder müssen gefüllt sein.");
            return;
        }
        Vocab v = new Vocab(de, en);
        vocabList.add(v);
        listModel.addElement(v.toString());
        deField.setText("");
        enField.setText("");
    }

    private void removeSelected() {
        int idx = vocabJList.getSelectedIndex();
        if (idx >= 0) {
            vocabList.remove(idx);
            listModel.remove(idx);
        }
    }

    private void startQuiz() {
        if (vocabList.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Keine Vokabeln verfügbar. Bitte zuerst Vokabeln hinzufügen oder laden.");
            return;
        }
        currentQuiz = vocabList.get(rng.nextInt(vocabList.size()));
        quizLabel.setText("Übersetze: " + currentQuiz.de);
        answerField.setText("");
        answerField.requestFocusInWindow();
    }

    private void checkAnswer() {
        if (currentQuiz == null) {
            JOptionPane.showMessageDialog(frame, "Starte zuerst das Quiz.");
            return;
        }
        String ans = answerField.getText().trim();
        if (ans.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Bitte eine Antwort eingeben.");
            return;
        }
        if (ans.equalsIgnoreCase(currentQuiz.en)) {
            JOptionPane.showMessageDialog(frame, "Richtig!");
        } else {
            JOptionPane.showMessageDialog(frame, "Falsch. Richtige Antwort: " + currentQuiz.en);
        }
        // next
        startQuiz();
    }

    private void saveToFile() {
        File dir = new File("ressources");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, "vocab.json");
        try (Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {
            w.write("[");
            for (int i = 0; i < vocabList.size(); i++) {
                Vocab v = vocabList.get(i);
                String deEsc = v.de.replace("\\", "\\\\").replace("\"", "\\\"");
                String enEsc = v.en.replace("\\", "\\\\").replace("\"", "\\\"");
                w.write("{\"de\":\"" + deEsc + "\",\"en\":\"" + enEsc + "\"}");
                if (i < vocabList.size() - 1) w.write(",\n");
            }
            w.write("]\n");
            JOptionPane.showMessageDialog(frame, "Gespeichert: " + file.getPath());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Fehler beim Speichern: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File("ressources/vocab.json");
        if (!file.exists()) {
            JOptionPane.showMessageDialog(frame, "Datei nicht gefunden: " + file.getPath());
            return;
        }
        try {
            String content = Files.readString(Path.of(file.getPath()), StandardCharsets.UTF_8);
            // Naiver JSON-Parser für Datei im Format [{"de":"...","en":"..."}, ...]
            Pattern p = Pattern.compile("\\{\\s*\"de\"\\s*:\\s*\"(.*?)\"\\s*,\\s*\"en\"\\s*:\\s*\"(.*?)\"\\s*\\}", Pattern.DOTALL);
            Matcher m = p.matcher(content);
            vocabList.clear();
            listModel.clear();
            while (m.find()) {
                String de = unescapeJsonString(m.group(1));
                String en = unescapeJsonString(m.group(2));
                Vocab v = new Vocab(de, en);
                vocabList.add(v);
                listModel.addElement(v.toString());
            }
            JOptionPane.showMessageDialog(frame, "Geladen: " + vocabList.size() + " Einträge");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Fehler beim Laden: " + e.getMessage());
        }
    }

    private String unescapeJsonString(String s) {
        // einfache Unescape-Logik für \" und \\\\ und sonstige Escapes
        StringBuilder sb = new StringBuilder(s.length());
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < s.length()) {
                char n = s.charAt(i + 1);
                if (n == '"' || n == '\\' || n == '/') {
                    sb.append(n);
                    i++;
                } else if (n == 'n') { sb.append('\n'); i++; }
                else if (n == 't') { sb.append('\t'); i++; }
                else if (n == 'r') { sb.append('\r'); i++; }
                else { sb.append(n); i++; }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
