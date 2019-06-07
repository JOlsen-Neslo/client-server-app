package za.co.neslotech.server;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class ServerApplicationTest {

    @Rule
    public final ExpectedSystemExit normalExit = ExpectedSystemExit.none();

    @Ignore
    public void testMain() {
        normalExit.expectSystemExit();
        ServerApplication.main(null);
    }

}
