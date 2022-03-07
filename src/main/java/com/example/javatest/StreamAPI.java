import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamAPI {
    public static void main(String[] args){
        //https://velog.io/@kskim/Java-Stream-API

        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        System.out.println(list.stream().count());
        System.out.println();
        //

        Stream.of("1234", "123","12","21" ,"12345", "123")
                .peek(System.out::println)
                .distinct()
                .sorted(Comparator.reverseOrder())
                .forEach(System.out::println);

        System.out.println();

        Stream<String> streamOne = Arrays.asList("Hello", "World", "Test", "array", "What").stream();
        Stream<String> streamTwo = Stream.of("The", "Hell");
        Stream.concat(streamOne, streamTwo)
                .forEach(System.out::println);

        System.out.println();

        IntStream.of(1, 2, 3, 4, 5)
                .findAny()
                .ifPresent(System.out::println);

        System.out.println();

        System.out.println(Stream.of("1234", "123","12","21" ,"12345", "123")
                .anyMatch(it -> it.contains("1")));


        System.out.println("#####test - 1");


        String templateStatus = "cdb";
        String templateInspectStatus = "NOK";

        boolean isDormant = false;


        System.out.println(Stream.of(EN.DTD, EN.REQ, EN.REJ, EN.RDY)
                .filter(s -> {
                    boolean isDormantT = isDormant;
                    if(isDormantT && s.templateInspectStatus.equals(null) && s.templateStatus.equals(null)){
                        return true;
                    }
                    else if(!isDormantT && (!s.templateInspectStatus.equals(null) && !s.templateStatus.equals(null)))
                        return true;
                    else
                        return false;
                })
                .filter(s -> s.templateStatus.equals(templateStatus))
                .filter(s -> s.templateInspectStatus.equals(templateInspectStatus))
                .findFirst()
                .orElse(null));
    }

    public enum EN{
        TEST(null, "OK"),
        DTD("abc", "OK"),
        REQ("cdb", "NOK"),
        REJ("cda", "OK"),
        RDY("ffa", "NOK");


        private final String templateStatus;
        private final String templateInspectStatus;

        EN(String templateStatus, String templateInspectStatus) {
            this.templateStatus = templateStatus;
            this.templateInspectStatus = templateInspectStatus;
        }
    }
}
