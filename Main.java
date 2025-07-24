
import java.util.ArrayList;
import java.util.Arrays;


public class Main {
    public static void main(String[] Args) {
        //User A = new User();
        //User B = new User();

        //Group G = new Group("All", new ArrayList<Integer>(Arrays.asList(1, 2)));


        Controller controller = new Controller();
        controller.addUser();
        controller.addUser();
        controller.addUser();
        controller.addUser();
        controller.addUser();
        
        controller.addGroup("All Users", new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5)));
        controller.addGroup("Children", new ArrayList<Integer>(Arrays.asList(3, 4, 5)));
        controller.addGroup("Adults", new ArrayList<Integer>(Arrays.asList(1, 2)));


        controller.printGroups();
    }
}
