public class split {
    public static void main(String args[]){
        String test = "aćć“ćć¹";
        String test2[] = test.split("[.]");
        System.out.println(test2[0]);
    }
}
