package Model;

public class Tag {
    private Object content;
    private String type;
    private String[] alias;

    public Tag(Object content, String type, String[] alias) {
        this.content = content;
        this.type = type;
        this.alias = alias;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getAlias() {
        return alias;
    }

    public void setAlias(String[] alias) {
        this.alias = alias;
    }
}
