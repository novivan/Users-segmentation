import java.util.ArrayList;

import java.lang.String; //кажись оно итак везде импортируется


public class User {
    // ТУТ МЫ НЕ БУДЕМ ВЛАЗИТЬ В ПОЛЯ ГРУПП, ВСЕ ЭТО БУДЕТ ДЕЛАТЬ КОНТРОЛЛЕР
    private static int counter = 0;

    private final int id;
    private ArrayList<Integer> groups; //id-шники
    //TODO: Заменить потом на какую-то нормальную структуру данных (например, древовидную)

    User() {
        id = ++counter;
        groups = new ArrayList<Integer>();
    }

    User(ArrayList<Integer> Groups) {
        id = ++counter;
        groups = new ArrayList<Integer> (Groups);
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getGroups() {
        return groups;
    }

    public void addGroup(int gr_id) { //оптимизировать деревом
        if (!groups.contains(gr_id)) {
            groups.add(gr_id);
        }
    }

    public void deleteGroup(int gr_id) { //тут заранее предполагаем, что в списке только одно такое ID 
                                        //(ПЕРЕПИСАТЬ НА ДЕРЕВО!!!)
        Integer Gr_Id = gr_id;
        groups.remove(Gr_Id);
    }

    public void setNewGroups(ArrayList<Integer> NG) {
        groups = new ArrayList<Integer>(NG);
    }

}
