package exceptions;

public class InvalidUserInputException extends Exception{
    /**
     * class ctor
     * @param message the error msg to print
     */
    public InvalidUserInputException(String message) {
        super(message);
    }


}
