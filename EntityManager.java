import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private static final EntityManager _instance = new EntityManager();

    private final Map<String, Entity> entityMap;

    private EntityManager(){
        entityMap = new HashMap<>();
    }

    public static EntityManager getInstance() {return _instance;}

    public Group createGroup(String id) {
        if(id == null || id.length() == 0 || entityMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        Group g = new Group(id);
        entityMap.put(id, g);
        return g;
    }

    public User createUser(String id) {
        if(id == null || id.length() == 0 || entityMap.containsKey(id)) {
            throw new IllegalArgumentException();
        }
        User user = new User(id);
        entityMap.put(id, user);
        return user;
    }

    public boolean userExists(String id) {
        boolean containsUserId = entityMap.containsKey(id);
        if(containsUserId) {
            EntityTypes type = entityMap.get(id).getType();

            if(type == EntityTypes.USER) {
                return true;
            }
        }
        return  false;
    }

    public boolean groupExists(String id) {
        return entityMap.containsKey(id) && entityMap.get(id).getType() == EntityTypes.GROUP;
    }
}
