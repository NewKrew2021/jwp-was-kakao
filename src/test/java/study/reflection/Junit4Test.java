package study.reflection;

public class Junit4Test {
    @study.reflection.MyTest
    public void one() throws Exception {
        System.out.println("Running Test1");
    }

    @study.reflection.MyTest
    public void two() throws Exception {
        System.out.println("Running Test2");
    }

    public void testThree() throws Exception {
        System.out.println("Running Test3");
    }
}
