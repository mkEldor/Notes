package adilbek.assigmentnotes;

public class Note {
    private String date;
    private String text;
    private int color;
    private long number;

    public Note(String date, String text, int color, long number) {
        this.date = date;
        this.text = text;
        this.color = color;
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setNewText(String newText) {
        this.text = newText;
    }

    public void setNewDate(String newDate) {
        this.date = newDate;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int newColor) {
        this.color = newColor;
    }

    public long getNumber(){
        return number;
    }

    public void setNumber(int newNumber) {
        this.number = newNumber;
    }
}
