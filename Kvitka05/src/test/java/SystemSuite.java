import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(LogicalAssumption.class)
@IncludeTags("system")
public class SystemSuite {
}
