// import javax.swing.tree.DefaultMutableTreeNode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User extends Entity {

    private final Set<String> following;

    User(String id) {
        super(id, EntityTypes.GROUP);
        following = new HashSet<>();
    }

    public EntityTypes getMembers() {
        return EntityTypes.USER;
    }

    public Set<String> following() { //protecting data from changes
        return Collections.unmodifiableSet(new HashSet<>(following));
    }

    public void follow(String userId) {
        following.add(userId);
    }

    public void unFollow(String userId) {
        following.remove(userId);
    }

    public boolean isFollowing(Tweet t) {//
        return following.contains(t.getId());
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