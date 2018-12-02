package yong.intern;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class DependencyRequestTest {

    @Test
    public void requestLibrary() {
        List<String> result = DependencyRequest.requestDependency("akka");
        Assert.assertEquals(10, result.size());
    }

    @Test
    public void requestLibraryWithEmptyStrin() {
        List<String> result = DependencyRequest.requestDependency("");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
    }
}