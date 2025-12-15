package app;

import manager.SetManager;
import rating.ExamUnit;
import rating.ExamMode; // UMBENANNT
import rating.RatingSystem;
import setclasses.Set;
import setclasses.Languages;

import java.util.Scanner;
import java.util.Arrays;

public class App {

    public static void main(String[] args) {

        // HINWEIS: Stelle sicher, dass GSON eingebunden ist!

        SetManager manager = new SetManager();
        Scanner scanner = new Scanner(System.in);
        String setName = "Grundlagen Deutsch-Englisch";

        // --- 1. ERSTES SETUP UND SPEICHERN ---
        System.out.println("--- 1. ERSTES SETUP UND SPEICHERN ---");
        Set de_en_set = new Set(setName, Languages.DE, Languages.EN);

        de_en_set.addVocab(new String[] { "Haus", "Zuhause", "" }, new String[] { "house", "home", "" });
        de_en_set.addVocab(new String[] { "Hund", "", "" }, new String[] { "dog", "", "" });
        de_en_set.addVocab(new String[] { "Katze", "", "" }, new String[] { "cat", "", "" });
        de_en_set.addVocab(new String[] { "Apfel", "", "" }, new String[] { "apple", "", "" });

        manager.saveSet(de_en_set);

        // --- 2. LADEN UND PRÜFUNG STARTEN ---
        System.out.println("\n--- 2. LADEN UND PRÜFUNG STARTEN ---");
        Set loadedSet = manager.loadSet(setName);

        if (loadedSet == null) {
            scanner.close();
            return;
        }

        // Starte die Prüfung: Abfrage von ENGLISCH nach DEUTSCH
        RatingSystem ratingSystem = new RatingSystem(loadedSet, ExamMode.LANG2_TO_LANG1); // UMBENANNT

        System.out.println("\n*** VOKABELPRÜFUNG STARTET ***");
        System.out.println("Set: " + loadedSet.getName());
        System.out.println("Modus: " + loadedSet.getLang2() + " -> " + loadedSet.getLang1());
        System.out.println("Gesamte Vokabeln zum Lernen: " + loadedSet.getVocabularies().getLength());
        System.out.println("---------------------------------");

        // --- 3. PRÜFUNGS-LOOP ---
        while (!ratingSystem.isExamFinished()) {

            ExamUnit currentUnit = ratingSystem.getNextUnit();

            String askedWord = currentUnit.getVocabulary().getVocab2()[0];

            System.out.print("\nÜbersetze '" + askedWord + "' (" + currentUnit.getAskedLanguage() +
                    ") nach " + currentUnit.getExpectedLanguage() + ": ");

            String userInput = scanner.nextLine();

            boolean isCorrect = ratingSystem.rateAndProceed(userInput);

            if (isCorrect) {
                System.out.println("✅ KORREKT!");
            } else {
                String correctOptions = Arrays.toString(currentUnit.getVocabulary().getVocab1());

                System.out.println("❌ FALSCH! Deine Antwort war: " + currentUnit.getGivenAnswer());
                System.out.println("Eine korrekte Übersetzung ist: " + correctOptions);
                System.out.println("Diese Vokabel wurde zur Wiederholung in die Warteschlange zurückgelegt.");
            }
        }

        // --- 4. ERGEBNISSE AUSGEBEN ---
        System.out.println("\n=================================");
        System.out.println("🎉 PRÜFUNG BEENDET!");
        System.out.println("Gesamtversuche: " + ratingSystem.getTotalAttempts());
        System.out.println("Korrekte Antworten: " + ratingSystem.getCorrectAnswers());
        System.out.printf("Erfolgsquote: %.2f%%\n", ratingSystem.getSuccessRate() * 100);
        System.out.println("=================================");

        scanner.close();
    }
}