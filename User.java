// import javax.swing.tree.DefaultMutableTreeNode;

public class User extends Entity {

    User(String id) {
        super(id, EntityTypes.GROUP);
    }

    public EntityTypes getMembers() {
        return EntityTypes.USER;
    }
}



/*
// Visitor Pattern
interface UserVisitor {
    void visit(User user);
}

// Concrete visitor implementation
class PrintUserVisitor implements UserVisitor {
    @Override
    public void visit(User user) {
        System.out.println("Visiting user: " + user);
    }
}

// Element interface
interface Visitable {
    void accept(UserVisitor visitor);
}

// Concrete element implementation
public class User implements Visitable {
    private final String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public void accept(UserVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return username;
    }

}

*/