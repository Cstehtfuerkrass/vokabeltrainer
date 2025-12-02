package vokabeltrainer.model;

public class Vocab {
    private int id;
    private String source;
    private String target;

    public Vocab(int id, String source, String target) {
        this.id = id;
        this.source = source;
        this.target = target;
    }

    public Vocab(String source, String target) {
        this(-1, source, target);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return source + " - " + target;
    }

    public String toJson(int idOverride) {
        int useId = (idOverride >= 0) ? idOverride : id;
        return "{\"id\":" + useId + ",\"source\":\"" + escape(source) + "\",\"target\":\"" + escape(target) + "\"}";
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public static Vocab fromJson(String json) {
        // very small parser for objects like {"id":1,"source":"Haus","target":"house"}
        try {
            String tmp = json.trim();
            int id = -1;
            String source = "";
            String target = "";
            int idxId = tmp.indexOf("\"id\"");
            if (idxId >= 0) {
                int colon = tmp.indexOf(':', idxId);
                int comma = tmp.indexOf(',', colon);
                String idStr = tmp.substring(colon + 1, comma).trim();
                id = Integer.parseInt(idStr);
            }
            int idxSource = tmp.indexOf("\"source\"");
            if (idxSource >= 0) {
                int firstQuote = tmp.indexOf('"', tmp.indexOf(':', idxSource) + 1);
                int secondQuote = tmp.indexOf('"', firstQuote + 1);
                source = unescape(tmp.substring(firstQuote + 1, secondQuote));
            }
            int idxTarget = tmp.indexOf("\"target\"");
            if (idxTarget >= 0) {
                int firstQuote = tmp.indexOf('"', tmp.indexOf(':', idxTarget) + 1);
                int secondQuote = tmp.indexOf('"', firstQuote + 1);
                target = unescape(tmp.substring(firstQuote + 1, secondQuote));
            }
            return new Vocab(id, source, target);
        } catch (Exception e) {
            return null;
        }
    }

    private static String unescape(String s) {
        return s.replace("\\\"", "\"").replace("\\\\", "\\");
    }
}
