package mapper;

public class CsvBuilder {

    private final StringBuilder builder;

    public CsvBuilder() {
        builder = new StringBuilder();
    }

    public CsvBuilder add(String s) {
        builder.append(s).append(",");
        return this;
    }

    public String addAndBuild(String s) {
        builder.append(s);
        return builder.toString();
    }

}
