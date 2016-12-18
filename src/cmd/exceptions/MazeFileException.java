package cmd.exceptions;

public class MazeFileException extends Exception {

    //cmd.ui.exceptions about files
    private static final long serialVersionUID = 1L;

    public MazeFileException() {
        super();
    }

    public MazeFileException(String errorMsg) {
        super(errorMsg);
    }

}