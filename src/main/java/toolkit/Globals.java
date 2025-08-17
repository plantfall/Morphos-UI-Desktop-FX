package toolkit;

public class Globals {

    private static Toast toastInstance;

    public static void InitializeToast() {
        if (toastInstance == null) {
            toastInstance = new Toast();
        }
    }

    public static Toast getToast() {
        return toastInstance;
    }
}
