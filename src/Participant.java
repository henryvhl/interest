import java.util.ArrayList;
import java.util.List;


public class Participant {
    private String name;
    private List<ChatRecord> records;
    
    public Participant(String name) {
        this.name = name;
        records = new ArrayList<ChatRecord>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void addChatRecord(ChatRecord record) {
        if (record != null) {
            records.add(record);
        }
    }
    
    public int countRecords() {
        return records.size();
    }
    
    public void printRecords() {
        for (ChatRecord record : records) {
            System.out.println(record);
        }
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer("[").append(name).append("]: ")
            .append(records.size()).append(" record");
        if (records.size() > 1) {
            sb.append('s');
        }
        return sb.toString();
    }
}
