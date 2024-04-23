package uta.cse3310;
import junit.framework.TestCase;

public class playerTypeTest {
   public void testPlayerTypeValues() {
      assertEquals("NOPLAYER", PlayerType.NOPLAYER.toString());
      assertEquals("PLAYERONE", PlayerType.PLAYERONE.toString());
      assertEquals("PLAYERTWO", PlayerType.PLAYERTWO.toString());
      assertEquals("PLAYERTHREE", PlayerType.PLAYERTHREE.toString());
      assertEquals("PLAYERFOUR", PlayerType.PLAYERFOUR.toString());
  }

  public void testPlayerTypeOrdinal() {
      assertEquals(0, PlayerType.NOPLAYER.ordinal());
      assertEquals(1, PlayerType.PLAYERONE.ordinal());
      assertEquals(2, PlayerType.PLAYERTWO.ordinal());
      assertEquals(3, PlayerType.PLAYERTHREE.ordinal());
      assertEquals(4, PlayerType.PLAYERFOUR.ordinal());
  }

  public void testPlayerTypeEquality() {
      assertEquals(PlayerType.NOPLAYER, PlayerType.NOPLAYER);
      assertEquals(PlayerType.PLAYERONE, PlayerType.PLAYERONE);
      assertEquals(PlayerType.PLAYERTWO, PlayerType.PLAYERTWO);
      assertEquals(PlayerType.PLAYERTHREE, PlayerType.PLAYERTHREE);
      assertEquals(PlayerType.PLAYERFOUR, PlayerType.PLAYERFOUR);

      assertNotSame(PlayerType.PLAYERONE, PlayerType.PLAYERTWO);
      assertNotSame(PlayerType.PLAYERONE, PlayerType.PLAYERTHREE);
      assertNotSame(PlayerType.PLAYERONE, PlayerType.PLAYERFOUR);
      assertNotSame(PlayerType.PLAYERTWO, PlayerType.PLAYERTHREE);
      assertNotSame(PlayerType.PLAYERTWO, PlayerType.PLAYERFOUR);
      assertNotSame(PlayerType.PLAYERTHREE, PlayerType.PLAYERFOUR);
  }
}

