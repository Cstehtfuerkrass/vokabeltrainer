package vokabeltrainer;

import java.awt.GraphicsEnvironment;
import java.util.List;
import java.util.Scanner;

import vokabeltrainer.storage.VocabStore;
import vokabeltrainer.controller.VocabController;
import vokabeltrainer.model.Vocab;
import vokabeltrainer.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        if (args != null && args.length > 0 && "selftest".equals(args[0])) {
            runSelfTest();
            return;
        }

        boolean headless = GraphicsEnvironment.isHeadless();
        VocabStore store = new VocabStore("resources/.vocab.json");
        VocabController controller = new VocabController(store);

        if (headless) {
            System.out.println("Headless environment detected — starting console mode.");
            runConsole(controller);
        } else {
            javax.swing.SwingUtilities.invokeLater(() -> {
                MainWindow mw = new MainWindow(controller);
                mw.setLocationRelativeTo(null);
                mw.setVisible(true);
            });
        }
    }

    private static void runSelfTest() {
        VocabStore store = new VocabStore("resources/.vocab.json");
        VocabController controller = new VocabController(store);
        System.out.println("Loaded " + controller.list().size() + " entries.");
        controller.add(new vokabeltrainer.model.Vocab("Haus", "house"));
        controller.save();
        System.out.println("After add: " + controller.list().size());
    }

    private static void runConsole(VocabController controller) {
        Scanner sc = new Scanner(System.in);
        loop: while (true) {
            System.out.println();
            System.out.println("Vokabeltrainer - Konsole");
            System.out.println("1) List");
            System.out.println("2) Add");
            System.out.println("3) Delete");
            System.out.println("4) Exam");
            System.out.println("5) Save");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String line = sc.nextLine().trim();
            switch (line) {
                case "1":
                    List<Vocab> all = controller.list();
                    if (all.isEmpty()) System.out.println("(no entries)");
                    for (Vocab v : all) System.out.println(v.getId() + ": " + v.getSource() + " - " + v.getTarget());
                    break;
                case "2":
                    System.out.print("Source: ");
                    String s = sc.nextLine().trim();
                    System.out.print("Target: ");
                    String t = sc.nextLine().trim();
                    controller.add(new Vocab(s, t));
                    System.out.println("Added.");
                    break;
                case "3":
                    System.out.print("ID to delete: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine().trim());
                        boolean ok = controller.delete(id);
                        System.out.println(ok ? "Deleted." : "Not found.");
                    } catch (Exception e) { System.out.println("Invalid id."); }
                    break;
                case "4":
                    runConsoleExam(controller);
                    break;
                case "5":
                    controller.save();
                    System.out.println("Saved.");
                    break;
                case "0":
                    break loop;
                default:
                    System.out.println("Unknown choice.");
            }
        }
        sc.close();
    }

    private static void runConsoleExam(VocabController controller) {
        List<Vocab> pool = controller.list();
        if (pool.isEmpty()) {
            System.out.println("No entries.");
            return;
        }
        java.util.Collections.shuffle(pool);
        int total = Math.min(10, pool.size());
        java.util.Scanner sc = new java.util.Scanner(System.in);
        int correct = 0;
        for (int i = 0; i < total; i++) {
            Vocab v = pool.get(i);
            System.out.print("Translate: " + v.getSource() + " -> ");
            String ans = sc.nextLine();
            if (ans.trim().equalsIgnoreCase(v.getTarget().trim())) correct++;
        }
        System.out.println("Score: " + correct + " / " + total);
    }
}
