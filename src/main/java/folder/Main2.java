package folder;

import java.util.List;

class Main2 extends Parent {

    @Override
    byte[] test(List<String> strs) {
        String r = "";

        for (String s : strs) {
            byte[] bytes = s.getBytes(charset);
            r += new String(bytes, charset);
        }

        return r.getBytes(charset);
    }

    public static void main(String... args) throws Exception {
        new Main2().run(Integer.valueOf(args[0]), Integer.valueOf(args[1]), args[2]);
    }
}
