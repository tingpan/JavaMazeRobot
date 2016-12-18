package ui.exceptions;

public class RobotMoveException extends Exception {

    private static final long serialVersionUID = 1L;

    public RobotMoveException() {
        super();
    }

    public RobotMoveException(String errorMsg) {
        super(errorMsg);
    }

}
