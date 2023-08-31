package pl.kurs.nbp.enums;

public enum NbpTables {
    A("a"), B("b"), C("c");

    private final String tableName;

    NbpTables(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }
}
