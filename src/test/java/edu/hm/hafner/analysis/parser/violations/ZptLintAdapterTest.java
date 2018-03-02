package edu.hm.hafner.analysis.parser.violations;

import edu.hm.hafner.analysis.AbstractParser;
import edu.hm.hafner.analysis.AbstractParserTest;
import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.Issues;
import edu.hm.hafner.analysis.Priority;
import edu.hm.hafner.analysis.assertj.SoftAssertions;

/**
 * Tests the class {@link ZptLintAdapter}.
 *
 * @author Ullrich Hafner
 */
class ZptLintAdapterTest extends AbstractParserTest<Issue> {
    ZptLintAdapterTest() {
        super("zptlint.log");
    }

    @Override
    protected void assertThatIssuesArePresent(final Issues<Issue> issues, final SoftAssertions softly) {
        softly.assertThat(issues).hasSize(2);
        softly.assertThat(issues.get(0))
                .hasMessage("abc def ghe '\" 123")
                .hasFileName("cpplint.py")
                .hasLineStart(4796)
                .hasPriority(Priority.HIGH);
    }

    @Override
    protected AbstractParser<Issue> createParser() {
        return new ZptLintAdapter();
    }
}