import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CountAppearance {
    
    public static final Comparator<Participant> COMPARE_BY_RECORDS = new Comparator<Participant>() {
        @Override
        public int compare(Participant p1, Participant p2) {
            return p1.countRecords() - p2.countRecords();
        }
    };
    
    public static void main(String[] args) throws IOException {
        List<ChatRecord> records = readRecord("20161129.txt");
        System.out.println("Total records: " + records.size());
        System.out.println();
//        for (ChatRecord record : records) {
//            System.out.println(record);
//        }
//        System.out.println();
        
        List<Participant> people = countParticipants(records);
        System.out.println("Total participants: " + people.size());
        Collections.sort(people, COMPARE_BY_RECORDS.reversed());
        System.out.println();
        for (Participant person : people) {
            System.out.println(person);
        }
        System.out.println("\nThe most active participant: " + people.get(0).getName());
        people.get(0).printRecords();
        System.out.println();
        
        String[] topics = {"\u4f5c\u4e1a", "\u62db\u8058", "project", 
                "\u8bfe\u7a0b", "简历", "面试", "老师", "java", "666", "+1"}; 
        
        for (int i = 0; i < topics.length; i++) {
            printTopic(records, i+1, topics[i], topics[i] == "1");
        }
//        System.out.println(String.format("\\u%04x\\u%04x", (int)'课', (int)'次'));
    }
    
    private static List<ChatRecord> readRecord(String fileName) throws IOException {
        List<ChatRecord> records = new ArrayList<ChatRecord>();
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String prefix = "From ";
        //String middle = " to Everyone: ("; 
        String timePattern = "hh:mm a";
        String suffix = ")";
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
            .appendPattern(timePattern).toFormatter();
        
        String line = reader.readLine();
        while (line != null) {
            int index_to = line.indexOf(" to");
            int index_leftP = line.indexOf('(');
            String name = line.substring(prefix.length(), index_to);
            String timeAsString = line.substring(index_leftP + 1, line.length() - suffix.length());
            try {
                ChatRecord record = new ChatRecord(name, timeAsString, formatter);
                records.add(record);
                
                line = reader.readLine();
                while (line != null && !line.startsWith(prefix)) {
                    record.addLine(line);
                    line = reader.readLine();
                }
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println(name + ": " + timeAsString + ": " + line);
                reader.close();
                throw e;
            }
        }
        reader.close();
        
        return records;
    }
    
    private static List<Participant> countParticipants(List<ChatRecord> records) {
        Map<String, Participant> nameMap = new HashMap<String, Participant>();
        for (ChatRecord record: records) {
            String name = record.getName();
            if (name == null) {
                name = "<EMPTY>";
            }
            Participant person = nameMap.get(name);
            if (person == null) {
                person = new Participant(name);
                nameMap.put(name, person);
            }
            person.addChatRecord(record);
        }
        return new ArrayList<Participant>(nameMap.values());
    }
    
    private static void printTopic(List<ChatRecord> records, int index, String topic, boolean verbose) {
        int count = 0;
        StringBuffer buffer = new StringBuffer();
        for (ChatRecord record : records) {
            if (record.getWords().contains(topic)) {
                count ++;
                if (verbose) {
                    buffer.append(record).append('\n');
                }
            }
        }
        System.out.println("[Topic "+ index +"] '" + topic + "': "+ count +"\u6b21");
        System.out.println(buffer);
    }
}
