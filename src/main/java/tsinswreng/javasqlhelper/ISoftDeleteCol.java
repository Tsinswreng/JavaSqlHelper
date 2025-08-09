package tsinswreng.javasqlhelper;

public interface ISoftDeleteCol {
    String getCodeColName();
    void setCodeColName(String name);
    Object fnDelete(Object input);
}
