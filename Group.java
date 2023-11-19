//Singleton

import java.util.ArrayList;
import java.util.Collection;

public class Group extends Entity {
    private final Collection<Entity> members;

    Group(String id) {
        super(id, EntityTypes.GROUP);
        this.members = new ArrayList<>();
    }

    public Collection<Entity> getMembers() {
        return this.members;
    }
}

/*
User user = entityManager.createUser("username")
group.getMembers().add(user);
*/
