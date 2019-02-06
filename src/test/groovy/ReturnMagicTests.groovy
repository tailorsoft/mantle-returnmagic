import groovy.json.JsonOutput
import org.moqui.Moqui
import org.moqui.context.ExecutionContext
import org.moqui.entity.EntityFind
import org.moqui.entity.EntityValue
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import spock.lang.Shared
import spock.lang.Specification
import groovy.json.JsonSlurper
import spock.lang.Unroll


class ReturnMagicTests extends Specification {
    protected final static Logger logger = LoggerFactory.getLogger(ReturnMagicTests.class)
    //protected Map 

    @Shared
    ExecutionContext ec

    def setupSpec() {
        ec = Moqui.getExecutionContext()
    }

    def cleanupSpec() {
        ec.destroy()
    }

    def setup() {
        ec.user.loginUser("john.doe", "moqui")
        ec.artifactExecution.disableAuthz()

        ec.transaction.begin(null)
    }

    def cleanup() {
        ec.transaction.commit()
        ec.artifactExecution.enableAuthz()
        ec.user.logoutUser()
    }

    def "Test of get returns service"() {
        given:
            def systemMessageRemoteId = "SpyReturnMagicRemote"
            def uri = "returns.json"
            def stateParam = "APPROVED"
        when:
            Map bodyText = ec.service.sync().name("mantle.returnmagic.ReturnMagicServices.get#Returns")
                .parameters([systemMessageRemoteId: systemMessageRemoteId, uri: uri, stateParam: stateParam])
                .call()
            //logger.info("\n===== The output of returns is =====\n${bodyText}")
        then:
            bodyText != null
    }

    def "Test of get return details service"() {
        given:
            def systemMessageRemoteId = "SpyReturnMagicRemote"
            def uri = "returns.json"
            def id = "b5faa3da-16d9-4a31-869b-86f5776e1ba9"
        when:
            Map bodyText = ec.service.sync().name("mantle.returnmagic.ReturnMagicServices.get#ReturnDetails")
                .parameters([systemMessageRemoteId: systemMessageRemoteId, uri: uri, id: id])
                .call()
            //logger.info("\n===== The output of returns details is =====\n${bodyText}")
        then:
            bodyText != null
    }

    def "Test of get approved returns service"() {
        given:
            def systemMessageRemoteId = "SpyReturnMagicRemote"
        when:
            Map moquiReturns = ec.service.sync().name("mantle.returnmagic.ReturnMagicServices.get#ApprovedReturns")
                .parameters([systemMessageRemoteId: systemMessageRemoteId])
                .call()
            //logger.info("\n===== The output of approved returns is =====\n${moquiReturns}")
        then:
            moquiReturns != null
    }

    def "Test of import returns service"() {
        given:
            def systemMessageRemoteId = "SpyReturnMagicRemote"
        when:
            ec.service.sync().name("mantle.returnmagic.ReturnMagicServices.import#ReturnMagicReturns")
                .parameters([systemMessageRemoteId: systemMessageRemoteId])
                .call()
        then:
            logger.info("\n===== The import returns service test end =====")
    }
}