package edu.hm.hafner.analysis.ast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import static edu.hm.hafner.analysis.assertions.Assertions.*;

/**
 * Base class for all {@link Ast} tests.
 *
 * @author Ullrich Hafner
 */
public abstract class AbstractAstTest {
// -------------------------------------------------------------------------------------------------------------------
// Overall AST of the file elements.ast-test
// -------------------------------------------------------------------------------------------------------------------
// PACKAGE_DEF ANNOTATIONS DOT DOT DOT IDENT IDENT IDENT IDENT SEMI IMPORT DOT DOT IDENT IDENT IDENT SEMI
// IMPORT DOT DOT IDENT IDENT IDENT SEMI
// IMPORT DOT DOT DOT DOT DOT IDENT IDENT IDENT IDENT IDENT IDENT SEMI
//
//   CLASS_DEF MODIFIERS LITERAL_PUBLIC LITERAL_CLASS IDENT EXTENDS_CLAUSE IDENT OBJBLOCK LCURLY
//       VARIABLE_DEF MODIFIERS LITERAL_PRIVATE LITERAL_STATIC FINAL TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI
//       VARIABLE_DEF MODIFIERS LITERAL_PRIVATE FINAL TYPE LITERAL_INT IDENT SEMI
//
//       CTOR_DEF MODIFIERS LITERAL_PUBLIC IDENT
//            LPAREN PARAMETERS
//                PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT COMMA
//                PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_INT IDENT
//            RPAREN
//       SLIST
//            CTOR_CALL LPAREN ELIST EXPR IDENT COMMA EXPR IDENT COMMA EXPR IDENT RPAREN SEMI
//       RCURLY

//       METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT
//            LPAREN PARAMETERS
//                PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT
//            RPAREN
//       SLIST
//            LITERAL_RETURN EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR LITERAL_TRUE RPAREN SEMI
//       RCURLY
//
//       METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT
//            LPAREN PARAMETERS
//                PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT
//            RPAREN
//       SLIST
//            LITERAL_RETURN EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR LITERAL_FALSE RPAREN SEMI
//       RCURLY
//
//       METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT
//            LPAREN PARAMETERS
//                PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT
//                COMMA PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_BOOLEAN IDENT
//            RPAREN
//        SLIST
//            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR METHOD_CALL DOT IDENT IDENT ELIST RPAREN SEMI
//            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI
//            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI
//            VARIABLE_DEF MODIFIERS TYPE IDENT IDENT ASSIGN EXPR METHOD_CALL IDENT ELIST RPAREN SEMI
//
//            VARIABLE_DEF MODIFIERS TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT ASSIGN EXPR LITERAL_NEW IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END LPAREN ELIST RPAREN SEMI
//
//            VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI
//            LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST
//                EXPR ASSIGN IDENT IDENT SEMI
//            RCURLY
//            LITERAL_ELSE SLIST
//                EXPR ASSIGN IDENT PLUS MINUS METHOD_CALL IDENT ELIST RPAREN IDENT NUM_INT SEMI
//            RCURLY
//            LITERAL_FOR LPAREN FOR_INIT VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI FOR_CONDITION EXPR LT IDENT IDENT SEMI FOR_ITERATOR ELIST EXPR POST_INC IDENT RPAREN SLIST
//                LITERAL_IF LPAREN EXPR LT IDENT IDENT RPAREN SLIST
//                    LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST
//                        EXPR ASSIGN IDENT MINUS IDENT IDENT SEMI
//                    RCURLY
//                    LITERAL_ELSE SLIST
//                        EXPR ASSIGN IDENT PLUS IDENT IDENT SEMI
//                    RCURLY
//                    EXPR METHOD_CALL IDENT ELIST RPAREN SEMI
//                    EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR IDENT RPAREN SEMI
//                    LITERAL_IF LPAREN EXPR LNOT METHOD_CALL DOT METHOD_CALL IDENT ELIST RPAREN IDENT ELIST RPAREN RPAREN SLIST
//                        EXPR POST_INC IDENT SEMI
//                        EXPR METHOD_CALL DOT IDENT IDENT ELIST EXPR METHOD_CALL IDENT ELIST RPAREN RPAREN SEMI
//                        EXPR METHOD_CALL IDENT ELIST RPAREN SEMI
//                    RCURLY
//                RCURLY
//                LITERAL_ELSE SLIST
//                    LITERAL_BREAK SEMI
//                RCURLY
//            RCURLY
//
//            LITERAL_RETURN EXPR IDENT SEMI
//        RCURLY

    static final String LINE1_PACKAGE = "PACKAGE_DEF ANNOTATIONS DOT DOT DOT IDENT IDENT IDENT IDENT SEMI ";
    static final String LINE3_IMPORT = "IMPORT DOT DOT IDENT IDENT IDENT SEMI ";
    static final String LINE4_IMPORT = "IMPORT DOT DOT IDENT IDENT IDENT SEMI ";
    static final String LINE6_IMPORT = "IMPORT DOT DOT DOT DOT DOT IDENT IDENT IDENT IDENT IDENT IDENT SEMI ";
    static final String LINE14_CLASS = "CLASS_DEF MODIFIERS LITERAL_PUBLIC LITERAL_CLASS IDENT EXTENDS_CLAUSE IDENT OBJBLOCK LCURLY ";
    static final String LINE16_FIELD = "VARIABLE_DEF MODIFIERS LITERAL_PRIVATE LITERAL_STATIC FINAL TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI ";
    static final String LINE18_FIELD = "VARIABLE_DEF MODIFIERS LITERAL_PRIVATE FINAL TYPE LITERAL_INT IDENT SEMI ";
    static final String LINE25_CTOR = "CTOR_DEF MODIFIERS LITERAL_PUBLIC IDENT LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT COMMA PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_INT IDENT RPAREN SLIST ";
    static final String LINE26_SUPER = "CTOR_CALL LPAREN ELIST EXPR IDENT COMMA EXPR IDENT COMMA EXPR IDENT RPAREN SEMI ";
    static final String LINE27_RCURLY = "RCURLY ";
    static final String LINE29_METHOD = "METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT RPAREN SLIST ";
    static final String LINE30_RETURN = "LITERAL_RETURN EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR LITERAL_TRUE RPAREN SEMI ";
    static final String LINE31_RCURLY = "RCURLY ";
    static final String LINE33_METHOD = "METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT RPAREN SLIST ";
    static final String LINE34_RETURN = "LITERAL_RETURN EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR LITERAL_FALSE RPAREN SEMI ";
    static final String LINE35_RCURLY = "RCURLY ";
    static final String LINE67_METHOD =
            "METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT "
                    + "LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE IDENT IDENT "
                    + "COMMA PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_BOOLEAN IDENT RPAREN SLIST ";
    static final String LINE68_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR METHOD_CALL DOT IDENT IDENT ELIST RPAREN SEMI ";
    static final String LINE69_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI ";
    static final String LINE70_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI ";
    static final String LINE71_VAR = "VARIABLE_DEF MODIFIERS TYPE IDENT IDENT ASSIGN EXPR METHOD_CALL IDENT ELIST RPAREN SEMI ";
    static final String LINE73_VAR = "VARIABLE_DEF MODIFIERS TYPE IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END IDENT ASSIGN EXPR LITERAL_NEW IDENT TYPE_ARGUMENTS GENERIC_START TYPE_ARGUMENT IDENT GENERIC_END LPAREN ELIST RPAREN SEMI ";
    static final String LINE75_VAR = "VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT SEMI ";
    static final String LINE76_IF = "LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST ";
    static final String LINE77_ASSIGN = "EXPR ASSIGN IDENT IDENT SEMI ";
    static final String LINE78_RCURLY = "RCURLY ";
    static final String LINE79_ELSE = "LITERAL_ELSE SLIST ";
    static final String LINE80_ASSIGN = "EXPR ASSIGN IDENT PLUS MINUS METHOD_CALL IDENT ELIST RPAREN IDENT NUM_INT SEMI ";
    static final String LINE81_RCURLY = "RCURLY ";
    static final String LINE82_FOR = "LITERAL_FOR LPAREN FOR_INIT VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI FOR_CONDITION EXPR LT IDENT IDENT SEMI FOR_ITERATOR ELIST EXPR POST_INC IDENT RPAREN SLIST ";
    static final String LINE83_IF = "LITERAL_IF LPAREN EXPR LT IDENT IDENT RPAREN SLIST ";
    static final String LINE84_IF = "LITERAL_IF LPAREN EXPR IDENT RPAREN SLIST ";
    static final String LINE85_ASSIGN = "EXPR ASSIGN IDENT MINUS IDENT IDENT SEMI ";
    static final String LINE86_RCURLY = "RCURLY ";
    static final String LINE87_ELSE = "LITERAL_ELSE SLIST ";
    static final String LINE88_ASSIGN = "EXPR ASSIGN IDENT PLUS IDENT IDENT SEMI ";
    static final String LINE89_RCURLY = "RCURLY ";
    static final String LINE90_CALL = "EXPR METHOD_CALL IDENT ELIST RPAREN SEMI ";
    static final String LINE91_CALL = "EXPR METHOD_CALL IDENT ELIST EXPR IDENT COMMA EXPR IDENT RPAREN SEMI ";
    static final String LINE92_IF = "LITERAL_IF LPAREN EXPR LNOT METHOD_CALL DOT METHOD_CALL IDENT ELIST RPAREN IDENT ELIST RPAREN RPAREN SLIST ";
    static final String LINE93_INCR = "EXPR POST_INC IDENT SEMI ";
    static final String LINE94_CALL = "EXPR METHOD_CALL DOT IDENT IDENT ELIST EXPR METHOD_CALL IDENT ELIST RPAREN RPAREN SEMI ";
    static final String LINE95_CALL = "EXPR METHOD_CALL IDENT ELIST RPAREN SEMI ";
    static final String LINE96_RCURLY = "RCURLY ";
    static final String LINE97_RCURLY = "RCURLY ";
    static final String LINE98_ELSE = "LITERAL_ELSE SLIST ";
    static final String LINE99_BREAK = "LITERAL_BREAK SEMI ";
    static final String LINE100_RCURLY = "RCURLY ";
    static final String LINE101_RCURLY = "RCURLY ";
    static final String LINE103_RETURN = "LITERAL_RETURN EXPR IDENT SEMI ";
    static final String LINE104_RCURLY = "RCURLY ";
    static final String LINE105_RCURLY = "RCURLY ";
    static final String LINE76_NESTED = "CLASS_DEF MODIFIERS LITERAL_PRIVATE LITERAL_STATIC LITERAL_CLASS IDENT OBJBLOCK LCURLY ";
    static final String LINE77_METHOD = "METHOD_DEF MODIFIERS LITERAL_PRIVATE TYPE LITERAL_VOID IDENT LPAREN PARAMETERS RPAREN SLIST ";
    static final String LINE78_METHOD_CALL =  "EXPR METHOD_CALL DOT DOT IDENT IDENT IDENT ELIST EXPR STRING_LITERAL RPAREN SEMI ";
    static final String LINE79_NESTED_METHOD_RCURLY =  "RCURLY ";
    static final String LINE80_NESTED_CLASS_RCURLY =  "RCURLY ";
    static final String NESTED = LINE76_NESTED + LINE77_METHOD + LINE78_METHOD_CALL + LINE79_NESTED_METHOD_RCURLY + LINE80_NESTED_CLASS_RCURLY;

    static final String WHOLE_METHOD = LINE67_METHOD + LINE68_VAR + LINE69_VAR
            + LINE70_VAR + LINE71_VAR + LINE73_VAR + LINE75_VAR + LINE76_IF + LINE77_ASSIGN + LINE78_RCURLY
            + LINE79_ELSE
            + LINE80_ASSIGN + LINE81_RCURLY + LINE82_FOR + LINE83_IF + LINE84_IF + LINE85_ASSIGN + LINE86_RCURLY
            + LINE87_ELSE + LINE88_ASSIGN + LINE89_RCURLY
            + LINE90_CALL + LINE91_CALL + LINE92_IF + LINE93_INCR + LINE94_CALL + LINE95_CALL + LINE96_RCURLY
            + LINE97_RCURLY + LINE98_ELSE + LINE99_BREAK + LINE100_RCURLY
            + LINE101_RCURLY + LINE103_RETURN + LINE104_RCURLY;
    static final String WHOLE_CLASS = LINE14_CLASS + LINE16_FIELD + LINE18_FIELD
            + LINE25_CTOR + LINE26_SUPER + LINE27_RCURLY + LINE29_METHOD
            + LINE30_RETURN + LINE31_RCURLY + LINE33_METHOD + LINE34_RETURN + LINE35_RCURLY
            + WHOLE_METHOD + LINE105_RCURLY;
    static final String WHOLE_FILE = LINE1_PACKAGE + LINE3_IMPORT + LINE4_IMPORT + LINE6_IMPORT + LINE14_CLASS + LINE16_FIELD + LINE18_FIELD
            + LINE25_CTOR + LINE26_SUPER + LINE27_RCURLY + LINE29_METHOD
            + LINE30_RETURN + LINE31_RCURLY + LINE33_METHOD + LINE34_RETURN + LINE35_RCURLY
            + WHOLE_METHOD + NESTED + LINE105_RCURLY;

    Ast createAst(final int lineNumber) {
        String fileName = read("elements.ast-test");

        return createAst(fileName, lineNumber);
    }

    /**
     * Creates the AST under test for the specified file at the given line number.
     *
     * @param fileName
     *         the file to scan
     * @param lineNumber
     *         the first interesting line number
     *
     * @return the created {@link Ast}
     */
    abstract Ast createAst(String fileName, int lineNumber);

    void assertThatAstIs(final Ast ast, final String expectedResult) {
        assertThat(ast.chosenAreaAsString(' ')).isEqualTo(expectedResult);
    }

    String read(final String fileName) {
        File warnings = createCopyInTemp(fileName);
        return warnings.getAbsolutePath();
    }

    private File createCopyInTemp(final String fileName) {
        try {
            File sourceCode = File.createTempFile("ast", ".java");
            sourceCode.deleteOnExit();

            InputStream stream = AbstractAstTest.class.getResourceAsStream(fileName);
            if (stream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            FileUtils.copyInputStreamToFile(stream, sourceCode);
            return sourceCode;
        }
        catch (IOException cause) {
            throw new IllegalArgumentException(cause);
        }
    }

    protected void verifyAstAtLine(final int line, final String expectedAst) {
        assertThat(createAst(line)).as("AST at starting line %d", line).hasToString(expectedAst);
    }
}