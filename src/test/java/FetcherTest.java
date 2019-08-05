import com.han.config.Config;
import com.han.entity.Customer;
import com.han.send.Fetcher;
import com.han.send.impl.FetcherException;
import com.han.util.Painter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public void testV() throws FetcherException, IOException {
        fetcher.login();
        List<Customer> customers = fetcher.selectCustomers(1, 100, 1564934400, 1565020799, "");
        customers = customers.subList(0, 30);
        BufferedImage bufferedImage = Painter.paintCustomers(customers, 1920);
        ImageIO.write(bufferedImage, "PNG", new File("F://custom.png"));
    }
}
