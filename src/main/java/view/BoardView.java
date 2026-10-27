package view;

import controller.BoardController;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BoardView extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 100;
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE_COLOR = new Color(164, 149, 195);

    private final Map<PieceType, Image> whitePieceImages = new HashMap<>();
    private final Map<PieceType, Image> blackPieceImages = new HashMap<>();
    private final BoardController boardController;

    private int selectedRow = -1;
    private int selectedColumn = -1;

    public BoardView(BoardController boardController) {
        this.boardController = boardController;
        setPreferredSize(new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE));
        loadPieceImages();
        addMouseListener(new BoardMouseListener());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawBoard(graphics);
        drawSelectedSquare(graphics);
        drawPieces(graphics);
    }

    private void drawBoard(Graphics graphics) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Color squareColor = (row + column) % 2 == 0 ? LIGHT_SQUARE_COLOR : DARK_SQUARE_COLOR;
                graphics.setColor(squareColor);
                graphics.fillRect(column * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void loadPieceImages() {
        loadPieceImage(PieceType.PAWN, PieceColor.WHITE, "images/white_pawn.png");
        loadPieceImage(PieceType.ROOK, PieceColor.WHITE, "images/white_rook.png");
        loadPieceImage(PieceType.KNIGHT, PieceColor.WHITE, "images/white_knight.png");
        loadPieceImage(PieceType.BISHOP, PieceColor.WHITE, "images/white_bishop.png");
        loadPieceImage(PieceType.QUEEN, PieceColor.WHITE, "images/white_queen.png");
        loadPieceImage(PieceType.KING, PieceColor.WHITE, "images/white_king.png");

        loadPieceImage(PieceType.PAWN, PieceColor.BLACK, "images/black_pawn.png");
        loadPieceImage(PieceType.ROOK, PieceColor.BLACK, "images/black_rook.png");
        loadPieceImage(PieceType.KNIGHT, PieceColor.BLACK, "images/black_knight.png");
        loadPieceImage(PieceType.BISHOP, PieceColor.BLACK, "images/black_bishop.png");
        loadPieceImage(PieceType.QUEEN, PieceColor.BLACK, "images/black_queen.png");
        loadPieceImage(PieceType.KING, PieceColor.BLACK, "images/black_king.png");
    }

    private void loadPieceImage(PieceType type, PieceColor color, String imagePath) {
        try (InputStream imageInputStream = getClass().getClassLoader().getResourceAsStream(imagePath)) {
            if (imageInputStream == null) {
                throw new IllegalStateException("Piece image not found: " + imagePath);
            }

            BufferedImage image = ImageIO.read(imageInputStream);
            if (image == null) {
                throw new IllegalStateException("Unable to read piece image: " + imagePath);
            }

            Map<PieceType, Image> pieceImages = color == PieceColor.WHITE
                ? whitePieceImages
                : blackPieceImages;
            pieceImages.put(type, image);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to load piece image: " + imagePath, e);
        }
    }

    private void drawPieces(Graphics graphics) {
        Piece[][] boardSnapshot = boardController.getBoardSnapshot();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Piece piece = boardSnapshot[row][column];
                if (piece != null) {
                    Image pieceImage = piece.getColor() == PieceColor.WHITE
                        ? whitePieceImages.get(piece.getType())
                        : blackPieceImages.get(piece.getType());
                    graphics.drawImage(
                        pieceImage,
                        column * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE,
                        this
                    );
                }
            }
        }
    }

    private void drawSelectedSquare(Graphics graphics) {
        if (selectedRow < 0 || selectedColumn < 0) {
            return;
        }

        graphics.setColor(SELECTED_SQUARE_COLOR);
        graphics.fillRect(
            selectedColumn * TILE_SIZE,
            selectedRow * TILE_SIZE,
            TILE_SIZE,
            TILE_SIZE
        );
    }

    private class BoardMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            selectedColumn = event.getX() / TILE_SIZE;
            selectedRow = event.getY() / TILE_SIZE;

            // FIXME: perform input validation

            repaint();
            boardController.handleSquareClick(new Location(selectedColumn, selectedRow));
        }
    }
}
