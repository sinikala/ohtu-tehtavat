package ohtuesimerkki;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.*;



public class StatisticsTest {

    double delta = 0.001;
    Reader readerStub = new Reader() {

        public List<Player> getPlayers() {
            ArrayList<Player> players = new ArrayList<>();

            players.add(new Player("Semenko", "EDM", 4, 12));
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Kurri",   "EDM", 37, 53));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Gretzky", "EDM", 35, 89));

            return players;
        }
    };

    Statistics stats;

    @Before
    public void setUp(){
        // luodaan Statistics-olio joka käyttää "stubia"
        stats = new Statistics(readerStub);
    }

    @Test
    public void searchReturnsCorrectPlayer(){
        assertEquals("Yzerman", stats.search("Yzerman").getName());
        assertEquals("DET", stats.search("Yzerman").getTeam());
    }

    @Test
    public void searchReturnsNullWithNonExistentName(){
        assertNull(stats.search("Player"));
    }

    @Test
    public void teamReturnsRightAmountOfPlayers(){
        assertEquals(3, stats.team("EDM").size(), delta);
    }

    @Test
    public void teamReturnsEmptyListWithNonExistentTeam(){
        assertEquals(0, stats.team("ABC").size(), delta);
    }

    @Test
    public void topScorersReturnCorrectNumberOfPlayers(){
        System.out.println(stats.topScorers(2));
        assertEquals(3, stats.topScorers(2).size(), delta);
    }
}
