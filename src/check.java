import java.util.ArrayList;

public class check {
    public boolean equal1(ArrayList arr1[], ArrayList arr2[]) {
        int counter = 0;
        if (arr1.length == arr2.length)
            for (int i = 0; i < arr1.length; i++) {
                if (arr1[i] == arr2[i])
                    counter++;
            }
        if(counter ==arr1.length)
            return true;
        else
            return false;
    }
}