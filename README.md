# Vokabeltrainer (Frontend)

Dieses Repository enthält ein einfaches Swing-Frontend für einen Vokabeltrainer.

Kurz: keine externen Abhängigkeiten, nur Java und Swing (in JDK enthalten).

Dateien:
- `frontend/Frontend.java` – das Swing-UI (hinzufügen, entfernen, speichern, laden, Quiz)
- `ressources/vocab.json` – Vokabelliste (JSON-Array)

Kompilieren und Starten (aus dem Projekt-Root):

```bash
javac frontend/Frontend.java
java -cp frontend Frontend
```

Hinweis:
- Das Programm öffnet ein GUI-Fenster — es benötigt eine grafische Umgebung (X11/Wayland). In headless-Containern wird das Fenster nicht angezeigt.
- Die Vokabeldatei wird unter `ressources/vocab.json` gespeichert und geladen.
- Keine externen Bibliotheken verwendet.

Beispiel-JSON:

```json
[
  {"de":"Haus","en":"house"},
  {"de":"Baum","en":"tree"},
  {"de":"Katze","en":"cat"}
]
```

Wenn du möchtest, erweitere ich das Frontend z. B. um Fortschrittsstatistiken, CSV-Import/Export oder Multiple-Choice-Quiz.
# Vokabeltrainer
