
import org.junit.AfterClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.moqui.Moqui

@RunWith(Suite.class)
@Suite.SuiteClasses([ ReturnMagicTests.class ])
class BrandedonlineCoreSuite {
	@AfterClass
    public static void destroyMoqui() {
        Moqui.destroyActiveExecutionContextFactory()
    }
}
