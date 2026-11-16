package controller;

import domain.Board;
import domain.Game;
import domain.Location;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.piece.PieceColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BoardControllerTest {

    private static final Location WHITE_PAWN = new Location(4, 6);
    private static final Set<Location> WHITE_PAWN_MOVES = Set.of(new Location(4, 5), new Location(4, 4));

    private Game mockGame;
    private BoardController controller;

    @BeforeEach
    public void setUp(){
        mockGame = createMock(Game.class);
        controller = new BoardController(mockGame);
    }

    private void expectStartingBoard(PieceColor currentTurn){
        expect(mockGame.getBoardSnapshot()).andReturn(new Board().getSnapshot()).anyTimes();
        expect(mockGame.getCurrentTurn()).andReturn(currentTurn).anyTimes();
    }

    private void expectWhitePawnCanBeSelected(){
        expectStartingBoard(PieceColor.WHITE);
        expect(mockGame.getLegalMoves(WHITE_PAWN)).andReturn(WHITE_PAWN_MOVES);
    }

    private void assertSelection(Location expectedSquare, Set<Location> expectedHighlights){
        assertEquals(expectedSquare, controller.getSelectedSquare());
        assertEquals(expectedHighlights, controller.getHighlightedSquares());
    }

    private void assertNothingSelected(){
        assertSelection(null, Set.of());
    }

    @Test
    public void GetBoardSnapshot_FromGame_ReturnInitializedBoard(){
        Game game = new Game();
        BoardController controller = new BoardController(game);

        Piece[][] snapshot = controller.getBoardSnapshot();

        int expectedRows = 8;
        int actualRows = snapshot.length;

        assertEquals(expectedRows, actualRows);
    }

    @Test
    public void GetCurrentTurn_OnNewGame_ReturnWhite(){
        Game game = new Game();
        BoardController controller = new BoardController(game);

        PieceColor expected = PieceColor.WHITE;
        PieceColor actual = controller.getCurrentTurn();

        assertEquals(expected, actual);
    }

    @Test
    public void GetSelectedSquare_NoClick_ReturnNull(){
        replay(mockGame);

        Location actual = controller.getSelectedSquare();

        assertNull(actual);
        verify(mockGame);
    }

    @Test
    public void GetHighlightedSquares_NoSelection_ReturnEmpty(){
        replay(mockGame);

        Set<Location> actual = controller.getHighlightedSquares();

        assertEquals(Set.of(), actual);
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_SelectOwnPawn_NoPriorSelection_NewSelection(){
        expectWhitePawnCanBeSelected();
        replay(mockGame);

        controller.handleSquareClick(WHITE_PAWN);

        assertSelection(WHITE_PAWN, WHITE_PAWN_MOVES);
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_SelectOwnPiece_NoValidDestinations_NoHighlights(){
        Location boxedInRook = new Location(0, 7);
        expectStartingBoard(PieceColor.WHITE);
        expect(mockGame.getLegalMoves(boxedInRook)).andReturn(Set.of());
        replay(mockGame);

        controller.handleSquareClick(boxedInRook);

        assertSelection(boxedInRook, Set.of());
        verify(mockGame);
    }

    @Test
    public void GetHighlightedSquares_SelectionWithExactlyOneMove_ReturnOne(){
        Location king = new Location(4, 7);
        Set<Location> kingMoves = Set.of(new Location(4, 6));
        expectStartingBoard(PieceColor.WHITE);
        expect(mockGame.getLegalMoves(king)).andReturn(kingMoves);
        replay(mockGame);

        controller.handleSquareClick(king);

        assertSelection(king, kingMoves);
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_SelectEmptySquare_NoPriorSelection_NoChange(){
        expectStartingBoard(PieceColor.WHITE);
        replay(mockGame);

        controller.handleSquareClick(new Location(4, 4));

        assertNothingSelected();
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_SelectOpponentPiece_NoPriorSelection_NoChange(){
        expectStartingBoard(PieceColor.WHITE);
        replay(mockGame);

        controller.handleSquareClick(new Location(4, 1));

        assertNothingSelected();
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_BlackTurn_SelectBlackPiece(){
        Location blackPawn = new Location(4, 1);
        Set<Location> blackPawnMoves = Set.of(new Location(4, 2), new Location(4, 3));
        expectStartingBoard(PieceColor.BLACK);
        expect(mockGame.getLegalMoves(blackPawn)).andReturn(blackPawnMoves);
        replay(mockGame);

        controller.handleSquareClick(blackPawn);

        assertSelection(blackPawn, blackPawnMoves);
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_MoveToHighlightedSquare_MoveCompletedAndTurnProgresses(){
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

    @Test
    public void HandleSquareClick_MoveToHighlightedCaptureSquare_MoveCompletedAndTurnProgresses(){
        Location whitePawn = new Location(4, 4);
        Location blackPawn = new Location(5, 3);
        Piece[][] snapshot = new Piece[8][8];
        snapshot[4][4] = new Pawn(PieceColor.WHITE);
        snapshot[3][5] = new Pawn(PieceColor.BLACK);
        expect(mockGame.getBoardSnapshot()).andReturn(snapshot).anyTimes();
        expect(mockGame.getCurrentTurn()).andReturn(PieceColor.WHITE).anyTimes();
        expect(mockGame.getLegalMoves(whitePawn)).andReturn(Set.of(new Location(4, 3), blackPawn));
        mockGame.movePiece(whitePawn, blackPawn);
        mockGame.switchTurn();
        replay(mockGame);
        controller.handleSquareClick(whitePawn);

        controller.handleSquareClick(blackPawn);

        assertNothingSelected();
        verify(mockGame);
    }

    @Test
    public void HandleSquareClick_ClickUnhighlightedEmptySquare_ClearSelection(){
        expectWhitePawnCanBeSelected();
        replay(mockGame);
        controller.handleSquareClick(WHITE_PAWN);

        controller.handleSquareClick(new Location(6, 5));

        assertNothingSelected();
        verify(mockGame);
    }
}