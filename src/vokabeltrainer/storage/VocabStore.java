package vokabeltrainer.storage;

import java.io.*;
import java.util.*;
import vokabeltrainer.model.Vocab;

public class VocabStore {
    private final File file;

    public VocabStore(String path) {
        this.file = new File(path);
        try {
            if (!file.exists()) {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) parent.mkdirs();
                writeRaw("[]");
            }
        } catch (Exception e) {
            // ignore
        }
    }

    public List<Vocab> load() {
        try {
            String s = readAll();
            s = s.trim();
            if (s.length() == 0 || s.equals("[]")) return new ArrayList<>();
            if (s.startsWith("[")) s = s.substring(1);
            if (s.endsWith("]")) s = s.substring(0, s.length() - 1);
            List<Vocab> res = new ArrayList<>();
            // split by '},{' (naive but acceptable for small entries)
            String[] parts = s.split("\\},\\{");
            for (int i = 0; i < parts.length; i++) {
                String p = parts[i];
                if (!p.startsWith("{")) p = "{" + p;
                if (!p.endsWith("}")) p = p + "}";
                Vocab v = Vocab.fromJson(p);
                if (v != null) res.add(v);
            }
            return res;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public void save(List<Vocab> list) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (int i = 0; i < list.size(); i++) {
                Vocab v = list.get(i);
                sb.append(v.toJson(v.getId()));
                if (i < list.size() - 1) sb.append(',');
            }
            sb.append("]");
            writeRaw(sb.toString());
        } catch (Exception e) {
            // ignore
        }
    }

    private String readAll() throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    private void writeRaw(String s) throws IOException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, false))) {
            pw.print(s);
        }
    }
}
