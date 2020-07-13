package ninja.peplinski.nightcore.model.specifications;

public class SearchCriteria {
    private String key;
    private Operation operation;
    private Object value;

    /*
    Optional property for searching inside an relationship type
    example: Searching for songs by there related artist name
    SearchCriteria("artist", ":", searchText, "name")
               type inside Song          type of the Artist
    */
    private String insideRelationshipTypeKey;

    public SearchCriteria(String key, Operation operation, Object value, String insideRelationshipTypeKey) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.insideRelationshipTypeKey = insideRelationshipTypeKey;
    }

    public SearchCriteria(String key, Operation operation, Object value) {
        this(key, operation, value, null);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getInsideRelationshipTypeKey() {
        return insideRelationshipTypeKey;
    }

    public void setInsideRelationshipTypeKey(String insideRelationshipTypeKey) {
        this.insideRelationshipTypeKey = insideRelationshipTypeKey;
    }

    public enum Operation {
        LESS_THAN, GREATER_THAN, LIKE
    }
}
