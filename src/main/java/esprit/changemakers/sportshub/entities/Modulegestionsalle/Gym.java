package esprit.changemakers.sportshub.entities.Modulegestionsalle;

public class Gym {

	private int id;
	private int user_id;
	private String name;
	private String description;
	private String location;
	private String image;


	public Gym(int id,int user_id, String name, String description, String location,String image) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.location = location;
		this.user_id = user_id;
		this.image=image;
	}



	public Gym(int user_id,String name, String description, String location,String image) {
		super();
		this.name = name;
		this.description = description;
		this.location = location;
		this.user_id = user_id;
		this.image=image;
	}


	public Gym() {
		super();
	}

	public Gym(int id, int user_id, String name, String description, String location) {
		this.id = id;
		this.user_id = user_id;
		this.name = name;
		this.description = description;
		this.location = location;
	}

	public Gym(int i, String bizerte_fitness, String s, String bizerte) {
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "Gym{" +
				"id=" + id +
				", user_id=" + user_id +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", location='" + location + '\'' +
				", image='" + image + '\'' +
				'}';
	}
}
