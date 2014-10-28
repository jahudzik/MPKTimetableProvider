package pl.jahu.mpk;

import dagger.ObjectGraph;

/**
 * MPK Timetable Parser
 * Created by jahudzik on 2014-10-21.
 */
public class DaggerTestApplication {

    public static ObjectGraph graph;

    public static void init() {
        graph = ObjectGraph.create(new TestModule());
        graph.injectStatics();
    }

    public static void inject(Object clazz) {
        graph.inject(clazz);
    }

}
