package ninja.peplinski.nightcore.model.specifications;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface SearchableInsideRelationshipChain {
    @JsonIgnore
    public String getKeyForSearchableProperty();
}
