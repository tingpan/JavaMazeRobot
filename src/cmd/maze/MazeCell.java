package cmd.maze;

public class MazeCell {

    public final Position cellPosition;
    // if the mazeCell is a wall or not
    public final boolean isMovable;
    private final char cellSymbol;
    // if a road is a wrong road
    private boolean mark = true;

    // if a cell is being visited
    private boolean ifVisit = false;

    // if a cell has been visited and is a right way
    private boolean footPrint = false;

    public MazeCell(int x, int y, char symbol) {
        cellPosition = new Position(x, y);
        cellSymbol = symbol;

        if (symbol != ' ') {
            isMovable = false;
        } else {
            isMovable = true;
        }
    }

    public boolean getIfVisit() {
        return ifVisit;
    }

    public void setIfVisit(boolean state) {
        ifVisit = state;
    }

    void printCell() {

        // if a cell is being visited, print the R to represent the cmd.ui.robot
        if (ifVisit) {
            System.out.print('R');
        } else if (footPrint) {
            System.out.print('.');
        } else {
            System.out.print(cellSymbol);
        }

    }

    public boolean getMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public boolean getFootprint() {
        return footPrint;
    }

    public void setFootprint(boolean footprint) {
        this.footPrint = footprint;
    }
}
