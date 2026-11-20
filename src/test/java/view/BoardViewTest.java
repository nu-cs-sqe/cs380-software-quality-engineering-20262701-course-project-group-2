package view;

import controller.BoardController;
import domain.Game;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Set;

import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
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

    // V2
    @Test
    public void ClickActivePlayerPiece_PaintCenteredIndicatorsOnHighlightedSquares() {
        BoardController controller = new BoardController(new Game());
        BoardView boardView = new BoardView(controller);
        boardView.setSize(boardView.getPreferredSize());

        BufferedImage beforeSelection = render(boardView);

        clickSquare(boardView, 4, 6);

        BufferedImage afterSelection = render(boardView);
        int tileSize = getTileSize(boardView);

        Set<Location> expectedHighlightedSquares = Set.of(
                new Location(4, 5),
                new Location(4, 4)
        );

        assertEquals(
                expectedHighlightedSquares,
                controller.getHighlightedSquares()
        );

        for (int row = 2; row <= 5; row++) {
            for (int column = 0; column < 8; column++) {
                Location square = new Location(column, row);

                int centerBefore = getSquareCenterPixel(
                        beforeSelection,
                        column,
                        row,
                        tileSize
                );
                int centerAfter = getSquareCenterPixel(
                        afterSelection,
                        column,
                        row,
                        tileSize
                );

                int cornerBefore = getSquareCornerPixel(
                        beforeSelection,
                        column,
                        row,
                        tileSize
                );
                int cornerAfter = getSquareCornerPixel(
                        afterSelection,
                        column,
                        row,
                        tileSize
                );

                if (expectedHighlightedSquares.contains(square)) {
                    assertNotEquals(
                            centerBefore,
                            centerAfter,
                            "Expected move indicator at: " + square
                    );

                    assertEquals(
                            cornerBefore,
                            cornerAfter,
                            "Move indicator should not fill square: " + square
                    );
                } else {
                    assertEquals(
                            centerBefore,
                            centerAfter,
                            "Unexpected center change at: " + square
                    );

                    assertEquals(
                            cornerBefore,
                            cornerAfter,
                            "Unexpected corner change at: " + square
                    );
                }
            }
        }
    }

    // V3
    @Test
    public void ReselectActivePlayerPiece_UpdateSelectionAndMoveIndicators() {
        BoardController controller = new BoardController(new Game());
        BoardView boardView = new BoardView(controller);
        boardView.setSize(boardView.getPreferredSize());

        BufferedImage beforeSelection = render(boardView);
        int tileSize = getTileSize(boardView);

        clickSquare(boardView, 4, 6);
        clickSquare(boardView, 3, 6);

        BufferedImage afterReselection = render(boardView);

        assertEquals(
                new Location(3, 6),
                controller.getSelectedSquare()
        );

        assertEquals(
                Set.of(
                        new Location(3, 5),
                        new Location(3, 4)
                ),
                controller.getHighlightedSquares()
        );

        assertEquals(
                getSquareCornerPixel(beforeSelection, 4, 6, tileSize),
                getSquareCornerPixel(afterReselection, 4, 6, tileSize)
        );

        assertNotEquals(
                getSquareCornerPixel(beforeSelection, 3, 6, tileSize),
                getSquareCornerPixel(afterReselection, 3, 6, tileSize)
        );

        assertEquals(
                getSquareCenterPixel(beforeSelection, 4, 5, tileSize),
                getSquareCenterPixel(afterReselection, 4, 5, tileSize)
        );

        assertEquals(
                getSquareCenterPixel(beforeSelection, 4, 4, tileSize),
                getSquareCenterPixel(afterReselection, 4, 4, tileSize)
        );

        assertNotEquals(
                getSquareCenterPixel(beforeSelection, 3, 5, tileSize),
                getSquareCenterPixel(afterReselection, 3, 5, tileSize)
        );

        assertNotEquals(
                getSquareCenterPixel(beforeSelection, 3, 4, tileSize),
                getSquareCenterPixel(afterReselection, 3, 4, tileSize)
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

    // V5
    @Test
    public void CompleteLegalMove_RedrawPieceAndClearSelectionAndIndicators() {
        BoardController controller = new BoardController(new Game());
        BoardView boardView = new BoardView(controller);
        boardView.setSize(boardView.getPreferredSize());

        int tileSize = getTileSize(boardView);

        BufferedImage beforeMove = render(boardView);

        clickSquare(boardView, 4, 6);
        clickSquare(boardView, 4, 5);

        BufferedImage afterMove = render(boardView);

        assertNull(controller.getSelectedSquare());
        assertEquals(Set.of(), controller.getHighlightedSquares());

        assertNotEquals(
                getSquareCenterPixel(beforeMove, 4, 5, tileSize),
                getSquareCenterPixel(afterMove, 4, 5, tileSize)
        );

        assertNotEquals(
                getSquareCenterPixel(beforeMove, 4, 6, tileSize),
                getSquareCenterPixel(afterMove, 4, 6, tileSize)
        );

        Piece[][] snapshot = controller.getBoardSnapshot();

        assertNull(snapshot[6][4]);
        assertNotNull(snapshot[5][4]);
        assertEquals(PieceType.PAWN, snapshot[5][4].getType());
        assertEquals(PieceColor.WHITE, snapshot[5][4].getColor());
    }

    // GS3
    @Test
    public void CompleteMove_RefreshCurrentPlayerIndicator() {
        BoardController controller = new BoardController(new Game());

        GameStatsView gameStatsView = new GameStatsView(
                "Alice",
                "Bob",
                controller
        );

        BoardView boardView = new BoardView(
                controller,
                gameStatsView::refreshCurrentPlayer
        );
        boardView.setSize(boardView.getPreferredSize());

        assertEquals(
                "Alice (White)",
                gameStatsView.getCurrentPlayerText()
        );

        clickSquare(boardView, 4, 6);
        clickSquare(boardView, 4, 5);

        assertEquals(
                "Bob (Black)",
                gameStatsView.getCurrentPlayerText()
        );
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

    private int getSquareCenterPixel(
            BufferedImage renderedBoard,
            int column,
            int row,
            int tileSize) {

        return renderedBoard.getRGB(
                squareCenter(column, tileSize),
                squareCenter(row, tileSize)
        );
    }
}