package liquibase.change;

import liquibase.database.Database;
import liquibase.database.InformixDatabase;
import liquibase.database.OracleDatabase;
import liquibase.database.PostgresDatabase;
import liquibase.statement.CreateSequenceStatement;
import liquibase.statement.SqlStatement;
import liquibase.test.DatabaseTest;
import liquibase.test.DatabaseTestTemplate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Tests for {@link CreateSequenceChange}
 */
public class CreateSequenceChangeTest extends AbstractChangeTest {

    @Override
    @Test
    public void getRefactoringName() throws Exception {
        assertEquals("Create Sequence", new CreateSequenceChange().getChangeMetaData().getDescription());
    }

    @Override
    @Test
    public void generateStatement() throws Exception {
        new DatabaseTestTemplate().testOnAllDatabases(new DatabaseTest() {
            public void performTest(Database database) throws Exception {
                if (!database.supportsSequences()) {
                    return;
                }

                CreateSequenceChange change = new CreateSequenceChange();
                change.setSchemaName("SCHEMA_NAME");
                change.setSequenceName("SEQ_NAME");
                change.setIncrementBy(1);
                change.setMinValue(2);
                change.setMaxValue(3);
                change.setOrdered(true);
                change.setStartValue(4);

                SqlStatement[] sqlStatements = change.generateStatements(database);
                assertEquals(1, sqlStatements.length);
                assertTrue(sqlStatements[0] instanceof CreateSequenceStatement);

                assertEquals("SCHEMA_NAME", ((CreateSequenceStatement) sqlStatements[0]).getSchemaName());
                assertEquals("SEQ_NAME", ((CreateSequenceStatement) sqlStatements[0]).getSequenceName());
                assertEquals(1, ((CreateSequenceStatement) sqlStatements[0]).getIncrementBy());
                assertEquals(2, ((CreateSequenceStatement) sqlStatements[0]).getMinValue());
                assertEquals(3, ((CreateSequenceStatement) sqlStatements[0]).getMaxValue());
                assertEquals(4, ((CreateSequenceStatement) sqlStatements[0]).getStartValue());
                assertEquals(true, ((CreateSequenceStatement) sqlStatements[0]).getOrdered());
            }
        });
    }

    @Override
    @Test
    public void getConfirmationMessage() throws Exception {
        CreateSequenceChange change = new CreateSequenceChange();
        change.setSequenceName("SEQ_NAME");

        assertEquals("Sequence SEQ_NAME created", change.getConfirmationMessage());
    }

    @Override
    protected boolean changeIsUnsupported(Database database) {
        return !database.supportsSequences();
    }
}
