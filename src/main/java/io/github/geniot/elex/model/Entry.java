package io.github.geniot.elex.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Entry {
    private String dicId;
    private String dicName;
    private String dicIndexLanguage;
    private String dicContentsLanguage;

    private String headword;
    private String body;
}
