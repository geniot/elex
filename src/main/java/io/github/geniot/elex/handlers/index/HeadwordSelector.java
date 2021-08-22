package io.github.geniot.elex.handlers.index;

import io.github.geniot.elex.DictionariesPool;
import io.github.geniot.elex.ezip.model.ElexDictionary;
import io.github.geniot.elex.model.Action;
import io.github.geniot.elex.model.Model;

import java.io.IOException;
import java.util.Set;

public class HeadwordSelector {
    public String select(Model model,
                         Set<ElexDictionary> set,
                         IteratorsWrapper forwardIteratorsWrapper,
                         IteratorsWrapper backwardIteratorsWrapper) throws Exception {
        String selectedHeadword = model.getSelectedHeadword();
        selectedHeadword = bestMatch(forwardIteratorsWrapper, backwardIteratorsWrapper, selectedHeadword);

        if (model.getAction().equals(Action.TO_START)) {
            selectedHeadword = DictionariesPool.getInstance().getMinHeadword(set);
        } else if (model.getAction().equals(Action.TO_END)) {
            selectedHeadword = DictionariesPool.getInstance().getMaxHeadword(set);
        } else if (model.getAction().equals(Action.NEXT_WORD)) {
            selectedHeadword = scroll(forwardIteratorsWrapper, selectedHeadword, 1);
            model.selectNext();
        } else if (model.getAction().equals(Action.PREVIOUS_WORD)) {
            selectedHeadword = scroll(backwardIteratorsWrapper, selectedHeadword, 1);
            model.selectPrevious();
        } else if (model.getAction().equals(Action.NEXT_PAGE)) {
            selectedHeadword = scroll(forwardIteratorsWrapper, selectedHeadword, model.getVisibleSize());
        } else if (model.getAction().equals(Action.PREVIOUS_PAGE)) {
            selectedHeadword = scroll(backwardIteratorsWrapper, selectedHeadword, model.getVisibleSize());
        } else if (model.getAction().equals(Action.NEXT_TEN_PAGES)) {
            selectedHeadword = scroll(forwardIteratorsWrapper, selectedHeadword, model.getVisibleSize() * 10);
        } else if (model.getAction().equals(Action.PREVIOUS_TEN_PAGES)) {
            selectedHeadword = scroll(backwardIteratorsWrapper, selectedHeadword, model.getVisibleSize() * 10);
        } else if (model.getAction().equals(Action.SEARCH)) {
            String userInput = model.getUserInput();
            selectedHeadword = bestMatch(forwardIteratorsWrapper, backwardIteratorsWrapper, userInput);
        }
        return selectedHeadword;
    }

    private String bestMatch(IteratorsWrapper forwardIteratorsWrapper,
                             IteratorsWrapper backwardIteratorsWrapper,
                             String selectedHeadword) throws IOException {
        forwardIteratorsWrapper.setFrom(selectedHeadword);
        backwardIteratorsWrapper.setFrom(selectedHeadword);
        if (forwardIteratorsWrapper.contains(selectedHeadword) ||
                backwardIteratorsWrapper.contains(selectedHeadword)) {
            return selectedHeadword;
        } else {
            String bestMatch = forwardIteratorsWrapper.next();
            if (bestMatch == null) {
                bestMatch = backwardIteratorsWrapper.next();
            }
            return bestMatch;
        }
    }

    private String scroll(IteratorsWrapper iteratorsWrapper,
                          String from,
                          int amount) {
        iteratorsWrapper.setFrom(from);
        while (amount-- > 0) {
            from = iteratorsWrapper.next();
        }
        return from;
    }
}
