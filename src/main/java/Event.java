public class Event extends Task {

    protected Event(String name, String date) {
        super(name, date);
        type = "E";
    }

    @Override
    public String stringType() {
        return "event";
    }

}