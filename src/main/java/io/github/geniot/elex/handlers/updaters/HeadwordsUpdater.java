package io.github.geniot.elex.handlers.updaters;

import io.github.geniot.elex.CaseInsensitiveComparatorV4;
import io.github.geniot.elex.DictionariesPool;
import io.github.geniot.elex.ezip.model.ElexDictionary;
import io.github.geniot.elex.handlers.index.Direction;
import io.github.geniot.elex.handlers.index.HeadwordSelector;
import io.github.geniot.elex.handlers.index.IteratorsWrapper;
import io.github.geniot.elex.model.Headword;
import io.github.geniot.elex.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class HeadwordsUpdater {

    @Autowired
    CaseInsensitiveComparatorV4 caseInsensitiveComparator;

    @Autowired
    DictionariesPool dictionariesPool;
    @Autowired
    HeadwordSelector headwordSelector;

    public void updateHeadwords(Model model) throws Exception {
        Set<ElexDictionary> set = new HashSet<>(dictionariesPool.getElexDictionaries(model).values());

        SortedSet<String> index = new TreeSet<>(caseInsensitiveComparator);

        IteratorsWrapper forwardIteratorsWrapper = new IteratorsWrapper(set, model.getSelectedHeadword(), Direction.FORWARD);
        IteratorsWrapper backwardIteratorsWrapper = new IteratorsWrapper(set, model.getSelectedHeadword(), Direction.BACKWARD);

        String selectedHeadword = headwordSelector.select(model, set, forwardIteratorsWrapper, backwardIteratorsWrapper);
        int pageSize = model.getVisibleSize();
        int viewOffset = model.getSelectedIndex();

        if (pageSize <= 0) {
            empty(model);
            return;
        }

        if (forwardIteratorsWrapper.contains(selectedHeadword)) {
            index.add(selectedHeadword);
        }


        forwardIteratorsWrapper.setFrom(selectedHeadword);
        backwardIteratorsWrapper.setFrom(selectedHeadword);

        int forwardCounter = pageSize - viewOffset - 1;
        while (index.size() < pageSize && forwardIteratorsWrapper.hasNext() && forwardCounter > 0) {
            index.add(forwardIteratorsWrapper.next());
            --forwardCounter;
        }
        while (index.size() < pageSize && backwardIteratorsWrapper.hasNext()) {
            index.add(backwardIteratorsWrapper.next());
        }
        while (index.size() < pageSize && forwardIteratorsWrapper.hasNext()) {
            index.add(forwardIteratorsWrapper.next());
        }

        if (index.isEmpty()) {
            empty(model);
            return;
        }

        List<Headword> headwords = new ArrayList<>();
        for (String s : index) {
            Headword hw = new Headword(s);
            if (hw.getName().equals(selectedHeadword)) {
                hw.setSelected(true);
            } else {
                hw.setSelected(false);
            }
            headwords.add(hw);
        }

        if (headwords.get(0).getName().equals(dictionariesPool.getMinHeadword(set))) {
            model.setStartReached(true);
        } else {
            model.setStartReached(false);
        }

        if (headwords.get(headwords.size() - 1).getName().equals(dictionariesPool.getMaxHeadword(set))) {
            model.setEndReached(true);
        } else {
            model.setEndReached(false);
        }

        model.setSelectedHeadword(selectedHeadword);
        model.setHeadwords(headwords.toArray(new Headword[headwords.size()]));
    }

    private void empty(Model model) {
        model.setHeadwords(new Headword[]{});
        model.setStartReached(true);
        model.setEndReached(true);
    }

}
