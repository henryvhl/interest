import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class ChatRecord {
    private String name;
    private LocalTime time;
    private StringBuffer dialogs;
    
    public ChatRecord(String name, String timeAsString, DateTimeFormatter formatter) throws DateTimeParseException {
        this.name = name;
        if (timeAsString == null || timeAsString.isEmpty()) {
            this.time = null;
        } else if (formatter != null) {
            this.time = LocalTime.parse(timeAsString, formatter);
        } else {
            this.time = LocalTime.parse(timeAsString);
        }
        this.dialogs = new StringBuffer();
    }
    
    public void addLine(String dialog) {
        dialogs.append('\n').append(dialog);
    }
    
    public String getName() {
        return this.name;
    }
    
    public LocalTime getTime() {
        return this.time;
    }
    
    public String getWords() {
        return this.dialogs.toString();
    }
    
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(name).append(" (")
            .append(time).append("): ").append(dialogs);
        return sb.toString();
    }
}
