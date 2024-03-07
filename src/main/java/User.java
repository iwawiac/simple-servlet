public class User {
    protected int id;
    protected String name;
    protected String surname;
    protected int age;

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String surname, int age) {
        this(name, surname, age);
        this.id = id;
    }

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
