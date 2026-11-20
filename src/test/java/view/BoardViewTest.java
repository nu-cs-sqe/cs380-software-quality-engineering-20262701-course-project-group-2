package view;

import controller.BoardController;
import domain.Game;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardViewTest {

    // V4
    @Test
    public void ClickUnhighlightedSquare_AfterSelection_RemoveVisibleSelection() {
        BoardController controller = new BoardController(new Game());
        BoardView boardView = new BoardView(controller);
        boardView.setSize(boardView.getPreferredSize());

        clickSquare(boardView, 4, 6);
        clickSquare(boardView, 6, 5);

        BufferedImage renderedBoard = render(boardView);
        int tileSize = getTileSize(boardView);

        int clickedSquareColor = renderedBoard.getRGB(
                squareCenter(6, tileSize),
                squareCenter(5, tileSize)
        );

        int ordinaryDarkSquareColor = renderedBoard.getRGB(
                squareCenter(4, tileSize),
                squareCenter(5, tileSize)
        );

        assertNull(controller.getSelectedSquare());
        assertEquals(ordinaryDarkSquareColor, clickedSquareColor);
    }

    private void clickSquare(
            BoardView boardView,
            int column,
            int row) {

        int tileSize = getTileSize(boardView);

        MouseEvent clickEvent = new MouseEvent(
                boardView,
                MouseEvent.MOUSE_CLICKED,
                System.currentTimeMillis(),
                0,
                squareCenter(column, tileSize),
                squareCenter(row, tileSize),
                1,
                false
        );

        for (MouseListener listener : boardView.getMouseListeners()) {
            listener.mouseClicked(clickEvent);
        }
    }

    private BufferedImage render(BoardView boardView) {
        BufferedImage renderedBoard = new BufferedImage(
                boardView.getWidth(),
                boardView.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D graphics = renderedBoard.createGraphics();
        boardView.paint(graphics);
        graphics.dispose();

        return renderedBoard;
    }

    private int getTileSize(BoardView boardView) {
        return boardView.getPreferredSize().width / 8;
    }

    private int squareCenter(int coordinate, int tileSize) {
        return coordinate * tileSize + tileSize / 2;
    }
}