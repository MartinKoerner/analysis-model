package edu.hm.hafner.analysis.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.hm.hafner.analysis.Issue;
import edu.hm.hafner.analysis.IssueBuilder;
import edu.hm.hafner.analysis.Report;
import edu.hm.hafner.analysis.Severity;

/**
 * A parser for Brakeman JSON output.
 * <p>
 * See <a href='https://brakemanscanner.org'>Brakeman</a> for project details.
 *
 * @author Justin Collins
 */
public class BrakemanParser extends JsonIssueParser {
    private static final long serialVersionUID = 1374428573878091300L;

    @Override
    protected void parseJsonObject(final Report report, final JSONObject jsonReport,
            final IssueBuilder issueBuilder) {
        JSONArray warnings = jsonReport.getJSONArray("warnings");
        for (Object warning : warnings) {
            report.add(convertToIssue((JSONObject) warning, issueBuilder));
        }
    }

    private Issue convertToIssue(final JSONObject warning, final IssueBuilder issueBuilder) throws JSONException {
        String fileName = warning.getString("file");
        String category = warning.getString("warning_type");
        Severity severity = getSeverity(warning.getString("confidence"));
        String fingerprint = warning.getString("fingerprint");
        String warningType = warning.getString("check_name");
        StringBuilder message = new StringBuilder();
        message.append(warning.getString("message"));

        if (warning.has("code")) {
            String code = warning.optString("code", "");

            if (!code.isEmpty()) {
                message.append(": ").append(warning.getString("code"));
            }
        }

        int line = warning.optInt("line", 1);

        return issueBuilder
            .setMessage(message.toString())
            .setCategory(category)
            .setType(warningType)
            .setSeverity(severity)
            .setFileName(fileName)
            .setLineStart(line)
            .setFingerprint(fingerprint)
            .build();
    }

    private Severity getSeverity(final String confidence) {
        if ("Medium".equalsIgnoreCase(confidence)) {
            return Severity.WARNING_NORMAL;
        }
        else if ("High".equalsIgnoreCase(confidence)) {
            return Severity.WARNING_HIGH;
        }
        else if ("Weak".equalsIgnoreCase(confidence)) {
            return Severity.WARNING_LOW;
        }
        else {
            return Severity.WARNING_HIGH;
        }
    }
}
