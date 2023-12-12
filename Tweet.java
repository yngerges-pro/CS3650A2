public class Tweet {
    private String id;
    private String msg;
    private String time;

    public Tweet(String id, String msg, String time) {
        this.id = id;
        this.msg = msg;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public String getTime(){
        return time;
    }
}
