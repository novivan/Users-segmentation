import java.util.ArrayList;

public class Group {
    // ТУТ МЫ НЕ БУДЕМ ВЛАЗИТЬ В ПОЛЯ ЮЗЕРОВ, ВСЕ ЭТО БУДЕТ ДЕЛАТЬ КОНТРОЛЛЕР
    private static int counter = 0;

    private final int id;
    private String name;
    private ArrayList<Integer> users_id;

    Group(String Name) {
        id = ++counter;
        name = new String(Name);
        users_id = new ArrayList<Integer>();
    }

    Group(String Name, ArrayList<Integer> Users) {
        id = ++counter;
        name = new String(Name);
        users_id = new ArrayList<Integer>(Users);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    } 

    public void setName(String newName) {
        name = new String(newName);
    }

    public ArrayList<Integer> getUsers() {
        return users_id;
    }

    public void addUser(int user_id) {
        if (!users_id.contains(user_id)) {
            users_id.add(user_id);
        }
    }

    public void deleteUser(int user_id) { //тут заранее предполагаем, что в списке только одно такое ID 
            //(ПЕРЕПИСАТЬ НА ДЕРЕВО!!!)
        Integer U_Id = user_id;
        users_id.remove(U_Id);
    }

    public void setNewUsers(ArrayList<Integer> NU) {
        users_id = new ArrayList<Integer>(NU);
    }

    public String toString() {
        String ret = new String("");
        Integer ID = id;
        ret += "Группа номер " + ID.toString() + " под названием \"" + name + "\". Пользователи(id):\n";
        for (Integer usr: users_id) {
            ret += " -- " + usr.toString() + "\n";
        }
        ret += "\n";
        return ret;
    }
}
