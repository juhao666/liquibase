package liquibase.statement;

public class RenameColumnStatementTest extends AbstractSqStatementTest<RenameColumnStatement> {

    @Override
    protected RenameColumnStatement createStatementUnderTest() {
        return new RenameColumnStatement(null, null, null, null, null);
    }


}
