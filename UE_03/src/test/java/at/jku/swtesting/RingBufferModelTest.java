package at.jku.swtesting;
import org.junit.jupiter.api.Test;
import nz.ac.waikato.modeljunit.RandomTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.Tester;
import nz.ac.waikato.modeljunit.VerboseListener;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionCoverage;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;

class RingBufferModelTest {

	@Test
	void testRingBufferModel() {
		Tester tester = new RandomTester(new RingBufferModel());
		
		tester.buildGraph();
		tester.addListener(new VerboseListener());
		tester.addListener(new StopOnFailureListener());
        tester.addCoverageMetric(new ActionCoverage());
        tester.addCoverageMetric(new StateCoverage());
		tester.addCoverageMetric(new TransitionCoverage());
		
		tester.generate(2000);
		tester.printCoverage();
	}

}
