package liquibase.change;

import liquibase.database.Database;
import liquibase.database.FirebirdDatabase;
import liquibase.database.SQLiteDatabase;
import liquibase.statement.AddDefaultValueStatement;
import liquibase.statement.SqlStatement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddLookupTableChangeTest extends AbstractChangeTest {

    @Override
    public void getRefactoringName() throws Exception {
        assertEquals("Add Lookup Table", new AddLookupTableChange().getChangeMetaData().getDescription());
    }

    @Override
    public void generateStatement() throws Exception {
        AddLookupTableChange change = new AddLookupTableChange();
        change.setExistingTableName("OLD_TABLE_NAME");
        change.setExistingColumnName("OLD_COLUMN_NAME");
        change.setExistingTableSchemaName("OLD_SCHEMA");
        change.setConstraintName("FK_NAME");
        change.setNewColumnDataType("TYPE(255)");
        change.setNewTableName("NEW_TABLE");
        change.setNewColumnName("NEW_COL");
        change.setNewTableSchemaName("NEW_SCHEM");

        testChangeOnAll(change, new GenerateAllValidator() {
            public void validate(SqlStatement[] statements, Database database) {
                assertEquals(1, statements.length);
                AddDefaultValueStatement statement = (AddDefaultValueStatement) statements[0];


                assertEquals("TABLE_NAME", statement.getTableName());
                assertEquals("COLUMN_NAME", statement.getColumnName());
                assertTrue(statement.getDefaultValue() instanceof Boolean);
                assertEquals(Boolean.TRUE, statement.getDefaultValue());
            }
        });
    }

    @Override
    public void getConfirmationMessage() throws Exception {
        AddLookupTableChange change = new AddLookupTableChange();
        change.setExistingTableName("OLD_TABLE_NAME");
        change.setExistingColumnName("OLD_COLUMN_NAME");

        assertEquals("Lookup table added for OLD_TABLE_NAME.OLD_COLUMN_NAME",change.getConfirmationMessage());
    }

    @Override
    protected boolean changeIsUnsupported(Database database) {
        return database instanceof FirebirdDatabase
                || database instanceof SQLiteDatabase;
    }
}
