package ui.exceptions;

public class MazeFileException extends Exception {

    private static final long serialVersionUID = 1L;

    public MazeFileException() {
        super();
    }

    public MazeFileException(String errorMsg) {
        super(errorMsg);
    }

}