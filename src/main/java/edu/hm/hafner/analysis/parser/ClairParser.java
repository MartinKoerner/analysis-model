package edu.hm.hafner.analysis.parser;

import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;
import edu.umd.cs.findbugs.annotations.CheckForNull;

/**
 * A parser for clair scanner json output.
 * <p>
 * See <a href='https://github.com/arminc/clair-scanner'>clair-scanner</a> for project details.
 *
 * @author Andreas Mandel
 */
public class ClairParser extends JsonIssueParser {
    private static final long serialVersionUID = 371390072777545322L;

    @Override
    protected void parseJsonObject(final Report report, final JSONObject jsonReport, final IssueBuilder issueBuilder) {
        String image = optStringIgnoreCase(jsonReport, "image");
        JSONArray vulnerabilities = optJsonArrayIgnoreCase(jsonReport, "vulnerabilities");
        for (Object vulnerability : vulnerabilities) {
            if (vulnerability instanceof JSONObject) {
                report.add(convertToIssue((JSONObject) vulnerability, image, issueBuilder));
            }
        }
    }

    private Issue convertToIssue(final JSONObject jsonIssue, @CheckForNull final String image,
            final IssueBuilder issueBuilder) {
        StringBuilder message = new StringBuilder();
        appendIfNotEmpty(jsonIssue, message, "featurename", "");
        appendIfNotEmpty(jsonIssue, message, "featureversion", ":");
        appendIfNotEmpty(jsonIssue, message, "description", "");
        appendIfNotEmpty(jsonIssue, message, "fixedby", "Fixed by ");
        appendIfNotEmpty(jsonIssue, message, "link", "see ");
        return issueBuilder
                .setMessage(message.toString())
                .setCategory(optStringIgnoreCase(jsonIssue, "vulnerability"))
                .setSeverity(toSeverity(optStringIgnoreCase(jsonIssue, "severity")))
                .setType(optStringIgnoreCase(jsonIssue, "namespace"))
                .setFileName(image).build();
    }

    private void appendIfNotEmpty(final JSONObject issue, final StringBuilder message, final String key,
            final String head) {
        final String text = optStringIgnoreCase(issue, key);
        if (text != null && !text.isEmpty()) {
            if (message.length() > 0 && !":".equals(head)) {
                message.append(' ');
            }
            message.append(head).append(text);
        }
    }

    private Severity toSeverity(@CheckForNull final String level) {
        switch (String.valueOf(level).toLowerCase(Locale.ENGLISH)) {
            case "defcon1":
                return Severity.ERROR;
            case "critical":
                return Severity.WARNING_HIGH;
            case "high":
                return Severity.WARNING_NORMAL;
            default:
                return Severity.WARNING_LOW;
        }
    }

    private JSONArray optJsonArrayIgnoreCase(final JSONObject json, final String searchKey) {
        final Object result = optIgnoreCase(json, searchKey);
        return result instanceof JSONArray ? (JSONArray) result : new JSONArray();
    }

    @CheckForNull
    private String optStringIgnoreCase(final JSONObject json, final String searchKey) {
        final Object result = optIgnoreCase(json, searchKey);
        return result instanceof String ? (String) result : null;
    }

    @CheckForNull
    private Object optIgnoreCase(final JSONObject json, final String searchKey) {
        Object result = json.opt(searchKey);
        if (result == null) {
            result = searchIgnoreCase(json, searchKey);
        }
        return result;
    }

    @CheckForNull
    private Object searchIgnoreCase(final JSONObject json, final String searchKey) {
        Object result = null;
        for (String key : json.keySet()) {
            if (key.equalsIgnoreCase(searchKey)) {
                result = json.opt(key);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }
}
