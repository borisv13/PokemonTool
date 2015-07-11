import com.boris.pokemontool.Coin;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Boris on 7/11/2015.
 */
public class CoinTest extends TestCase {

    @Test
    public void testToss() throws Exception {
        Coin coin = new Coin();
        int actual = coin.toss();
        assertTrue(actual == 0 || actual == 1);
    }
}