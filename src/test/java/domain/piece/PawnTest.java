package domain.piece;



import domain.Location;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class PawnTest {

    @Test
    public void MakeCopy_WhitePawn_ReturnEquivalentDistinctPawn(){
        Pawn pawn = new Pawn(PieceColor.WHITE);

        Piece copy = pawn.makeCopy();

        assertNotSame(pawn, copy);
        assertEquals(PieceType.PAWN, copy.getType());
        assertEquals(PieceColor.WHITE, copy.getColor());
    }

    @Test
    public void MakeCopy_BlackPawn_ReturnEquivalentDistinctPawn(){
        Pawn pawn = new Pawn(PieceColor.BLACK);

        Piece copy = pawn.makeCopy();

        assertNotSame(pawn, copy);
        assertEquals(PieceType.PAWN, copy.getType());
        assertEquals(PieceColor.BLACK, copy.getColor());
    }


    @Test
    public void GetValidMoves_Pawn_OnStartRow_ReturnTwo(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(4, 6);

        Set<Location> expected = Set.of(new Location(4, 5), new Location(4, 4));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_OffStartRow_ReturnOne(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(4, 4);

        Set<Location> expected = Set.of(new Location(4, 3));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_WhiteJustPastStartRow_ReturnOne(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(4, 5);

        Set<Location> expected = Set.of(new Location(4, 4));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_OneStepFromLastRank_ReturnLastRank(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(4, 1);

        Set<Location> expected = Set.of(new Location(4, 0));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_WhiteOnLeftEdgeStartRow_ReturnTwo(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(0, 6);

        Set<Location> expected = Set.of(new Location(0, 5), new Location(0, 4));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_Black_ReturnTwo(){
        Pawn pawn = new Pawn(PieceColor.BLACK);
        Location from = new Location(4, 1);

        Set<Location> expected = Set.of(new Location(4, 2), new Location(4, 3));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_BlackOffStartRow_ReturnOne(){
        Pawn pawn = new Pawn(PieceColor.BLACK);
        Location from = new Location(4, 3);

        Set<Location> expected = Set.of(new Location(4, 4));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_BlackJustPastStartRow_ReturnOne(){
        Pawn pawn = new Pawn(PieceColor.BLACK);
        Location from = new Location(4, 2);

        Set<Location> expected = Set.of(new Location(4, 3));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }


    @Test
    public void GetValidMoves_Pawn_BlackOneStepFromLastRank_ReturnLastRank(){
        Pawn pawn = new Pawn(PieceColor.BLACK);
        Location from = new Location(4, 6);

        Set<Location> expected = Set.of(new Location(4, 7));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_BlackOnRightEdgeStartRow_ReturnTwo(){
        Pawn pawn = new Pawn(PieceColor.BLACK);
        Location from = new Location(7, 1);

        Set<Location> expected = Set.of(new Location(7, 2), new Location(7, 3));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_WhiteOnLastRank_ReturnEmpty(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(4, 0);

        Set<Location> expected = Set.of();
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_BlackOnLastRank_ReturnEmpty(){
        Pawn pawn = new Pawn(PieceColor.BLACK);
        Location from = new Location(4, 7);

        Set<Location> expected = Set.of();
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_OneStepFromLastRankAtLeftEdge_ReturnOne(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(0, 1);

        Set<Location> expected = Set.of(new Location(0, 0));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }

    @Test
    public void GetValidMoves_Pawn_OneStepFromLastRankAtRightEdge_ReturnOne(){
        Pawn pawn = new Pawn(PieceColor.WHITE);
        Location from = new Location(7, 1);

        Set<Location> expected = Set.of(new Location(7, 0));
        Set<Location> actual = pawn.getValidMoves(from);

        assertEquals(expected, actual);
    }


}