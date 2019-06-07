package za.co.neslotech.client;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class ClientApplicationTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Ignore
    public void testMain() {
        exit.expectSystemExit();
        ClientApplication.main(null);
    }

}