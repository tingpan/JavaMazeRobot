package cmd.exceptions;

public class RobotMoveException extends Exception {

    //cmd.ui.exceptions about cmd.ui.robot
    private static final long serialVersionUID = 1L;

    public RobotMoveException() {
        super();
    }

    public RobotMoveException(String errorMsg) {
        super(errorMsg);
    }

}
