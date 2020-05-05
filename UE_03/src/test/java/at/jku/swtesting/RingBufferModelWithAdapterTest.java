package at.jku.swtesting;
import org.junit.jupiter.api.Test;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;

class RingBufferModelWithAdapterTest {

	@Test
	void testRingBufferModelWithAdapter() {
		Tester tester = new RandomTester(new RingBufferModelWithAdapter());
		tester.buildGraph();
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new TransitionCoverage());

		tester.addListener(new VerboseListener());
		tester.addListener(new StopOnFailureListener());
		
		tester.generate(3000);
		tester.printCoverage();
	}

}
