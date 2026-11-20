package domain;

import domain.piece.Bishop;
import domain.piece.Knight;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import domain.piece.Rook;
import java.util.Set;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class GameTest {

    // G1
    @Test
    public void GetCurrentTurn_OnNewGame_ReturnWhite() {
        Game game = new Game();
        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = game.getCurrentTurn();
        assertEquals(expected, actual);
    }

    // G5
    @Test
    public void GetBoardSnapshot_OnNewGame_Return8x8Snapshot() {
        Game game = new Game();
        Piece[][] snapshot = game.getBoardSnapshot();
        assertEquals(8, snapshot.length);
        for (Piece[] row : snapshot) {
            assertEquals(8, row.length);
        }
    }

    // Supporting setup integration smoke test (G1 + G5)
    @Test
    public void NewGame_InitializeBoardAndWhiteTurn() {
        Game game = new Game();
        Piece[][] snapshot = game.getBoardSnapshot();
        PieceColor currentTurn = game.getCurrentTurn();
        assertEquals(8, snapshot.length);
        assertEquals(PieceColor.WHITE, currentTurn);
    }

    // M3
    @Test
    public void GetLegalMoves_Pawn_BlockedAhead_ReturnEmpty() {
        Game game = gameWith(
                square(4, 4, new Pawn(PieceColor.WHITE)),
                square(4, 3, new Pawn(PieceColor.WHITE))
        );
        Set<Location> actual = game.getLegalMoves(new Location(4, 4));
        assertEquals(Set.of(), actual);
    }

    // M4
    @Test
    public void GetLegalMoves_Pawn_OneCapturePossible_ReturnTwo() {
        Game game = gameWith(
                square(4, 4, new Pawn(PieceColor.WHITE)),
                square(5, 3, new Pawn(PieceColor.BLACK))
        );
        Set<Location> expected = Set.of(new Location(4, 3), new Location(5, 3));
        Set<Location> actual = game.getLegalMoves(new Location(4, 4));
        assertEquals(expected, actual);
    }

    // M5
    @Test
    public void GetLegalMoves_Pawn_TwoCapturesPossible_ReturnThree() {
        Game game = gameWith(
                square(4, 4, new Pawn(PieceColor.WHITE)),
                square(3, 3, new Pawn(PieceColor.BLACK)),
                square(5, 3, new Pawn(PieceColor.BLACK))
        );
        Set<Location> expected = Set.of(
                new Location(4, 3),
                new Location(3, 3),
                new Location(5, 3)
        );
        Set<Location> actual = game.getLegalMoves(new Location(4, 4));
        assertEquals(expected, actual);
    }

    // M40
    @Test
    public void GetLegalMoves_Pawn_OnStartRowBlockedAhead_ReturnEmpty() {
        Game game = gameWith(
                square(4, 6, new Pawn(PieceColor.WHITE)),
                square(4, 5, new Pawn(PieceColor.BLACK))
        );
        Set<Location> actual = game.getLegalMoves(new Location(4, 6));
        assertEquals(Set.of(), actual);
    }

    // M41
    @Test
    public void GetLegalMoves_Pawn_OwnPieceDiagonal_NotCapture() {
        Game game = gameWith(
                square(4, 4, new Pawn(PieceColor.WHITE)),
                square(5, 3, new Knight(PieceColor.WHITE))
        );
        Set<Location> expected = Set.of(new Location(4, 3));
        Set<Location> actual = game.getLegalMoves(new Location(4, 4));
        assertEquals(expected, actual);
    }

    // M42
    @Test
    public void GetLegalMoves_Pawn_OpponentDirectlyAhead_NotCapture() {
        Game game = gameWith(
                square(4, 4, new Pawn(PieceColor.WHITE)),
                square(4, 3, new Pawn(PieceColor.BLACK))
        );
        Set<Location> actual = game.getLegalMoves(new Location(4, 4));
        assertEquals(Set.of(), actual);
    }

    // M43
    @Test
    public void GetLegalMoves_Pawn_OnLeftEdge_NoOffBoardDiagonal() {
        Game game = gameWith(
                square(0, 4, new Pawn(PieceColor.WHITE)),
                square(1, 3, new Pawn(PieceColor.BLACK))
        );
        Set<Location> expected = Set.of(new Location(0, 3), new Location(1, 3));
        Set<Location> actual = game.getLegalMoves(new Location(0, 4));
        assertEquals(expected, actual);
    }

    // M13
    @Test
    public void GetLegalMoves_Knight_PathBlocked_ReturnTwo() {
        Game game = gameOnStandardBoard();
        Set<Location> expected = Set.of(new Location(0, 5), new Location(2, 5));
        Set<Location> actual = game.getLegalMoves(new Location(1, 7));
        assertEquals(expected, actual);
    }

    // M19
    @Test
    public void GetLegalMoves_Knight_NoValidDestinations_ReturnEmpty() {
        Game game = gameWith(
                square(0, 0, new Knight(PieceColor.WHITE)),
                square(1, 2, new Rook(PieceColor.WHITE)),
                square(2, 1, new Bishop(PieceColor.WHITE))
        );
        Set<Location> actual = game.getLegalMoves(new Location(0, 0));
        assertEquals(Set.of(), actual);
    }

    // M20
    @Test
    public void GetLegalMoves_Knight_OpponentOnDestination_ReturnIncludingOpponent() {
        Game game = gameWith(
                square(3, 4, new Knight(PieceColor.WHITE)),
                square(5, 3, new Pawn(PieceColor.BLACK))
        );
        Set<Location> expected = Set.of(
                new Location(1, 3),
                new Location(1, 5),
                new Location(2, 2),
                new Location(2, 6),
                new Location(4, 2),
                new Location(4, 6),
                new Location(5, 3),
                new Location(5, 5)
        );
        Set<Location> actual = game.getLegalMoves(new Location(3, 4));
        assertEquals(expected, actual);
    }

    // M10
    @Test
    public void GetLegalMoves_Bishop_Blocked_ReturnEmpty() {
        Game game = gameWith(
                square(2, 7, new Bishop(PieceColor.WHITE)),
                square(1, 6, new Pawn(PieceColor.WHITE)),
                square(3, 6, new Pawn(PieceColor.WHITE))
        );
        Set<Location> actual = game.getLegalMoves(new Location(2, 7));
        assertEquals(Set.of(), actual);
    }

    // M12
    @Test
    public void GetLegalMoves_Bishop_OpponentInDiagonal_ReturnUpToOpponent() {
        Game game = gameWith(
                square(2, 7, new Bishop(PieceColor.WHITE)),
                square(4, 5, new Pawn(PieceColor.BLACK))
        );
        Set<Location> expected = Set.of(
                new Location(0, 5),
                new Location(1, 6),
                new Location(3, 6),
                new Location(4, 5)
        );
        Set<Location> actual = game.getLegalMoves(new Location(2, 7));
        assertEquals(expected, actual);
    }

    // M6
    @Test
    public void GetLegalMoves_Rook_ReturnEmpty() {
        Game game = gameWith(
                square(0, 7, new Rook(PieceColor.WHITE)),
                square(0, 6, new Pawn(PieceColor.WHITE)),
                square(1, 7, new Knight(PieceColor.WHITE))
        );

        Set<Location> actual = game.getLegalMoves(new Location(0, 7));

        assertEquals(Set.of(), actual);
    }

    // M7
    @Test
    public void GetLegalMoves_Rook_AdjacentTilesUnblocked_ReturnAdjacent() {
        Game game = standardGameWith(
                cleared(7, 6),
                cleared(6, 7),
                square(7, 4, new Pawn(PieceColor.WHITE)),
                square(5, 5, new Knight(PieceColor.WHITE))
        );

        Set<Location> expected = Set.of(
                new Location(7, 6),
                new Location(7, 5),
                new Location(6, 7)
        );
        Set<Location> actual = game.getLegalMoves(new Location(7, 7));

        assertEquals(expected, actual);
    }

    // M9
    @Test
    public void GetLegalMoves_Rook_BlockedByOpponent_ReturnUpToOpponent() {
        Game game = gameWith(
                square(7, 7, new Rook(PieceColor.WHITE)),
                square(7, 3, new Pawn(PieceColor.BLACK)),
                square(6, 7, new Pawn(PieceColor.WHITE))
        );

        Set<Location> expected = Set.of(
                new Location(7, 6),
                new Location(7, 5),
                new Location(7, 4),
                new Location(7, 3)
        );
        Set<Location> actual = game.getLegalMoves(new Location(7, 7));

        assertEquals(expected, actual);
    }

    // M16
    @Test
    public void GetLegalMoves_Queen_BoxedIn_ReturnEmpty() {
        Game game = gameOnStandardBoard();

        Set<Location> actual = game.getLegalMoves(new Location(3, 7));

        assertEquals(Set.of(), actual);
    }

    // M17
    @Test
    public void GetLegalMoves_Queen_AdjacentsCleared_ReturnAdjacent() {
        Game game = standardGameWith(
                cleared(3, 6),
                square(3, 4, new Pawn(PieceColor.WHITE))
        );

        Set<Location> expected = Set.of(new Location(3, 6), new Location(3, 5));
        Set<Location> actual = game.getLegalMoves(new Location(3, 7));

        assertEquals(expected, actual);
    }

    // M23
    @Test
    public void GetLegalMoves_Queen_OpponentInFile_ReturnUpToOpponent() {
        Game game = standardGameWith(
                cleared(3, 6),
                square(3, 3, new Pawn(PieceColor.BLACK))
        );

        Set<Location> expected = Set.of(
                new Location(3, 6),
                new Location(3, 5),
                new Location(3, 4),
                new Location(3, 3)
        );
        Set<Location> actual = game.getLegalMoves(new Location(3, 7));

        assertEquals(expected, actual);
    }

    // M14
    @Test
    public void GetLegalMoves_King_BoxedIn_ReturnEmpty() {
        Game game = gameOnStandardBoard();

        Set<Location> actual = game.getLegalMoves(new Location(4, 7));

        assertEquals(Set.of(), actual);
    }

    // M15
    @Test
    public void GetLegalMoves_King_OneNeighbourFreed_ReturnOne() {
        Game game = standardGameWith(
                cleared(4, 6),
                square(4, 4, new Pawn(PieceColor.WHITE))
        );

        Set<Location> expected = Set.of(new Location(4, 6));
        Set<Location> actual = game.getLegalMoves(new Location(4, 7));

        assertEquals(expected, actual);
    }

    // M21
    @Test
    public void GetLegalMoves_King_OpponentAdjacent_ReturnIncludingOpponent() {
        Game game = standardGameWith(
                square(4, 6, new Pawn(PieceColor.BLACK))
        );

        Set<Location> expected = Set.of(new Location(4, 6));
        Set<Location> actual = game.getLegalMoves(new Location(4, 7));

        assertEquals(expected, actual);
    }

    // M82
    @Test
    public void GetLegalMoves_Pawn_OnRightEdge_NoOffBoardDiagonal() {
        Game game = gameWith(
                square(7, 4, new Pawn(PieceColor.WHITE)),
                square(6, 3, new Pawn(PieceColor.BLACK))
        );

        Set<Location> expected = Set.of(new Location(7, 3), new Location(6, 3));
        Set<Location> actual = game.getLegalMoves(new Location(7, 4));

        assertEquals(expected, actual);
    }

    // M83
    @Test
    public void GetLegalMoves_WhitePawn_OnTopEdge_NoOffBoardDiagonal() {
        Game game = gameWith(
                square(4, 0, new Pawn(PieceColor.WHITE))
        );

        Set<Location> expected = Set.of();
        Set<Location> actual = game.getLegalMoves(new Location(4, 0));

        assertEquals(expected, actual);
    }

    // M84
    @Test
    public void GetLegalMoves_BlackPawn_OnBottomEdge_NoOffBoardDiagonal() {
        Game game = gameWith(
                square(4, 7, new Pawn(PieceColor.BLACK))
        );

        Set<Location> expected = Set.of();
        Set<Location> actual = game.getLegalMoves(new Location(4, 7));

        assertEquals(expected, actual);
    }

    // G2
    @Test
    public void MovePiece_ApprovedMove_DelegateToBoardAndKeepTurn() {
        Board board = createMock(Board.class);
        Location from = new Location(4, 6);
        Location to = new Location(4, 4);

        board.movePiece(from, to);
        replay(board);

        Game game = new Game(board);

        game.movePiece(from, to);

        PieceColor expectedTurn = PieceColor.WHITE;
        PieceColor actualTurn = game.getCurrentTurn();

        assertEquals(expectedTurn, actualTurn);
        verify(board);
    }

    // G3
    @Test
    public void SwitchTurn_White_ReturnBlack() {
        Game game = new Game();

        game.switchTurn();

        PieceColor expected = PieceColor.BLACK;
        PieceColor actual = game.getCurrentTurn();
        assertEquals(expected, actual);
    }

    // G4
    @Test
    public void SwitchTurn_Black_ReturnWhite() {
        Game game = new Game();
        game.switchTurn();

        game.switchTurn();

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = game.getCurrentTurn();
        assertEquals(expected, actual);
    }

    // G6
    @Test
    public void GetBoardSnapshot_AfterMove_ReturnUpdatedBoard() {
        Game game = new Game();
        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        game.movePiece(from, to);

        Piece[][] snapshot = game.getBoardSnapshot();

        assertNull(snapshot[6][4]);
        assertNotNull(snapshot[5][4]);
        assertEquals(
                PieceType.PAWN,
                snapshot[5][4].getType()
        );
        assertEquals(
                PieceColor.WHITE,
                snapshot[5][4].getColor()
        );
    }

    private static Square square(int column, int row, Piece piece) {
        return new Square(column, row, piece);
    }

    private static Square cleared(int column, int row) {
        return new Square(column, row, null);
    }

    private static Game gameOnStandardBoard() {
        return gameOn(new Board().getSnapshot());
    }

    private static Game gameWith(Square... placements) {
        return gameOn(placeOn(new Piece[8][8], placements));
    }

    private static Game standardGameWith(Square... placements) {
        return gameOn(placeOn(new Board().getSnapshot(), placements));
    }

    private static Game gameOn(Piece[][] boardState) {
        Board board = createMock(Board.class);
        expect(board.getSnapshot()).andReturn(boardState).anyTimes();
        replay(board);
        return new Game(board);
    }

    private static Piece[][] placeOn(Piece[][] boardState, Square... placements) {
        for (Square placement : placements) {
            int row = placement.location.getRow();
            int column = placement.location.getColumn();
            boardState[row][column] = placement.piece;
        }
        return boardState;
    }

    private static final class Square {
        private final Location location;
        private final Piece piece;

        private Square(int column, int row, Piece piece) {
            this.location = new Location(column, row);
            this.piece = piece;
        }
    }
}
