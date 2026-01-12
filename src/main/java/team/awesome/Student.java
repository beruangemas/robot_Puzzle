// Student.java
package team.awesome;

public class Student {
    private String name;
    private String robot1;
    private String robot2;
    
    // Constructor
    public Student(String name, String robot1, String robot2){
        this.name = name;
        this.robot1 = robot1;
        this.robot2 = robot2;
    }

    // Getters
    public String getName(){
        return name;
    }

    public String getRobot1(){
        return robot1;
    }

    public String getRobot2(){
        return robot2;
    }

    //setters
    public void setName(String name){
        this.name = name;
    }
}