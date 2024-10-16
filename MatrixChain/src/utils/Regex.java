package utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Regex {
    private final String label, exp;
    private Pattern p;
    public Regex(String label, String exp) {
        this.label = label;
        this.exp = exp;
        try {
            p = Pattern.compile(exp);
        } catch (Exception e) {
            System.out.println("Ошибка синтаксиса");
            System.exit(-1);
        }
    }
    public Boolean MatchLine(String line) {
        return Pattern.matches(exp, line);
    }
    public Boolean InLine(String line) {
        Matcher m = p.matcher(line);
        return m.find();
    }
    public int FirstOccurance(String line) {
        Matcher m = p.matcher(line);
        if (m.find()) {
            return m.start();
        }
        else return -1;
    }
    public String findSubstr(String line) {
        Matcher m = p.matcher(line);
        if (!m.find()) {
            return null;
        }
        return m.group(1);
    }
    public ArrayList<String> ExtractNums(String line) {
        Matcher m = p.matcher(line);
        ArrayList<String> nums = new ArrayList<>();
        while(m.find()){
            nums.add(line.substring(m.start(), m.end()));
        }
        return nums;
    }
    public String GetLabel() {
        return label;
    }
}
