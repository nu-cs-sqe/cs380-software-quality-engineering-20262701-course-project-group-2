package controller;

import domain.Board;
import domain.Game;
import domain.Location;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import domain.piece.PieceType;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardControllerTest {

    private static final Location WHITE_PAWN = new Location(4, 6);
    private static final Set<Location> WHITE_PAWN_MOVES =
            Set.of(new Location(4, 5), new Location(4, 4));

    private Game mockGame;
    private BoardController controller;

    @BeforeEach
    public void setUp() {
        mockGame = createMock(Game.class);
        controller = new BoardController(mockGame);
    }

    private void expectStartingBoard(PieceColor currentTurn) {
        expect(mockGame.getBoardSnapshot()).andReturn(new Board().getSnapshot()).anyTimes();
        expect(mockGame.getCurrentTurn()).andReturn(currentTurn).anyTimes();
    }

    private void expectWhitePawnCanBeSelected() {
        expectStartingBoard(PieceColor.WHITE);
        expect(mockGame.getLegalMoves(WHITE_PAWN)).andReturn(WHITE_PAWN_MOVES);
    }

    private void assertSelection(Location expectedSquare, Set<Location> expectedHighlights) {
        assertEquals(expectedSquare, controller.getSelectedSquare());
        assertEquals(expectedHighlights, controller.getHighlightedSquares());
    }

    private void assertNothingSelected() {
        assertSelection(null, Set.of());
    }

    // C25
    @Test
    public void GetBoardSnapshot_FromGame_ReturnInitializedBoard() {
        Game game = new Game();
        BoardController realController = new BoardController(game);

        Piece[][] snapshot = realController.getBoardSnapshot();

        assertEquals(8, snapshot.length);
        for (Piece[] row : snapshot) {
            assertEquals(8, row.length);
        }
    }

    // C18
    @Test
    public void GetCurrentTurn_OnNewGame_ReturnWhite() {
        Game game = new Game();
        BoardController realController = new BoardController(game);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = realController.getCurrentTurn();

        assertEquals(expected, actual);
    }

    // C12
    @Test
    public void GetSelectedSquare_NoClick_ReturnNull() {
        replay(mockGame);

        Location actual = controller.getSelectedSquare();

        assertNull(actual);
        verify(mockGame);
    }

    // C15
    @Test
    public void GetHighlightedSquares_NoSelection_ReturnEmpty() {
        replay(mockGame);

        Set<Location> actual = controller.getHighlightedSquares();

        assertEquals(Set.of(), actual);
        verify(mockGame);
    }

    // C1
    @Test
    public void HandleSquareClick_SelectOwnPawn_NoPriorSelection_NewSelection() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);

        controller.handleSquareClick(WHITE_PAWN);

        assertSelection(WHITE_PAWN, WHITE_PAWN_MOVES);
        verify(mockGame);
    }

    // C2
    @Test
    public void HandleSquareClick_SelectOwnPiece_NoValidDestinations_NoHighlights() {
        Location boxedInRook = new Location(0, 7);
        expectStartingBoard(PieceColor.WHITE);
        expect(mockGame.getLegalMoves(boxedInRook)).andReturn(Set.of());
        replay(mockGame);

        controller.handleSquareClick(boxedInRook);

        assertSelection(boxedInRook, Set.of());
        verify(mockGame);
    }

    // C23
    @Test
    public void GetHighlightedSquares_SelectionWithExactlyOneMove_ReturnOne() {
        Location king = new Location(4, 7);
        Set<Location> kingMoves = Set.of(new Location(4, 6));
        expectStartingBoard(PieceColor.WHITE);
        expect(mockGame.getLegalMoves(king)).andReturn(kingMoves);
        replay(mockGame);

        controller.handleSquareClick(king);

        assertSelection(king, kingMoves);
        verify(mockGame);
    }

    // C3
    @Test
    public void HandleSquareClick_SelectEmptySquare_NoPriorSelection_NoChange() {
        expectStartingBoard(PieceColor.WHITE);
        replay(mockGame);

        controller.handleSquareClick(new Location(4, 4));

        assertNothingSelected();
        verify(mockGame);
    }

    // C4
    @Test
    public void HandleSquareClick_SelectOpponentPiece_NoPriorSelection_NoChange() {
        expectStartingBoard(PieceColor.WHITE);
        replay(mockGame);

        controller.handleSquareClick(new Location(4, 1));

        assertNothingSelected();
        verify(mockGame);
    }

    // C24
    @Test
    public void HandleSquareClick_BlackTurn_SelectBlackPiece() {
        Location blackPawn = new Location(4, 1);
        Set<Location> blackPawnMoves = Set.of(new Location(4, 2), new Location(4, 3));
        expectStartingBoard(PieceColor.BLACK);
        expect(mockGame.getLegalMoves(blackPawn)).andReturn(blackPawnMoves);
        replay(mockGame);

        controller.handleSquareClick(blackPawn);

        assertSelection(blackPawn, blackPawnMoves);
        verify(mockGame);
    }

    // C5
    @Test
    public void HandleSquareClick_MoveToHighlightedSquare_MoveCompletedAndTurnProgresses() {
        Location destination = new Location(4, 5);
        expectWhitePawnCanBeSelected();
        mockGame.movePiece(WHITE_PAWN, destination);
        mockGame.switchTurn();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(destination);

        assertNothingSelected();
        verify(mockGame);
    }

    // C6
    @Test
    public void HandleSquareClick_MoveToHighlightedCaptureSquare_MoveCompletedAndTurnProgresses() {
        Piece[][] snapshot = new Piece[8][8];
        snapshot[4][4] = new Pawn(PieceColor.WHITE);
        snapshot[3][5] = new Pawn(PieceColor.BLACK);
        expect(mockGame.getBoardSnapshot()).andReturn(snapshot).anyTimes();
        expect(mockGame.getCurrentTurn()).andReturn(PieceColor.WHITE).anyTimes();
        Location whitePawn = new Location(4, 4);
        Location blackPawn = new Location(5, 3);
        expect(mockGame.getLegalMoves(whitePawn)).andReturn(Set.of(new Location(4, 3), blackPawn));
        mockGame.movePiece(whitePawn, blackPawn);
        mockGame.switchTurn();
        replay(mockGame);
        controller.handleSquareClick(whitePawn);

        controller.handleSquareClick(blackPawn);

        assertNothingSelected();
        verify(mockGame);
    }

    // C9
    @Test
    public void HandleSquareClick_ClickUnhighlightedEmptySquare_ClearSelection() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(6, 5));

        assertNothingSelected();
        verify(mockGame);
    }

    // C10
    @Test
    public void HandleSquareClick_ClickUnhighlightedOpponentPiece_ClearSelection() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(3, 1));

        assertNothingSelected();
        verify(mockGame);
    }

    // C8
    @Test
    public void HandleSquareClick_ClickOtherOwnPieceWhileSelected_NewSelection() {
        Location knight = new Location(1, 7);
        Set<Location> knightMoves = Set.of(new Location(0, 5), new Location(2, 5));
        expectWhitePawnCanBeSelected();
        expect(mockGame.getLegalMoves(knight)).andReturn(knightMoves);
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(knight);

        assertSelection(knight, knightMoves);
        verify(mockGame);
    }

    // C7
    @Test
    public void HandleSquareClick_ClickSelectedSquareAgain_ClearSelection() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(WHITE_PAWN);

        assertNothingSelected();
        verify(mockGame);
    }

    // C11
    @Test
    public void HandleSquareClick_ClickOutsideBoardUpperColumnWhileSelected_NoChange() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(8, 5));

        assertSelection(WHITE_PAWN, WHITE_PAWN_MOVES);
        verify(mockGame);
    }

    // C20
    @Test
    public void HandleSquareClick_ClickOutsideBoardLowerColumnWhileSelected_NoChange() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(-1, 5));

        assertSelection(WHITE_PAWN, WHITE_PAWN_MOVES);
        verify(mockGame);
    }

    // C21
    @Test
    public void HandleSquareClick_ClickOutsideBoardLowerRowWhileSelected_NoChange() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(5, -1));

        assertSelection(WHITE_PAWN, WHITE_PAWN_MOVES);
        verify(mockGame);
    }

    // C22
    @Test
    public void HandleSquareClick_ClickOutsideBoardUpperRowWhileSelected_NoChange() {
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(5, 8));

        assertSelection(WHITE_PAWN, WHITE_PAWN_MOVES);
        verify(mockGame);
    }

    // C27; also verifies C19 and C26
    @Test
    public void HandleSquareClick_RealGameOrdinaryMove_UpdateBoardTurnAndSelection() {
        Game game = new Game();
        BoardController realController = new BoardController(game);
        Location from = new Location(4, 6);
        Location to = new Location(4, 5);

        realController.handleSquareClick(from);
        realController.handleSquareClick(to);

        Piece[][] snapshot = realController.getBoardSnapshot();
        Piece movedPiece = snapshot[5][4];

        assertNull(snapshot[6][4]);
        assertNotNull(movedPiece);
        assertEquals(PieceType.PAWN, movedPiece.getType());
        assertEquals(PieceColor.WHITE, movedPiece.getColor());
        assertEquals(PieceColor.BLACK, realController.getCurrentTurn());
        assertNull(realController.getSelectedSquare());
        assertEquals(Set.of(), realController.getHighlightedSquares());
    }

    // C28
    @Test
    public void HandleSquareClick_RealGameCapture_UpdateBoardTurnAndPieceCount() {
        Game game = new Game();
        BoardController realController = new BoardController(game);

        Location whitePawnStart = new Location(4, 6);
        Location whitePawnAdvance = new Location(4, 4);
        Location blackPawnStart = new Location(5, 1);
        Location blackPawnAdvance = new Location(5, 3);

        realController.handleSquareClick(whitePawnStart);
        realController.handleSquareClick(whitePawnAdvance);

        realController.handleSquareClick(blackPawnStart);
        realController.handleSquareClick(blackPawnAdvance);

        realController.handleSquareClick(whitePawnAdvance);
        realController.handleSquareClick(blackPawnAdvance);

        Piece[][] snapshot = realController.getBoardSnapshot();
        Piece capturingPawn = snapshot[3][5];

        assertNull(snapshot[4][4]);
        assertNotNull(capturingPawn);
        assertEquals(PieceType.PAWN, capturingPawn.getType());
        assertEquals(PieceColor.WHITE, capturingPawn.getColor());

        int occupiedSquares = 0;

        for (Piece[] row : snapshot) {
            for (Piece piece : row) {
                if (piece != null) {
                    occupiedSquares++;
                }
            }
        }

        assertEquals(31, occupiedSquares);
        assertEquals(PieceColor.BLACK, realController.getCurrentTurn());
        assertNull(realController.getSelectedSquare());
        assertEquals(Set.of(), realController.getHighlightedSquares());
    }
}
