public class Record {

    String name, value, type;

    public Record(){
        name = "name";
        value = "value";
        type = "type";
    }
    public Record(String name, String value, String type){
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public String getType(){
        return type;
    }

}
