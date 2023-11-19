//Observer Pattern
import java.util.ArrayList;
import java.util.List;

// Observer interface
interface Observer {
    void update(GroupCount groupCount);
}

// Concrete observer implementation
class UserCountObserver implements Observer {
    @Override
    public void update(GroupCount groupCount) {
        System.out.println("Observer notified: " + groupCount);
    }
}

// Subject (observable) class
public class GroupCount {
    private int count;
    private List<Observer> observers = new ArrayList<>();

    public GroupCount(int count) {
        this.count = count;
    }

    public int getCount(){
        return this.count;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void Increment() {
        this.count += 1;
        notifyObservers();
    }

    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }


    @Override
    public String toString() {
        return "Group Count " + this.count;
    }

    
    
}

