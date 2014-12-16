public class Services {
    public static String geldString(int cents) {
        return  String.format("%.2f", (float)cents/100);
    }
}
