 // adapted from https://richardstartin.github.io/posts/5-java-mundane-performance-tricks
package folder;

import static folder.HttpUtils.get;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

abstract class Parent {

    Charset charset = StandardCharsets.UTF_8;

    abstract Object test(List<String> contents);

    long avg(List<Long> l) {
        return Math.round(l.stream().mapToLong(v -> v).average().getAsDouble());
    }

    int[] indexAfterSkipingMs(List<Long> l, int s) {
        int t = 0, i = 0;
        while (true) {
            t += l.get(i);
            i++;
            if (t > s) {
                return new int[]{i, t};
            }
        }
    }

    List<String> shuffledStringFromString(String str, int count) {
        List<String> l = new LinkedList<>();
        int blockLen = str.length() / count;

        for (int i = 0; i < count; i++) {
            int pos = i * blockLen;
            l.add(str.substring(pos) + str.substring(0, pos));
        }
        return l;
    }

    void run(int repeatString, int repeatTests, String url) throws Exception {
        String str = get(url);
        List<String> strs = shuffledStringFromString(str, repeatString);

        int totalLength = strs.stream()
                .mapToInt(o -> o.length())
                .reduce((a, b) -> a + b).getAsInt();
        System.out.println(totalLength + " chars in " + strs.size() + " strings made of " + str.length() + " chars");

        List<Long> l = new LinkedList<>();
        for (int i = 0; i < repeatTests; i++) {
            long start = System.currentTimeMillis();
            test(strs);
            long time = System.currentTimeMillis() - start;
            l.add(time);
            System.out.println(time + " ms");
        }

        System.out.println("average: " + avg(l));
        int[] hotIdx = indexAfterSkipingMs(l, 10000);
        System.out.println("if " + hotIdx[1] + " ms of the first " + hotIdx[0] + " runs skipped, average: " + avg(l.subList(hotIdx[0], l.size())));
    }
}
