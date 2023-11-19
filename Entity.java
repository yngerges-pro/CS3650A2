public abstract class Entity {
    private final String id;
    private final EntityTypes type;

    protected Entity(String id, EntityTypes type) {
        this.id = id;
        this.type = type;
    }
    
    public String getId(){
        return id;
    }

    public EntityTypes getType() {
        return type;
    }

    @Override
    public String toString() {
        return id;
    }
}
