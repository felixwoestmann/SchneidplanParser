package processing;

import model.Schneidplan;

public interface Processor {

    void processAndWrite(Schneidplan schneidplan, String path);

    String getFileExtension();

    String getFileExtensionName();
}
