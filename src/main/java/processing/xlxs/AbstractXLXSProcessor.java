package processing.xlxs;

import processing.Processor;

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
