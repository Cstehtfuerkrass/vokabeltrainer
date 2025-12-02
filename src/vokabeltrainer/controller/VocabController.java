package vokabeltrainer.controller;

import java.util.*;
import vokabeltrainer.model.Vocab;
import vokabeltrainer.storage.VocabStore;

public class VocabController {
    private final VocabStore store;
    private final List<Vocab> vocabs;
    private int nextId = 1;

    public VocabController(VocabStore store) {
        this.store = store;
        this.vocabs = new ArrayList<>(store.load());
        for (Vocab v : vocabs) {
            if (v.getId() >= nextId) nextId = v.getId() + 1;
        }
    }

    public List<Vocab> list() {
        return new ArrayList<>(vocabs);
    }

    public Vocab add(Vocab v) {
        v.setId(nextId++);
        vocabs.add(v);
        store.save(vocabs);
        return v;
    }

    public boolean delete(int id) {
        Iterator<Vocab> it = vocabs.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            Vocab v = it.next();
            if (v.getId() == id) {
                it.remove();
                removed = true;
                break;
            }
        }
        if (removed) store.save(vocabs);
        return removed;
    }

    public void save() {
        store.save(vocabs);
    }
}
