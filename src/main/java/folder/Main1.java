 // adapted from https://richardstartin.github.io/posts/5-java-mundane-performance-tricks
package folder;

import java.util.LinkedList;
import java.util.List;

class Main1 extends Parent {

    @Override
    List<String> test(List<String> strs) {
        List<String> l = new LinkedList<>();

        for (String s : strs) {
            byte[] bytes = s.getBytes(charset);
            l.add(new String(bytes, charset));
        }

        return l;
    }

    public static void main(String... args) throws Exception {
        new Main1().run(Integer.valueOf(args[0]), Integer.valueOf(args[1]), args[2]);
    }
}
