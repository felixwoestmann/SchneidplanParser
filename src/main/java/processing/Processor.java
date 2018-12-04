package processing;

import model.Schneidplan;

public interface Processor {

    void processAndWrite(Schneidplan schneidplan, String path) throws Exception;

    String getFileExtension();

    String getFileExtensionName();
}
