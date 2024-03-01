package output_factory;

import ascii_art.Shell;
import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;

public class FactoryAsciiOutput {
    private static final String DEFAULT_FILE_NAME = "out.html";
    private static final String DEFAULT_FONT = "Courier New";

    /**
     * Class ctor
     */
    public FactoryAsciiOutput() {}
    public AsciiOutput createAsciiOutput(String outputType){
        switch (outputType){
            case Shell.HTML:
                return new HtmlAsciiOutput(DEFAULT_FILE_NAME, DEFAULT_FONT);
            default:
                return new ConsoleAsciiOutput();
        }
    }
}
