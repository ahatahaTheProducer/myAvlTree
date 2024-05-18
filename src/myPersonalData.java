public class myPersonalData {
    public String name;
    public float gValue;
    
    myPersonalData(String name, float gValue){
        this.name = name;
        this.gValue = gValue;
    }
    public String toString(){
        return this.name + " " + this.gValue;
    }
    //setters and getters
    public String getName(){
        return this.name;
    }
    public float getGValue(){
        return this.gValue;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setGValue(float gValue){
        this.gValue = gValue;
    }
}
