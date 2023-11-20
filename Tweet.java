public class Tweet {
    private String id;
    private String msg;
    
    public Tweet(String id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }
}
