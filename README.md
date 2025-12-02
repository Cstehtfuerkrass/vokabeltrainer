# Vokabeltrainer

Kleiner Java-Swing Vokabeltrainer für die Schule.

Kompilieren:

```bash
mkdir -p out
javac -d out $(find src -name "*.java")
```

Starten (GUI):

```bash
java -cp out vokabeltrainer.Main
```

Selftest (konsole, fügt eine Beispiel-Vokabel ein):

```bash
java -cp out vokabeltrainer.Main selftest
```

Die Vokabeln werden in `resources/.vocab.json` im Projektverzeichnis gespeichert.

