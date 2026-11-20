package view;

import controller.BoardController;
import domain.Location;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public final class BoardView extends JPanel {
    private static final int BOARD_SIZE = 8;
    private static final int TILE_SIZE = 100;
    private static final Color LIGHT_SQUARE_COLOR = new Color(240, 217, 181);
    private static final Color DARK_SQUARE_COLOR = new Color(181, 136, 99);
    private static final Color SELECTED_SQUARE_COLOR = new Color(164, 149, 195);
    private static final Color MOVE_INDICATOR_COLOR = new Color(128, 0, 128);
    private static final int MOVE_INDICATOR_DIAMETER = TILE_SIZE / 4;
    private static final int MOVE_INDICATOR_OFFSET = (TILE_SIZE - MOVE_INDICATOR_DIAMETER) / 2;

    private final Map<PieceType, Image> whitePieceImages = new HashMap<>();
    private final Map<PieceType, Image> blackPieceImages = new HashMap<>();
    private final BoardController boardController;
    private final Runnable turnChangedHandler;

    public BoardView(BoardController boardController) {
        this(boardController, () -> {
        });
    }

    BoardView(BoardController boardController, Runnable turnChangedHandler) {
        this.boardController = boardController;
        this.turnChangedHandler = turnChangedHandler;
        setPreferredSize(
                new Dimension(BOARD_SIZE * TILE_SIZE, BOARD_SIZE * TILE_SIZE)
        );
        loadPieceImages();
        addMouseListener(new BoardMouseListener());
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawBoard(graphics);
        drawSelectedSquare(graphics);
        drawPieces(graphics);
        drawMoveIndicators(graphics);
    }

    private void drawBoard(Graphics graphics) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                Color squareColor = (row + column) % 2 == 0
                        ? LIGHT_SQUARE_COLOR
                        : DARK_SQUARE_COLOR;
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
        try (InputStream imageInputStream = getClass()
                .getClassLoader()
                .getResourceAsStream(imagePath)) {
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
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Unable to load piece image: " + imagePath,
                    exception
            );
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
        Location selectedSquare = boardController.getSelectedSquare();

        if (selectedSquare == null) {
            return;
        }

        graphics.setColor(SELECTED_SQUARE_COLOR);
        graphics.fillRect(
                selectedSquare.getColumn() * TILE_SIZE,
                selectedSquare.getRow() * TILE_SIZE,
                TILE_SIZE,
                TILE_SIZE
        );
    }

    private void drawMoveIndicators(Graphics graphics) {
        graphics.setColor(MOVE_INDICATOR_COLOR);

        for (Location highlightedSquare : boardController.getHighlightedSquares()) {
            int indicatorX = highlightedSquare.getColumn() * TILE_SIZE + MOVE_INDICATOR_OFFSET;
            int indicatorY = highlightedSquare.getRow() * TILE_SIZE + MOVE_INDICATOR_OFFSET;

            graphics.fillOval(
                    indicatorX,
                    indicatorY,
                    MOVE_INDICATOR_DIAMETER,
                    MOVE_INDICATOR_DIAMETER
            );
        }
    }

    private final class BoardMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event) {
            int clickedColumn = event.getX() / TILE_SIZE;
            int clickedRow = event.getY() / TILE_SIZE;

            if (clickedColumn < 0 || clickedColumn >= BOARD_SIZE
                    || clickedRow < 0 || clickedRow >= BOARD_SIZE) {
                return;
            }

            PieceColor turnBeforeClick = boardController.getCurrentTurn();

            boardController.handleSquareClick(new Location(clickedColumn, clickedRow));

            PieceColor turnAfterClick = boardController.getCurrentTurn();
            if (turnAfterClick != turnBeforeClick) {
                turnChangedHandler.run();
            }

            repaint();
        }
    }
}