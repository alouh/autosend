import com.han.config.Config;
import com.han.send.Fetcher;
import com.han.send.impl.FetcherException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Hanjiafeng
 * @Date: 2019/8/4
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class FetcherTest {
    @Autowired
    Fetcher fetcher;

    @Test
    public void testV() throws FetcherException {
        fetcher.login();
    }
}
