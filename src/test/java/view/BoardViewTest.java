package view;

import controller.BoardController;
import domain.Game;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import domain.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardViewTest {

    // V1
    @Test
    public void ClickActivePlayerPiece_PaintControllerSelectedSquare() {
        BoardController controller = new BoardController(new Game());
        BoardView boardView = new BoardView(controller);
        boardView.setSize(boardView.getPreferredSize());

        BufferedImage beforeSelection = render(boardView);
        int tileSize = getTileSize(boardView);

        int selectedSquareBefore = getSquareCornerPixel(
                beforeSelection,
                4,
                6,
                tileSize
        );
        int otherPawnSquareBefore = getSquareCornerPixel(
                beforeSelection,
                2,
                6,
                tileSize
        );

        clickSquare(boardView, 4, 6);

        BufferedImage afterSelection = render(boardView);

        int selectedSquareAfter = getSquareCornerPixel(
                afterSelection,
                4,
                6,
                tileSize
        );
        int otherPawnSquareAfter = getSquareCornerPixel(
                afterSelection,
                2,
                6,
                tileSize
        );

        assertEquals(
                new Location(4, 6),
                controller.getSelectedSquare()
        );
        assertNotEquals(
                selectedSquareBefore,
                selectedSquareAfter
        );
        assertEquals(
                otherPawnSquareBefore,
                otherPawnSquareAfter
        );
    }

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

    private int getSquareCornerPixel(
            BufferedImage renderedBoard,
            int column,
            int row,
            int tileSize) {

        int inset = 5;

        return renderedBoard.getRGB(
                column * tileSize + inset,
                row * tileSize + inset
        );
    }
}