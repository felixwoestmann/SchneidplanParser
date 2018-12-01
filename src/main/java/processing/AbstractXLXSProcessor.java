package processing;

public abstract class AbstractXLXSProcessor implements Processor {

    @Override
    public String getFileExtension() {
        return "*.xlsx";
    }

    @Override
    public String getFileExtensionName() {
        return "Excel/(.xlsx)";
    }
}
